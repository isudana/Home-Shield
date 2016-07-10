package com.home.shield;

import com.home.shield.sensors.SensorsStore;
import com.home.shield.sensors.pirs.PIRSensor;
import com.home.shield.serial.SerialCommunicator;

public class Executor {

    public static void main(String[] args) {

        new EventHandler().handleEvents();
        SensorsStore.getInstance().registerSensor(RFCodes.PIR1, new PIRSensor("ground-right.mp3", "Ground_Right"));

        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                //Ignore
            }
        }

    }

}
