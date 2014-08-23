package com.uprightpath.ld.thirty.logic.agents;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.ObjectMap;
import com.uprightpath.ld.thirty.logic.Agent;

/**
 * Created by Geo on 8/22/2014.
 */
public class Player extends Agent {
    private ObjectMap<String, Integer> items = new ObjectMap<String, Integer>();

    public Player(String name, Polygon polygon, Polygon polygonBase) {
        super(name, polygon, polygonBase);
    }

    public Integer getItem(String item) {
        return items.get(item, 0);
    }

    public void setItem(String item, int value) {
        items.put(item, value);
    }
}
