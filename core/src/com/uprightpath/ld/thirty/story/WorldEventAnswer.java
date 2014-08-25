package com.uprightpath.ld.thirty.story;

import com.uprightpath.ld.thirty.logic.World;
import com.uprightpath.ld.thirty.logic.WorldEvent;

/**
 * Created by Geo on 8/24/2014.
 */
public class WorldEventAnswer extends Answer {


    private WorldEvent[] worldEvent;

    public WorldEventAnswer() {
    }

    public WorldEventAnswer(Story story, String text, int exitCode, WorldEvent[] worldEvent) {
        super(story, text, exitCode);
        this.worldEvent = worldEvent;
    }

    @Override
    public boolean canPerform(World world) {
        return true;
    }

    @Override
    public int perform(World world) {
        for (int i = 0; i < worldEvent.length; i++) {
            worldEvent[i].trigger(world, world.getPlayer());
        }
        return exitCode;
    }
}
