package com.uprightpath.ld.thirty;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Simple manager class to control the playing of sound effects by name/id.
 * <p/>
 * Due to the limitations of the Sound API it is intended that sounds be <i>short</i> (length < 1s) so that changes in volume during play are more difficult to notice. This is due to there being no tracking of 'running' Sound objects to adjust their volume during run.
 */
public class SoundManager {
    private ObjectMap<String, Sound> soundByName = new ObjectMap<String, Sound>();
    private boolean mute = false;
    private float volume = .5f;

    public SoundManager() {
    }

    public boolean isMute() {
        return mute;
    }

    /**
     * Mutes sound playback.
     * <p/>
     * Also stops all sounds still playing.
     *
     * @param mute
     */
    public void setMute(boolean mute) {
        this.mute = mute;
        if (mute == true) {
            for (Sound sound : soundByName.values()) {
                sound.stop();
            }
        }
    }

    public float getVolume() {
        return volume;
    }

    /**
     * Sets the volume of sound playback.
     *
     * @param volume
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * Plays the specified sound if the system is not muted.
     *
     * @param string
     */
    public void playSound(String string) {
        if (!mute) {
            soundByName.get(string).play(volume);
        }
    }

    /**
     * Adds sound to the Manager providing an identifier.
     *
     * @param string
     * @param sound
     */
    public void addSound(String string, Sound sound) {
        soundByName.put(string, sound);
    }

    /**
     * Disposes of the sound instances
     */
    public void dispose() {
        for (Sound sound : soundByName.values()) {
            sound.dispose();
        }
    }
}