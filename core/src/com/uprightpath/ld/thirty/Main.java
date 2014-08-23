package com.uprightpath.ld.thirty;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uprightpath.ld.thirty.screens.WorldScreen;

public class Main extends Game {
    public final AssetManager manager = new AssetManager();
    public final SoundManager soundManager = new SoundManager();
    public final MusicManager musicManager = new MusicManager();
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // Set up the various assets to be loaded by the manager.
        manager.load("sound/test-hit.wav", Sound.class);
        manager.finishLoading();
        soundManager.addSound("test-hit", manager.get("sound/test-hit.wav", Sound.class));
        this.setScreen(new WorldScreen(this));
    }

    /**
     * Used to swap screens or do something while waiting for loading to complete.
     */
    public void loadingAssets() {
    }

    /**
     * Called when the AssetManager has finished loading assets.
     * <p/>
     * Should be used to set up the various screens that require assets.
     */
    public void doneLoadingAssets() {

    }

    public int getUnits() {
        return 32;
    }
}
