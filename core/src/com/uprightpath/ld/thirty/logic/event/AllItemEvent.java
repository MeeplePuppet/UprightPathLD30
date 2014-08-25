package com.uprightpath.ld.thirty.logic.event;

import com.uprightpath.ld.thirty.logic.Agent;
import com.uprightpath.ld.thirty.logic.World;
import com.uprightpath.ld.thirty.logic.WorldEvent;
import com.uprightpath.ld.thirty.logic.WorldEventTrigger;

/**
 * Created by Geo on 8/23/2014.
 */
public class AllItemEvent implements WorldEvent {
    private WorldEventTrigger eventTrigger;
    private String item;
    private int quantity;

    public AllItemEvent() {
    }

    public AllItemEvent(String item, int quanity, WorldEventTrigger eventTrigger) {
        this.item = item;
        this.quantity = quanity;
        this.eventTrigger = eventTrigger;
    }

    @Override
    public void trigger(World world, Agent agent) {
        if (agent == world.getPlayer()) {
            world.getPlayer().adjustItem(item, quantity);
            for (int i = 0; i < world.getWorldGroup().getWorlds().size; i++) {
                world.getWorldGroup().getWorlds().get(i).removeWorldEventTrigger(eventTrigger);
            }
        }
    }
}
