package com.uprightpath.ld.thirty.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.uprightpath.ld.thirty.Main;

/**
 * Created by Geo on 8/23/2014.
 */
public class LoadingScreen extends GameScreen {
    private Table mainTable = new Table();
    private Label lblLoading;
    private ProgressBar progressBar;

    public LoadingScreen(Main main) {
        super(main);

        lblLoading = new Label("Loading: ", main.getSkin());
        progressBar = new ProgressBar(0, 100, 1, false, main.getSkin());
        mainTable.add(lblLoading);
        mainTable.add(progressBar);

        stage.addActor(mainTable);

    }

    @Override
    protected void renderImplement(float delta) {
        if (main.manager.update()) {
            main.doneLoadingAssets();
        } else {
            progressBar.setValue(main.manager.getProgress());
        }
    }

    @Override
    public void update() {

    }
}
