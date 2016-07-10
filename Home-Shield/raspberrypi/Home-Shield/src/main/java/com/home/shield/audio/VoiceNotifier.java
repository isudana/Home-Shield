package com.home.shield.audio;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VoiceNotifier extends PlaybackListener {

    private ExecutorService executor = Executors.newFixedThreadPool(1);

    private static VoiceNotifier instance = new VoiceNotifier();

    private ClassLoader classLoader = getClass().getClassLoader();


    public static VoiceNotifier getInstance() {
        return instance;
    }

    private VoiceNotifier() {
    }

    public void submit(AudioMessage audioMessage) {

        executor.submit( () -> {

            AdvancedPlayer player = null;
            try {
                player = new AdvancedPlayer(getAudioStream(audioMessage.getAudioMessageResource()),
                                   javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());
                player.play();

            } catch (JavaLayerException e) {
                e.printStackTrace();
            } finally {
                player.stop();
                player.close();
                System.out.println("Playback Finished...!");
            }
        });

    }

    public void playbackFinished(PlaybackEvent evt) {
        System.out.println("Playback Finished");
    }

    private InputStream getAudioStream(String fileName) {
        return classLoader.getResourceAsStream(fileName);
    }

    public void reset() {
        executor.shutdownNow();
        executor = Executors.newFixedThreadPool(1);
    }

}
