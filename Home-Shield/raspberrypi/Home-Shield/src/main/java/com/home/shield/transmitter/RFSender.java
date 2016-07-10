package com.home.shield.transmitter;

import com.pi4j.io.gpio.RaspiPin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RFSender {

    private static RFSender instance = new RFSender();

    final RCSwitch transmitter = new RCSwitch(RaspiPin.GPIO_02);

    private ExecutorService executor = Executors.newFixedThreadPool(1);

    public static RFSender getInstance() {
        return instance;
    }

    private RFSender() {};

    public void sendCode(String code) {
        System.out.println("Sending " + code);
        transmitter.send(code);

        executor.submit(() -> {
            System.out.printf("Sending " + code);
            transmitter.send(code);
        });
    }

}
