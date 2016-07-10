package com.home.shield.sensors;

public interface Sensor {

    String getName();

    String getAudioMessageFile();

    void handleEvent();

    void reset();

}
