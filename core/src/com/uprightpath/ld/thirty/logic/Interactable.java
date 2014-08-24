package com.uprightpath.ld.thirty.logic;

import com.uprightpath.ld.thirty.story.Story;

/**
 * Created by Geo on 8/23/2014.
 */
public interface Interactable {
    public String getName();

    public int getTextureId();

    public Story getStory();

    public void interact(World world, Agent agent);
}
