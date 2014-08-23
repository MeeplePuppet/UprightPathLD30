package com.uprightpath.ld.thirty.logic.triggers;

import com.badlogic.gdx.math.Intersector;
import com.uprightpath.ld.thirty.logic.Agent;
import com.uprightpath.ld.thirty.logic.AgentEvent;
import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/22/2014.
 */
public class ExclusionTrigger extends PolygonEventTrigger {
    public ExclusionTrigger(World world) {
        super(world);
    }

    public ExclusionTrigger(World world, AgentEvent event) {
        super(world, event);
    }

    @Override
    public boolean canTriggerEvent(Agent agent) {
        return !Intersector.overlapConvexPolygons(this.getPolygon(), agent.getPolygonCollision());
    }
}
