package com.uprightpath.ld.thirty.logic.triggers;

import com.badlogic.gdx.math.Polygon;
import com.uprightpath.ld.thirty.logic.AgentEvent;
import com.uprightpath.ld.thirty.logic.AgentEventTrigger;
import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/22/2014.
 */
public abstract class PolygonEventTrigger extends AgentEventTrigger {
    private Polygon polygon;

    public PolygonEventTrigger(World world) {
        super(world);
    }

    public PolygonEventTrigger(World world, AgentEvent event) {
        super(world, event);
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Polygon getPolygon() {
        return polygon;
    }
}
