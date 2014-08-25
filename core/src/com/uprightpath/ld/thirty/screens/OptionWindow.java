package com.uprightpath.ld.thirty.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.uprightpath.ld.thirty.Main;


/**
 * Created by Geo on 8/24/2014.
 */
public class OptionWindow extends Window {
    private boolean game;
    private CheckBox chckbxMute;
    private Slider sldrMusicVolume;
    private Slider sldrEffectVolume;
    private TextButton btnButtons;
    private TextButton btnResume;
    private TextButton btnRestart;
    private TextButton btnExit;

    public OptionWindow(final OptionMenuListener optionMenuListener, Skin skin, boolean game) {
        super("Options", skin);
        this.game = game;
        chckbxMute = new CheckBox("Mute All", skin);
        chckbxMute.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.soundManager.setMute(chckbxMute.isChecked());
                Main.musicManager.setMute(chckbxMute.isChecked());
            }
        });
        this.add(chckbxMute).colspan(2).row();
        sldrMusicVolume = new Slider(0, 1, .1f, false, skin);
        sldrMusicVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.musicManager.setVolume(sldrMusicVolume.getValue());
            }
        });
        this.add("Music");
        this.add(sldrMusicVolume).row();
        sldrEffectVolume = new Slider(0, 1, .1f, false, skin);
        sldrEffectVolume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.soundManager.setVolume(sldrMusicVolume.getValue());
            }
        });
        this.add("Effects");
        this.add(sldrEffectVolume).row();
        btnButtons = new TextButton("Assign Keys", skin);
        btnButtons.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                optionMenuListener.setKeyAssignMenuVisible(true);
            }
        });
        this.add(btnButtons).colspan(2).row();
        if (game) {
            btnResume = new TextButton("Resume", skin);
            btnResume.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    optionMenuListener.setOptionMenuVisible(false);
                }
            });
            this.add(btnResume).colspan(2).row();
            btnRestart = new TextButton("Restart", skin);
            btnRestart.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    optionMenuListener.restart();
                }
            });
            this.add(btnRestart).colspan(2).row();
        }
        btnExit = new TextButton("Exit", skin);
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                optionMenuListener.exit();
            }
        });
        this.add(btnExit).colspan(2).row();
    }
}
