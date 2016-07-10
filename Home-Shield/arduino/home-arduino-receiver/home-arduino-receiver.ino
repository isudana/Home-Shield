#include <RCSwitch.h>

RCSwitch rcReceiver = RCSwitch();

void setup() {
  Serial.begin(38400);
  rcReceiver.enableReceive(0);
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
