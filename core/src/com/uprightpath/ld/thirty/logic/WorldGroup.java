package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.uprightpath.ld.thirty.Controls;
import com.uprightpath.ld.thirty.Main;
import com.uprightpath.ld.thirty.story.Story;

/**
 * Created by Geo on 8/23/2014.
 */
public class WorldGroup implements Comparable<WorldGroup> {
    private Main main;
    private Array<World> worlds = new Array<World>();
    private int current;
    private Interactable interaction;
    private String name;
    private int id;

    public WorldGroup() {

    }

    public WorldGroup(Main main) {
        this.main = main;
    }

    public void update() {
        if (Controls.SWAP.isJustDown()) {
            worlds.get(current).getWorldRenderer().setFront(false);
            current = (current + 1) % worlds.size;
        }
        worlds.get(current).getWorldRenderer().setFront(true);
        worlds.get(current).applyPhysics();
        for (int i = 0; i < worlds.size; i++) {
            worlds.get(i).cleanWorld();
        }
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
    }

    public void setInteraction(Interactable interaction) {
        this.interaction = interaction;
    }

    public void enterDialog(int id) {
        this.interaction.getStory().setDialog(id);
    }

    public void exitDialog() {
        interaction = null;
    }

    public Story getStory() {
        return interaction.getStory();
    }

    public boolean isTellingStory() {
        return interaction != null;
    }

    public World getCurrentWorld() {
        return worlds.get(current);
    }

    public void exit(World world) {
        if (worlds.size == 1) {
            worlds.clear();
            main.completedWorldGroup();
        } else {
            worlds.removeValue(world, true);
            current = current % worlds.size;
            update();
        }
    }

    public Array<World> getWorlds() {
        return worlds;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void createDisplay() {
        for (int i = 0; i < worlds.size; i++) {
            worlds.get(i).createDisply();
            worlds.get(i).getWorldRenderer().setFront(i == current);
        }
    }

    public void destoryDisplay() {
        for (int i = 0; i < worlds.size; i++) {
            worlds.get(i).destoryDisply();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isComplete() {
        return worlds.size == 0;
    }

    @Override
    public int compareTo(WorldGroup o) {
        return id - o.id;
    }
}
