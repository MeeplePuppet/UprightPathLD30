package com.uprightpath.ld.thirty.logic.triggers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.uprightpath.ld.thirty.logic.Agent;
import com.uprightpath.ld.thirty.logic.WorldEventTrigger;

/**
 * Created by Geo on 8/22/2014.
 */
public class InclusionTrigger extends WorldEventTrigger {
    public InclusionTrigger() {}
    public InclusionTrigger(Polygon polygon) {
        super(polygon);
    }

    @Override
    public boolean canTriggerEvent(Agent agent) {
        return Intersector.overlapConvexPolygons(this.getPolygon(), agent.getPolygon());
    }
}
