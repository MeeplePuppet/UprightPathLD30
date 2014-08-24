package com.uprightpath.ld.thirty.story;

import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/23/2014.
 */
public class DialogAnswer extends Answer {

    public DialogAnswer() {}
    public DialogAnswer(Story story, String text, int exitCode) {
        super(story, text, exitCode);
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
