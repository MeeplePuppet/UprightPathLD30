package com.uprightpath.ld.thirty.logic.event;

import com.uprightpath.ld.thirty.logic.GlobalEvent;
import com.uprightpath.ld.thirty.logic.World;
import com.uprightpath.ld.thirty.story.Story;

/**
 * Created by Geo on 8/23/2014.
 */
public class DialogEvent implements GlobalEvent {

    private Story story;
    private int dialogEvent;

    public DialogEvent() {
    }

    public DialogEvent(Story story, int dialogEvent) {
        this.story = story;
        this.dialogEvent = dialogEvent;
    }

    public void setDialogEvent(Story story, int dialogEvent) {
        this.story = story;
        this.dialogEvent = dialogEvent;
    }

    @Override
    public void trigger(World world) {
        story.setDialog(dialogEvent);
    }
}
