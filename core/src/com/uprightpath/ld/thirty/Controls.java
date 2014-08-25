package com.uprightpath.ld.thirty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by Geo on 8/24/2014.
 */
public enum Controls {
    UP("up", Input.Keys.UP),
    DOWN("down", Input.Keys.DOWN),
    LEFT("left", Input.Keys.LEFT),
    RIGHT("right", Input.Keys.RIGHT),
    JUMP("jump", Input.Keys.Z),
    INTERACT("interact", Input.Keys.X),
    SWAP("swap", Input.Keys.A),
    ACCEPT("accept", Input.Keys.Z),
    CANCEL("cancel", Input.Keys.X),
    ITEMS("items", Input.Keys.S),
    MENU("menu", Input.Keys.ESCAPE);

    public static void update() {
        for (int i = 0; i < Controls.values().length; i++) {
            Controls.values()[i].updateButton();
        }
    }

    private String name;
    private int val;
    private boolean down;
    private boolean justDown;

    Controls(String name, int val) {
        this.name = name;
        this.val = val;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isJustDown() {
        return justDown;
    }

    private void updateButton() {
        down = Gdx.input.isKeyPressed(Main.preferences.getInteger(name + "-button", val));
        justDown = Gdx.input.isKeyJustPressed(Main.preferences.getInteger(name + "-button", val));
    }
}
