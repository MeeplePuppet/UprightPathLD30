package com.uprightpath.ld.thirty.logic;

/**
 * Created by Geo on 8/23/2014.
 */
public abstract class WorldEventTrigger {
    protected final World world;
    protected WorldEvent event;

    public WorldEventTrigger(World world) {
        this.world = world;
    }

    public WorldEventTrigger(World world, WorldEvent event) {
        this.world = world;
        this.event = event;
    }


    public abstract boolean canTriggerEvent();

    public void triggerEvent() {
        event.trigger(world);
    }

    public void setEvent(WorldEvent event) {
        this.event = event;
    }
}
