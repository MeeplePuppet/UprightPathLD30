package com.uprightpath.ld.thirty;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Simple manager class to control the playing of single music instances.
 */
public class MusicManager {
    private ObjectMap<String, Music> musicByName = new ObjectMap<String, Music>();
    private Array<Music> currentMusic = new Array<Music>();
    private boolean mute = false;
    private float volume = .5f;

    public MusicManager() {
    }

    /**
     * Adds the specified music to the list of those playing and starts it whatever position it was last set to.
     *
     * @param string
     */
    public void playMusic(String string) {
        Music music = musicByName.get(string);
        if (music != null) {
            if (!currentMusic.contains(music, true)) {
                currentMusic.add(music);
            }
            if (!mute) {
                music.play();
                music.setVolume(volume);
                music.setLooping(true);
            }
        }
    }

    /**
     * Removes the specified music from the list of those playing and stops its playing.
     *
     * @param string
     */
    public void stopMusic(String string) {
        Music music = musicByName.get(string);
        if (music != null) {
            currentMusic.removeValue(music, true);
            music.stop();
        }
    }

    public float getVolume() {
        return volume;
    }

    /**
     * Sets the volume variable and updates the volume of all current music instances.
     *
     * @param volume
     */
    public void setVolume(float volume) {
        this.volume = volume;
        if (!mute) {
            for (Music music : currentMusic) {
                music.setVolume(volume);
            }
        }
    }

    public boolean isMute() {
        return mute;
    }

    /**
     * Mutes the playback of the currently playing music.
     *
     * @param mute
     */
    public void setMute(boolean mute) {
        this.mute = mute;
        for (Music music : currentMusic) {
            if (mute) {
                music.setVolume(0);
            } else {
                music.setVolume(volume);
            }
        }
    }

    /**
     * Adds music to the Manager providing an identifier.
     *
     * @param string
     * @param music
     */
    public void addMusic(String string, Music music) {
        if (musicByName.get(string) != null) {
            musicByName.get(string).dispose();
        }
        musicByName.put(string, music);
    }

    /**
     * Disposes of the music instances.
     */
    public void dispose() {
        for (Music sound : musicByName.values()) {
            sound.dispose();
        }
    }
}