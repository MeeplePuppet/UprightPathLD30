package com.uprightpath.ld.thirty.logic.agents;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.ObjectMap;
import com.uprightpath.ld.thirty.logic.Agent;
import com.uprightpath.ld.thirty.logic.Interactable;
import com.uprightpath.ld.thirty.story.Story;

/**
 * Created by Geo on 8/22/2014.
 */
public class PlayerAgent extends Agent implements Interactable {
    protected Story story;
    private ObjectMap<String, Integer> items = new ObjectMap<String, Integer>();

    public PlayerAgent() {
    }

    public PlayerAgent(String name, Polygon polygon, Polygon polygonBase) {
        super(name, polygon, polygonBase);
    }

    @Override
    public boolean collides(Agent agent) {
        return true;
    }

    @Override
    public void collidedWith(Agent agent) {

    }


    public Integer getItem(String item) {
        return items.get(item, 0);
    }

    public void setItem(String item, int value) {
        items.put(item, value);
    }

    public void adjustItem(String item, int value) {
        items.put(item, items.get(item, 0) + value);
    }

    @Override
    public int getTextureId() {
        return 0;
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

    }
}
