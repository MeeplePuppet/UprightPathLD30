package com.uprightpath.ld.thirty.story;

import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/23/2014.
 */
public class TapAnswer extends Answer {
    public TapAnswer() {
    }

    public TapAnswer(Story story, int exitCode) {
        super(story, null, exitCode);
    }

    @Override
    public boolean canPerform(World world) {
        return true;
    }

    @Override
    public int perform(World world) {
        return exitCode;
    }
}
