package com.uprightpath.ld.thirty.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.uprightpath.ld.thirty.Controls;
import com.uprightpath.ld.thirty.Main;
import com.uprightpath.ld.thirty.logic.WorldGroup;

/**
 * Created by Geo on 8/24/2014.
 */
public class WorldSelectScreen extends GameScreen implements OptionMenuListener {
    private Array<WorldGroupButton> worldGroupButtons = new Array<WorldGroupButton>();
    private Table mainTable;
    private ScrollPane scrollPane;
    private Table worldsTable;
    private int currentSelected;
    private ImageButton btnMute;
    private ImageButton btnOptions;

    public WorldSelectScreen(Main main) {
        super(main);
        this.optionWindow = new OptionWindow(this, main.getSkin(), false);
        this.keyAssignWindow = new KeyAssignWindow(this, main.getSkin());
        btnMute = new ImageButton(main.getSkin().getDrawable("sound-on"), main.getSkin().getDrawable("sound-on"), main.getSkin().getDrawable("sound-off"));
        btnMute.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.musicManager.setMute(btnMute.isChecked());
                Main.soundManager.setMute(btnMute.isChecked());
            }
        });
        btnOptions = new ImageButton(main.getSkin().getDrawable("options-button"));
        btnOptions.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                WorldSelectScreen.this.setOptionMenuVisible(true);
            }
        });
        mainTable = new Table(main.getSkin());
        mainTable.setBackground(main.getSkin().getDrawable("default-round-large"));
        mainTable.setFillParent(true);
        mainTable.pad(10);
        mainTable.add("Worlds Collide").fillX().expandX().align(Align.center);
        mainTable.add(btnMute);
        mainTable.add(btnOptions).row();
        mainTable.add("a tale of many").fillX().expandX().align(Align.center).colspan(3).row();
        worldsTable = new Table(main.getSkin());
        worldsTable.setFillParent(true);
        scrollPane = new ScrollPane(worldsTable, main.getSkin());


        mainTable.add(scrollPane).fill().expand().colspan(3);
        stack.addActor(mainTable);
    }

    @Override
    protected void renderImplement(float delta) {
        if (Controls.UP.isJustDown()) {
            updateSelected((worldGroupButtons.size + currentSelected - 1) % worldGroupButtons.size);
        } else if (Controls.DOWN.isJustDown()) {
            updateSelected((worldGroupButtons.size + currentSelected + 1) % worldGroupButtons.size);
        }
        if (Controls.ACCEPT.isJustDown()) {
            if (!worldGroupButtons.get(currentSelected).isDisabled()) {
                worldGroupButtons.get(currentSelected).toggle();
            }
        }
    }

    @Override
    public void update() {
        currentSelected = 0;
        Array<WorldGroup> worldGroups = new Array<WorldGroup>();
        main.manager.getAll(WorldGroup.class, worldGroups);
        worldGroups.sort();
        worldGroupButtons.clear();
        worldsTable.clearChildren();
        for (int i = 0; i < worldGroups.size; i++) {
            WorldGroupButton worldGroupButton = new WorldGroupButton(worldGroups.get(i), i, main.getSkin());
            worldGroupButtons.add(worldGroupButton);
            worldsTable.add(worldGroupButton).fillX().expandX().row();
        }
        mainTable.layout();
        updateSelected(currentSelected);
    }

    public void playWorldGroup(WorldGroup worldGroup) {
        main.playWorldGroup(worldGroup);
    }

    public class WorldGroupButton extends TextButton {
        private WorldGroup worldGroup;
        private int id;

        public WorldGroupButton(WorldGroup worldGroup, int id, Skin skin) {
            super(worldGroup.getName(), skin);
            this.worldGroup = worldGroup;
            this.id = id;
            if (main.preferences.getBoolean("w" + (worldGroup.getId() - 1), false) || worldGroup.getId() == 0) {
                this.setDisabled(false);
            } else {
                this.setDisabled(true);
            }
            this.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    WorldSelectScreen.this.playWorldGroup(WorldGroupButton.this.worldGroup);
                }
            });
            this.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    WorldSelectScreen.this.updateSelected(WorldGroupButton.this.id);
                }
            });
        }
    }

    public void updateSelected(int id) {
        if (worldGroupButtons.size > 0) {
            worldGroupButtons.get(currentSelected).setStyle(main.getSkin().get("default", TextButton.TextButtonStyle.class));
            currentSelected = id;
            worldGroupButtons.get(currentSelected).setStyle(main.getSkin().get("selected", TextButton.TextButtonStyle.class));
        }
    }

    public void show() {
        super.show();
    }

    public void hide() {
        super.hide();
    }
}
