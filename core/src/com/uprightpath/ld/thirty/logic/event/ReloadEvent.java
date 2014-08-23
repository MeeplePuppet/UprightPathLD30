package com.uprightpath.ld.thirty.logic.event;

import com.uprightpath.ld.thirty.logic.Agent;
import com.uprightpath.ld.thirty.logic.Event;
import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/22/2014.
 */
public class ReloadEvent implements Event {
    @Override
    public void trigger(World world, Agent agent) {
        agent.setPosition(2f, 2f);
        agent.setDelta(0f, 0f);
        world.getMain().soundManager.playSound("test-hit");
    }
}
