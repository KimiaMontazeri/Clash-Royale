package ClashRoyale.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private Clip clip;
    private Long currentFrame;
    private String state;
    private AudioInputStream audioInputStream;
    private final String filePath;

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

    public String getState() {
        return state;
    }

    public Long getCurrentFrame() {
        return currentFrame;
    }

    public void play() {
        clip.start();
        state = "play";
    }

    public void pause() {
        if (!state.equals("paused")) {
            this.currentFrame = this.clip.getMicrosecondPosition();
            clip.stop();
            state = "paused";
        }
    }

    public void stop() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
        state = "stop";
    }

    public void restart() {
        clip.stop();
        clip.close();
        this.resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    public void resetAudioStream() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
