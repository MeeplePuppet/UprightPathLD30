package com.uprightpath.ld.thirty;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.uprightpath.ld.thirty.builder.WorldBuilder;
import com.uprightpath.ld.thirty.logic.WorldGroup;
import com.uprightpath.ld.thirty.screens.GameScreen;
import com.uprightpath.ld.thirty.screens.LoadingScreen;
import com.uprightpath.ld.thirty.screens.WorldScreen;

public class Main extends Game {
    public AssetManager manager = new AssetManager();
    public SoundManager soundManager = new SoundManager();
    public MusicManager musicManager = new MusicManager();
    public SpriteBatch batch;
    private Skin skin;
    private TextureAtlas gameAtlas;
    private GameScreen currentScreen;
    private GameScreen loadingScreen;
    private WorldScreen worldScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // Set up the various assets to be loaded by the manager.
        manager.load("ui/ui.json", Skin.class);
        manager.load("ui/ui.atlas", TextureAtlas.class);
        manager.finishLoading();
        skin = manager.get("ui/ui.json", Skin.class);
        skin.get("default-font", BitmapFont.class).setMarkupEnabled(true);
        gameAtlas = manager.get("ui/ui.atlas", TextureAtlas.class);
        manager.load("sound/test-hit.wav", Sound.class);
        loadingScreen = new LoadingScreen(this);
        worldScreen = new WorldScreen(this);
        currentScreen = worldScreen;
        this.setScreen(loadingScreen);
        Kryo kryo = new Kryo();

        System.out.println(Gdx.files.getExternalStoragePath());

        /*
        Output output = new Output(Gdx.files.external("worlds-1.wg").write(false));
        WorldGroup worldGroup = WorldBuilder.buildWorldGroup();
        kryo.writeObject(output, worldGroup);
        output.close();
        */

        Input input = new Input(Gdx.files.external("worlds-1.wg").read());
        WorldGroup worldGroup = kryo.readObject(input, WorldGroup.class);
        worldGroup.setMain(this);
        worldGroup.createDisplay();
        input.close();

        worldScreen.setWorldGroup(worldGroup);
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
        this.setScreen(currentScreen);
    }

    public int getUnits() {
        return 16;
    }

    public Skin getSkin() {
        return skin;
    }

    public void completedWorldGroup() {
        worldScreen.setWorldGroup(WorldBuilder.buildWorldGroup());
    }
}
