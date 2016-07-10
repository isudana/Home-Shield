
#define SIGNAL_IN 0 // INTERRUPT 0 = DIGITAL PIN 2 - use the interrupt number in attachInterrupt

volatile byte impulse = 0; // kolejny puls
volatile int bufor[53];
volatile boolean header = false;
volatile unsigned long StartPeriod = 0; // set in the interrupt
volatile boolean stop_ints = false;


void setup()
{
  attachInterrupt(SIGNAL_IN, calcInput, CHANGE);

  Serial.begin(9600);
}

void loop()
{
  if (stop_ints) //data in buffer
  {
    unsigned long binary = 1;
    //byte i = 0;
    for (byte j = 0; j < 46; j++)
    {
      //Serial.print(binary);
      if ((bufor[j] > 220) &&
          (bufor[j] < 400))
      {
        binary <<= 1;
        //binary |= 1;
        //i++;
        bitSet(binary,0);
      }
      else if ((bufor[j] > 90) &&
               (bufor[j] < 220) && (bufor[j + 1] > 90) &&
               (bufor[j + 1] < 220)) {
        binary <<= 1;
        j++;
        
      }
      else if ((bufor[j] > 90) &&
               (bufor[j] < 220) && (bufor[j + 1] > 220) &&
               (bufor[j + 1] < 400)) {
        binary <<= 1;
        bitSet(binary,0);
        //i += 2;
        j++;
      }
      else break;
          }
          //Serial.println(bitRead(binary,4));
    if (bitRead(binary,23))
    {
      bitClear(binary,23);
      Serial.print("remoteID:");
      Serial.print((binary / 128) & 65535);
      Serial.print(" - ");
      Serial.print("key code:");
      Serial.println(binary & 127);
    }
    else {
      Serial.println("wrong code  ");
      Serial.println(binary, BIN);
    }
    delay (1000);
    header = false;
    impulse = 0;
    stop_ints = false;

    // }
  }
}

// interrupt below...


void calcInput()
{
  // get the time using micros
  unsigned int duration = (int)(micros() - StartPeriod); // save pulse length to bufor
  StartPeriod = micros(); //begin next impulse
  //Serial.println(StartPeriod);
  if (stop_ints) return;
  if ((duration < 90) || (duration > 600)) goto reset; //impulse not right
  bufor[impulse++] = duration;
  if (duration < 415) return;
  if (!header)
  {
    header = true;
    impulse = 0;
    return;
  }
  else
  {
    if ((impulse < 23) || (impulse > 52)) goto reset; //too long or too short info
    stop_ints = true;
    return;
  }
reset:
  header = false;
  impulse = 0;
  return;
}


