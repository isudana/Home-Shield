
package com.home.shield;

import com.home.shield.audio.AudioMessage;
import com.home.shield.audio.VoiceNotifier;
import com.home.shield.serial.SerialCommunicator;
import com.home.shield.sensors.Sensor;
import com.home.shield.sensors.SensorsStore;

public class EventHandler {

    private boolean armed = false;

    public EventHandler() {
        System.out.println("System Activated...!");
        VoiceNotifier.getInstance().submit(new AudioMessage("booting.mp3"));
    }

    public void handleEvents() {
        SerialCommunicator.getInstance().receiveData(data -> {
            System.out.println("#### DATA RECEIVED " + data);
            switch (data) {
                case RFCodes.ARMED: {
                    enableSystem();
                    break;
                }
                case RFCodes.DISARMED: {
                    disableSystem();
                    break;
                }
                case RFCodes.STOP_SIREN: {
                    stopSiren();
                    break;
                }
                default: {
                    handleSensor(data);
                }
            }
        });
    }

    private void resetSensors() {
        VoiceNotifier.getInstance().reset();
        SerialCommunicator.getInstance().sendData(RFCodes.SIREN_OFF);
        SensorsStore.getInstance().getAll().forEach((k, v) -> ((Sensor) v).reset());

    }

    private synchronized void enableSystem() {
        if (!armed) {
            armed = true;
            VoiceNotifier.getInstance().submit(new AudioMessage("activated.mp3"));
            System.out.println("System Armed");
        }
    }

    private synchronized void disableSystem() {
        if (armed) {
            armed = false;
            resetSensors();
            VoiceNotifier.getInstance().submit(new AudioMessage("deactivated.mp3"));
            System.out.println("System Disarmed");
        }
    }

    private synchronized void stopSiren() {
        if (armed) {
            resetSensors();
            VoiceNotifier.getInstance().submit(new AudioMessage("siren-off.mp3"));
        }
    }

    private void handleSensor(String data) {
        if (armed) {
            Sensor sensor = SensorsStore.getInstance().getSensor(data);
            if (sensor != null) {
                sensor.handleEvent();
            }
        }
    }

}
