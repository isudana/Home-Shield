
package com.home.shield.tts;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Voice;
import java.beans.PropertyVetoException;
import java.util.Locale;

public class TTSEngine {
    SynthesizerModeDesc desc;
    Synthesizer synthesizer;
    String voiceName;

    private static TTSEngine instance = new TTSEngine();

    public static TTSEngine getInstance() {
        return instance;
    }

    private TTSEngine() {

        System.setProperty("freetts.voices",
                           "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        desc = new SynthesizerModeDesc(Locale.US);
        voiceName = "kevin16";

        try {
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

            synthesizer = Central.createSynthesizer(desc);

            synthesizer.allocate();

            synthesizer.resume();
            SynthesizerModeDesc smd =
                    (SynthesizerModeDesc) synthesizer.getEngineModeDesc();
            Voice[] voices = smd.getVoices();
            Voice voice = null;
            for (int i = 0; i < voices.length; i++) {
                if (voices[i].getName().equals(voiceName)) {
                    voice = voices[i];
                    break;
                }
            }
            synthesizer.getSynthesizerProperties().setVoice(voice);
        } catch (EngineException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (AudioException e) {
            e.printStackTrace();
        }

    }

    public void doSpeak(String speakText) {
        synthesizer.speakPlainText(speakText, null);
        try {
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void terminate()  {
        try {
            synthesizer.deallocate();
        } catch (EngineException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        TTSEngine su = TTSEngine.getInstance();
        su.doSpeak("Hello world!");
        su.terminate();
    }
}