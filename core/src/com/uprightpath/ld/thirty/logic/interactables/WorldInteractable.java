package com.uprightpath.ld.thirty.logic.interactables;

import com.badlogic.gdx.math.Polygon;
import com.uprightpath.ld.thirty.logic.Agent;
import com.uprightpath.ld.thirty.logic.Collision;
import com.uprightpath.ld.thirty.logic.Interactable;
import com.uprightpath.ld.thirty.logic.World;
import com.uprightpath.ld.thirty.rendering.Renderable;
import com.uprightpath.ld.thirty.rendering.Renderer;
import com.uprightpath.ld.thirty.story.Story;

/**
 * Created by Geo on 8/23/2014.
 */
public class WorldInteractable extends Collision implements Interactable, Renderable {

    private  String name;
    private  World world;
    private int textureId;
    private Story story;
    private Renderer renderer;

    public WorldInteractable() {}

    public WorldInteractable(String name, World world, Polygon polygon, int textureId) {
        super(polygon);
        this.world = world;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getTextureId() {
        return textureId;
    }

    @Override
    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    @Override
    public void interact(Agent agent) {
        world.getWorldGroup().setInteraction(this);
    }


    @Override
    public Renderer getRenderer() {
        return renderer;
    }

    @Override
    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (renderer != null) {
            renderer.setPosition(x, y);
        }
    }
}
