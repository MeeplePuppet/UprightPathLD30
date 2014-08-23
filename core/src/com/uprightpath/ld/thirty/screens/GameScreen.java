package com.uprightpath.ld.thirty.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uprightpath.ld.thirty.Main;

/**
 * Created by Geo on 8/22/2014.
 */
public abstract class GameScreen implements Screen {
    protected Main main;
    protected Stage stage;

    public GameScreen(Main main) {
        this.main = main;
        stage = new Stage();
    }

    @Override
    public void render(float delta) {
        // Sets up the basic GL flags and clears the screen.
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Implements the rendering.
        renderImplement(delta);

        // Draws the stage that's in use.
        stage.act(delta);
        stage.draw();
    }

    /**
     * Draws the screen specific information.
     *
     * @param delta
     */
    protected abstract void renderImplement(float delta);

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setScreenSize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        if (main.manager.update()) {
            main.loadingAssets();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
