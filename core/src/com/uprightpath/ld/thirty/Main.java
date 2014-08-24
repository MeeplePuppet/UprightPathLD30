package com.uprightpath.ld.thirty;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.uprightpath.ld.thirty.logic.WorldGroup;
import com.uprightpath.ld.thirty.screens.GameScreen;
import com.uprightpath.ld.thirty.screens.GameplayScreenScreen;
import com.uprightpath.ld.thirty.screens.LoadingScreen;
import com.uprightpath.ld.thirty.screens.WorldSelectScreen;

public class Main extends Game {
    public AssetManager manager = new AssetManager();
    public SoundManager soundManager = new SoundManager();
    public MusicManager musicManager = new MusicManager();
    public Preferences preferences;
    public SpriteBatch batch;
    private Skin skin;
    private TextureAtlas gameAtlas;
    private GameScreen currentScreen;
    private GameScreen loadingScreen;
    private GameplayScreenScreen worldScreen;
    private WorldSelectScreen worldSelectScreen;
    private WorldGroup worldGroup;

    @Override
    public void create() {
        batch = new SpriteBatch();
        preferences = Gdx.app.getPreferences("com.upright.ld.thirty");
        manager.setLoader(WorldGroup.class, new WorldGroupLoader(new InternalFileHandleResolver()));

        // Set up the various assets to be loaded by the manager.
        manager.load("ui/ui.json", Skin.class);
        manager.load("ui/ui.atlas", TextureAtlas.class);
        manager.finishLoading();
        skin = manager.get("ui/ui.json", Skin.class);
        skin.get("default-font", BitmapFont.class).setMarkupEnabled(true);
        gameAtlas = manager.get("ui/ui.atlas", TextureAtlas.class);
        manager.load("sound/test-hit.wav", Sound.class);
        for (FileHandle fileHandler : Gdx.files.internal("worlds").list()) {
            manager.load(fileHandler.path(), WorldGroup.class);
        }
        loadingScreen = new LoadingScreen(this);
        worldSelectScreen = new WorldSelectScreen(this);
        worldScreen = new GameplayScreenScreen(this);
        currentScreen = worldSelectScreen;
        this.setScreen(loadingScreen);
    }


    /**
     * Used to swap screens or do something while waiting for loading to complete.
     */
    public void loadingAssets() {
        currentScreen = ((GameScreen) this.getScreen());
        this.setScreen(loadingScreen);
    }

    /**
     * Called when the AssetManager has finished loading assets.
     * <p/>
     * Should be used to set up the various screens that require assets.
     */
    public void doneLoadingAssets() {
        soundManager.addSound("test-hit", manager.get("sound/test-hit.wav", Sound.class));
        currentScreen.update();
        this.setScreen(currentScreen);
    }

    public int getUnits() {
        return 16;
    }

    public Skin getSkin() {
        return skin;
    }

    public void completedWorldGroup() {
        this.manager.unload("worlds/" + worldGroup.getId() + ".wg");
        this.manager.load("worlds/" + worldGroup.getId() + ".wg", WorldGroup.class);
        this.worldGroup.destoryDisplay();
        this.worldGroup = null;
        this.worldSelectScreen.update();
        System.out.println("This was called?");
        this.setScreen(worldSelectScreen);
    }

    public void playWorldGroup(WorldGroup worldGroup) {
        System.out.println("Called?");
        this.worldGroup = worldGroup;
        this.worldGroup.setMain(this);
        this.worldGroup.createDisplay();
        worldScreen.setWorldGroup(worldGroup);
        this.setScreen(worldScreen);
    }
}
