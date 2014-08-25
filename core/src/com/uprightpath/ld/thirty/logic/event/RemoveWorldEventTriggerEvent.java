package com.uprightpath.ld.thirty.logic.event;

import com.uprightpath.ld.thirty.logic.*;

/**
 * Created by Geo on 8/24/2014.
 */
public class RemoveWorldEventTriggerEvent implements WorldEvent, GlobalEvent {

    private WorldEventTrigger worldEventTrigger;

    public RemoveWorldEventTriggerEvent() {

    }

    public RemoveWorldEventTriggerEvent(WorldEventTrigger worldEventTrigger) {
        this.worldEventTrigger = worldEventTrigger;
    }

    @Override
    public void trigger(World world) {
        triggered(world);
    }

    private void triggered(World world) {
        world.removeWorldEventTrigger(worldEventTrigger);
    }

    @Override
    public void trigger(World world, Agent agent) {
        triggered(world);
    }
}
