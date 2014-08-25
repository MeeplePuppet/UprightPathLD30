package com.uprightpath.ld.thirty.logic.triggers;

import com.badlogic.gdx.math.Polygon;
import com.uprightpath.ld.thirty.logic.Agent;
import com.uprightpath.ld.thirty.logic.World;
import com.uprightpath.ld.thirty.logic.WorldEvent;


/**
 * Created by Geo on 8/24/2014.
 */
public class ResettingEventTrigger extends InclusionEventTrigger {

    private int timer;
    private int time = 0;

    public ResettingEventTrigger() {
    }

    public ResettingEventTrigger(Polygon polygon, WorldEvent worldEvent, int timer) {
        super(polygon);
        this.event = worldEvent;
        this.timer = timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    @Override
    public boolean canTriggerEvent(World world, Agent agent) {
        if (time == 0 && super.canTriggerEvent(world, agent)) {
            time = timer;
            return true;
        } else if (time != 0) {
            time -= 1;
            return false;
        }
        return false;
    }
}
