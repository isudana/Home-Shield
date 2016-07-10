#include <RCSwitch.h>

RCSwitch rcSender = RCSwitch();


void setup() {
  Serial.begin(38400);
  rcSender.enableTransmit(10);

}

void loop() {
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
  }
}


  


