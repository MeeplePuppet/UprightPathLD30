package com.uprightpath.ld.thirty.story;

import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/23/2014.
 */
public class ExitAnswer extends Answer {
    public ExitAnswer() {}
    public ExitAnswer(Story story, String text, int exitCode) {
        super(story, text, exitCode);
    }

    @Override
    public boolean canPerform(World world) {
        return true;
    }

    @Override
    public int perform(World world) {
        world.exit();
        return 0;
    }
}
