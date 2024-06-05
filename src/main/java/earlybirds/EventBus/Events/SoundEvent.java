package earlybirds.EventBus.Events;

/**
 * Event class to communicate changes in sounds.
 */
public class SoundEvent implements Event {
    String soundText;
    boolean playOnce;

    /**
     * Make a sound event by govomg a sound file and whether it should play once or
     * loop.
     *
     * @param soundFilename the soundfile
     * @param playOnce      whether it should play once or loop
     */
    public SoundEvent(String soundFilename, boolean playOnce) {
        this.soundText = soundFilename;
        this.playOnce = playOnce;
    }

    /**
     * @return Returns the filename for the sound
     */
    public String getSoundText() {
        return soundText;
    }

    /**
     * @return the boolean for whether it should play once.
     */
    public boolean isPlayOnce() {
        return playOnce;
    }
}
