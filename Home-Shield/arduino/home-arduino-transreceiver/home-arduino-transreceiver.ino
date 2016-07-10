#include <RCSwitch.h>

RCSwitch rcReceiver = RCSwitch();
RCSwitch rcSender = RCSwitch();


void setup() {
  Serial.begin(38400);
  rcReceiver.enableReceive(0);
  rcSender.enableTransmit(10);

}

void loop() {
  // For Sensor Receive
  if (rcReceiver.available()) {
    long value = rcReceiver.getReceivedValue();
    if (!(value == 10000000 || value == 10000001)) {
       Serial.print('S');
       Serial.print(rcReceiver.getReceivedValue());
       Serial.print('E');
    }
    rcReceiver.resetAvailable();
  }
}

void serialEvent() {
  while (Serial.available()) {
    int command = Serial.read();
    char* code;
    switch (command) {
      case 'a':
          code = "100110001001011010000000";
        break;
      case 'b':
          code = "100110001001011010000001";
        break;
      case 'c':
        break;
      case 'd':
        break;
      case 'e':
        break;
      default:
       break;
     }
    rcSender.send(code);
    rcSender.send(code);
    rcSender.send(code);
    rcSender.send(code);
  }
}
  


