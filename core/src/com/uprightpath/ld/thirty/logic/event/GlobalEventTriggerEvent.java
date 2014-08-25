package com.uprightpath.ld.thirty.logic.event;

import com.uprightpath.ld.thirty.logic.*;

/**
 * Created by Geo on 8/24/2014.
 */
public class GlobalEventTriggerEvent implements WorldEvent, GlobalEvent {
    private GlobalEventTrigger globalEventTrigger;

    public GlobalEventTriggerEvent() {
    }

    public GlobalEventTriggerEvent(GlobalEventTrigger globalEventTrigger) {
        this.globalEventTrigger = globalEventTrigger;
    }

    public void setGlobalEventTrigger(GlobalEventTrigger globalEventTrigger) {
        this.globalEventTrigger = globalEventTrigger;
    }

    @Override
    public void trigger(World world, Agent agent) {
        triggered(world);
    }

    @Override
    public void trigger(World world) {
        triggered(world);
    }

    public void triggered(World world) {
        globalEventTrigger.reset();
        world.addGlobalEventTrigger(globalEventTrigger);
    }
}
