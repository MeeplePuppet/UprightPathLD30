package com.uprightpath.ld.thirty.logic;

/**
 * Created by Geo on 8/22/2014.
 */
public abstract class AgentEventTrigger {
    protected final World world;
    protected AgentEvent event;

    public AgentEventTrigger(World world) {
        this.world = world;
    }

    public AgentEventTrigger(World world, AgentEvent event) {
        this.world = world;
        this.event = event;
    }

    public abstract boolean canTriggerEvent(Agent agent);

    public void triggerEvent(Agent agent) {
        event.trigger(world, agent);
    }

    public void setEvent(AgentEvent event) {
        this.event = event;
    }
}
