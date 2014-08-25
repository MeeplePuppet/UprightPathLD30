package com.uprightpath.ld.thirty.logic.event;

import com.uprightpath.ld.thirty.logic.*;

/**
 * Created by Geo on 8/24/2014.
 */
public class RemoveGlobalEventTriggerEvent implements WorldEvent, GlobalEvent {

    private GlobalEventTrigger globalEventTrigger;

    public RemoveGlobalEventTriggerEvent() {

    }

    public RemoveGlobalEventTriggerEvent(GlobalEventTrigger globalEventTrigger) {
        this.globalEventTrigger = globalEventTrigger;
    }

    @Override
    public void trigger(World world) {
        triggered(world);
    }

    private void triggered(World world) {
        world.removeGlobalEventTrigger(globalEventTrigger);
    }

    @Override
    public void trigger(World world, Agent agent) {
        triggered(world);
    }
}
