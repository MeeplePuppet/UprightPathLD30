package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.uprightpath.ld.thirty.Main;
import com.uprightpath.ld.thirty.story.Story;

/**
 * Created by Geo on 8/23/2014.
 */
public class WorldGroup {
    private final Main main;
    private Array<World> worlds = new Array<World>();
    private int current;
    private Story story;
    private boolean tellingStory;

    public WorldGroup(Main main) {
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

    public void updateCameras(Vector3 update) {
        for (int i = 0; i < worlds.size; i++) {
            if (i != current) {
                worlds.get(i).getWorldRenderer().updateCamera(update);
            }
        }
    }

    public void render(float delta) {
        updateCameras(worlds.get(current).getWorldRenderer().getCamera());
        for (int i = 0; i < worlds.size; i++) {
            worlds.get((i + current + 1) % worlds.size).getWorldRenderer().render(delta);
        }
    }

    public void addWorld(World world) {
        worlds.add(world);
        worlds.get(0).getWorldRenderer().setFront(true);
    }

    public void enterDialog(int id) {
        tellingStory = true;
        this.story.setDialog(id);
    }

    public void exitDialog() {
        tellingStory = false;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public boolean isTellingStory() {
        return tellingStory;
    }
}
