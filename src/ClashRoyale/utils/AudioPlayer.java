package ClashRoyale.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * This class can be used for playing an audio file
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public class AudioPlayer {

    private Clip clip;
    private Long currentFrame;
    private String state;
    private AudioInputStream audioInputStream;
    private final String filePath;

    /**
     * Constructs an audio file
     * @param filePath file path of the audio
     */
    public AudioPlayer(String filePath) {
        this.filePath = filePath;
        try {

            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(1);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return state of this audio player
     */
    public String getState() {
        return state;
    }

    /**
     * Plays the audio file
     */
    public void play() {
        clip.start();
        state = "play";
    }

    /**
     * Pauses the audio file
     */
    public void pause() {
        if (!state.equals("paused")) {
            this.currentFrame = this.clip.getMicrosecondPosition();
            clip.stop();
            state = "paused";
        }
    }

    /**
     * Stops the audio file
     */
    public void stop() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
        state = "stop";
    }

    /**
     * Restarts this audio file
     */
    public void restart() {
        clip.stop();
        clip.close();
        this.resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    /**
     * Resets the audio player
     */
    public void resetAudioStream() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip.open(audioInputStream);
            clip.loop(1);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
