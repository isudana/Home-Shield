package com.home.shield.serial;

import com.home.shield.Configuration;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.SerialPortException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SerialCommunicator {

    private Serial serialSender;
    //private Serial serialReceiver;

    private static SerialCommunicator instance = new SerialCommunicator();

    private ExecutorService executor = Executors.newFixedThreadPool(1);

    public static SerialCommunicator getInstance() {
        return instance;
    }

    private SerialCommunicator() {

        serialSender = SerialFactory.createInstance();
        //serialReceiver = SerialFactory.createInstance();

        try {
            serialSender.open(Configuration.SERIAL_PORT_SENDER, Configuration.SERIAL_BIT_RATE);
            //serialReceiver.open(Configuration.SERIAL_PORT_RECEIVER, Configuration.SERIAL_BIT_RATE);
        } catch (SerialPortException ex) {
            System.out.println("SERIAL SETUP FAILED : " + ex.getMessage());
        }
    }

    public void receiveData(SerialDataCallback callback) {
        serialSender.addListener(event -> callback.handleData(event.getData()));
    }

    public void sendData(String data) {
        executor.submit(() -> {
            serialSender.write(data);
            serialSender.write(data);
        });
    }

}
