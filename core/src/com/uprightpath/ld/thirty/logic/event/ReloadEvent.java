package com.uprightpath.ld.thirty.logic.event;

import com.uprightpath.ld.thirty.logic.Agent;
import com.uprightpath.ld.thirty.logic.World;
import com.uprightpath.ld.thirty.logic.WorldEvent;

/**
 * Created by Geo on 8/22/2014.
 */
public class ReloadEvent implements WorldEvent {

    private World world;

    public ReloadEvent() {
    }

    @Override
    public void trigger(World world, Agent agent) {
        agent.setPosition(2f, 2f);
        agent.setDelta(0f, 0f);
        world.getMain().soundManager.playSound("test-hit");
    }
}
