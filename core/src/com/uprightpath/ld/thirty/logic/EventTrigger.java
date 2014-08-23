package com.uprightpath.ld.thirty.logic;

/**
 * Created by Geo on 8/22/2014.
 */
public abstract class EventTrigger {
    protected final World world;
    protected Event event;

    public EventTrigger(World world) {
        this.world = world;
    }

    public EventTrigger(World world, Event event) {
        this.world = world;
        this.event = event;
    }

    public abstract boolean canTriggerEvent(Agent agent);

    public void triggerEvent(Agent agent) {
        event.trigger(world, agent);
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
