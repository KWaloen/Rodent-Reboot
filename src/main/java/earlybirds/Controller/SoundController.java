package earlybirds.Controller;

import earlybirds.EventBus.Eventbus;
import earlybirds.EventBus.Events.Event;
import earlybirds.EventBus.Events.SoundEvent;
import earlybirds.Model.Soundpack;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundController {
    private Clip clip;
    Eventbus eventbus;
    Soundpack soundpack;

    /**
     * Constructor for the SoundController class.
     * 
     * @param eventbus  the eventbus
     * @param soundpack the soundpack
     */
    public SoundController(Eventbus eventbus, Soundpack soundpack) {
        this.eventbus = eventbus;
        this.soundpack = soundpack;
        eventbus.registerHandler(this::handle);
    }

    /**
     * Plays a sound file in an infinite loop.
     * 
     * @param soundFileName the name of the sound file to be played
     */
    public void playSoundInfiniteLoop(String soundFileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFileName));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays a sound file once.
     * 
     * @param soundFileName the name of the sound file to be played
     */
    public void playSoundOnce(String soundFileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFileName));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the sound that is currently playing.
     */
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Handles the sound event.
     * @param event the event to handle
     */
    public void handle(Event event) {
        if (event instanceof SoundEvent) {
            if (((SoundEvent) event).isPlayOnce()) {
                playSoundOnce(((SoundEvent) event).getSoundText());
            } else {
                playSoundInfiniteLoop(((SoundEvent) event).getSoundText());
            }
        }
    }
}
