package com.uprightpath.ld.thirty.logic.event;

import com.uprightpath.ld.thirty.logic.*;

/**
 * Created by Geo on 8/24/2014.
 */
public class WorldEventTriggerEvent implements WorldEvent, GlobalEvent {
    private WorldEventTrigger worldEventTrigger;

    public WorldEventTriggerEvent() {
    }

    public WorldEventTriggerEvent(WorldEventTrigger worldEventTrigger) {
        this.worldEventTrigger = worldEventTrigger;
    }

    public void setWorldEventTrigger(WorldEventTrigger worldEventTrigger) {
        this.worldEventTrigger = worldEventTrigger;
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
        worldEventTrigger.reset();
        world.addWorldEventTrigger(worldEventTrigger);
    }
}
