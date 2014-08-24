package com.uprightpath.ld.thirty.story;

import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/23/2014.
 */
public class ItemRequiredAnswer extends Answer {

    private  String item;
    private  int quantity;

    public ItemRequiredAnswer() {}
    public ItemRequiredAnswer(Story story, String text, int exitCode, String item, int quantiy) {
        super(story, text, exitCode);
        this.item = item;
        this.quantity = quantiy;
    }

    @Override
    public boolean canPerform(World world) {
        return story.getWorldGroup().getCurrentWorld().getPlayer().getItem(item) >= quantity;
    }

    @Override
    public int perform(World world) {
        return exitCode;
    }
}
