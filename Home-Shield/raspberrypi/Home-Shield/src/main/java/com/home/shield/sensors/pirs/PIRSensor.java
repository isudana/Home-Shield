/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.home.shield.sensors.pirs;

import com.home.shield.Configuration;
import com.home.shield.RFCodes;
import com.home.shield.audio.AudioMessage;
import com.home.shield.audio.VoiceNotifier;
import com.home.shield.sensors.Sensor;
import com.home.shield.serial.SerialCommunicator;
import com.home.shield.transmitter.RFSender;
import com.pi4j.io.serial.Serial;

import java.util.Timer;
import java.util.TimerTask;

public class PIRSensor implements Sensor {

    private int counter = 0;
    private long sensorLastSignalTime = 0;
    private String name;
    private String audioFileName;

    private Timer cancelTimer = new Timer(true);
    private TimerTask cancellingTask = new CancellationTask();

    public PIRSensor(String audioFileName, String name) {
        this.audioFileName = audioFileName;
        this.name = name;
    }

    @Override
    public void handleEvent() {

        if (System.currentTimeMillis() - sensorLastSignalTime > Configuration.PIR_BOUNCE_TIME) {
            sensorLastSignalTime = System.currentTimeMillis();
            counter++;
            cancellingTask.cancel();
            cancellingTask = new CancellationTask();
            SerialCommunicator.getInstance().sendData(RFCodes.SIREN_ON);
            if (counter == 1) {
                cancelTimer.schedule(cancellingTask, Configuration.PIR_FIRST_WARN_DURATION);
                submitVoiceMessage(2);
            } else if (counter == 2) {
                cancelTimer.schedule(cancellingTask, Configuration.PIR_SECOND_WARN_DURATION);
                submitVoiceMessage(5);
            } else {
                submitVoiceMessage(10);
            }
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String getAudioMessageFile() {
        return audioFileName;
    }

    class CancellationTask extends TimerTask {

        @Override
        public void run() {
            SerialCommunicator.getInstance().sendData(RFCodes.SIREN_OFF);
        }
    }

    private void submitVoiceMessage(int count) {
        for (int i=0 ; i < count ; i++ ) {
            VoiceNotifier.getInstance().submit(new AudioMessage(audioFileName));
        }
    }

    public void reset() {
        counter = 0;
        sensorLastSignalTime = 0;
    }

}
