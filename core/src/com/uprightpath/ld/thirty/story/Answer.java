package com.uprightpath.ld.thirty.story;

import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/23/2014.
 */
public abstract class Answer {
    protected  Story story;
    protected  String text;
    public int exitCode;

    public Answer() {}

    public Answer(Story story, String text, int exitCode) {
        this.story = story;
        this.text = text;
        this.exitCode = exitCode;
    }

    public abstract boolean canPerform(World world);

    public abstract int perform(World world);

    public Story getStory() {
        return story;
    }

    public String getText() {
        return text;
    }
}
