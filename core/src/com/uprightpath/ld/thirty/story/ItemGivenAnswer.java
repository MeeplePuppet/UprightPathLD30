package com.uprightpath.ld.thirty.story;

import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/23/2014.
 */
public class ItemGivenAnswer extends Answer {

    private  String item;
    private  int quantity;

    public ItemGivenAnswer() {}
    public ItemGivenAnswer(Story story, String text, int exitCode, String item, int quantiy) {
        super(story, text, exitCode);
        this.item = item;
        this.quantity = quantiy;
    }

    @Override
    public boolean canPerform(World world) {
        return true;
    }

    @Override
    public int perform(World world) {
        story.getWorldGroup().getCurrentWorld().getPlayer().adjustItem(item, quantity);
        return exitCode;
    }
}
