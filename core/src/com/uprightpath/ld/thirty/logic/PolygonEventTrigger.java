package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Polygon;

/**
 * Created by Geo on 8/22/2014.
 */
public abstract class PolygonEventTrigger extends EventTrigger {
    private Polygon polygon;

    public PolygonEventTrigger(World world) {
        super(world);
    }

    public PolygonEventTrigger(World world, Event event) {
        super(world, event);
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Polygon getPolygon() {
        return polygon;
    }
}
