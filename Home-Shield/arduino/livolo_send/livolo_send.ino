//
//remoteID:23203 - key code:8   A
//remoteID:23203 - key code:16 B 
//remoteID:23203 - key code:56  C
//remoteID:23203 - key code:42  D


#include <livolo.h>

Livolo livolo(10); // transmitter connected to pin #10


void setup() {
}

void loop() {
 
  livolo.sendButton(23203, 8);
  
  
}
