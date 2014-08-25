package com.uprightpath.ld.thirty.story;

import com.uprightpath.ld.thirty.logic.GlobalEvent;
import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/24/2014.
 */
public class GlobalEventAnswer extends Answer {

    private GlobalEvent[] globalEvent;

    public GlobalEventAnswer() {
    }

    public GlobalEventAnswer(Story story, String text, int exitCode, GlobalEvent[] globalEvent) {
        super(story, text, exitCode);
        this.globalEvent = globalEvent;
    }

    @Override
    public boolean canPerform(World world) {
        return true;
    }

    @Override
    public int perform(World world) {
        for (int i = 0; i < globalEvent.length; i++) {
            globalEvent[i].trigger(world);
        }
        return exitCode;
    }
}
