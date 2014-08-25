package com.uprightpath.ld.thirty.logic;

/**
 * Created by Geo on 8/23/2014.
 */
public abstract class GlobalEventTrigger {
    protected GlobalEvent event;

    public GlobalEventTrigger() {

    }

    public GlobalEventTrigger(GlobalEvent event) {
        this.event = event;
    }


    public abstract boolean canTriggerEvent(World world);

    public void triggerEvent(World world) {
        event.trigger(world);
    }

    public void setEvent(GlobalEvent event) {
        this.event = event;
    }

    public abstract boolean remove();

    public void reset() {};
}
