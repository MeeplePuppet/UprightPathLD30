package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.uprightpath.ld.thirty.Main;

/**
 * Created by Geo on 8/23/2014.
 */
public class GameArrangement {
    private final Main main;
    private Array<World> worlds = new Array<World>();
    private int current;

    public GameArrangement(Main main) {
        this.main = main;
    }

    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            worlds.get(current).getWorldRenderer().setFront(false);
            current = (current + 1) % worlds.size;
            worlds.get(current).getWorldRenderer().setFront(true);
        }
        worlds.get(current).applyPhysics();
    }

    public void render(float delta) {
        for (int i = 0; i < worlds.size; i++) {
            worlds.get((i + current + 1) % worlds.size).getWorldRenderer().render(delta);
        }
    }

    public void addWorld(World world) {
        worlds.add(world);
        worlds.get(0).getWorldRenderer().setFront(true);
    }
}
