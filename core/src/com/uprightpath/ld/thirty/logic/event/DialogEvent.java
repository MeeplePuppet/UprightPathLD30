package com.uprightpath.ld.thirty.logic.event;

import com.uprightpath.ld.thirty.logic.GlobalEvent;
import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/23/2014.
 */
public class DialogEvent implements GlobalEvent {

    private World world;
    private int dialogEvent;

    public DialogEvent() {}

    public DialogEvent(World world, int dialogEvent) {
        this.world = world;
        this.dialogEvent = dialogEvent;
    }

    public void setDialogEvent(int dialogEvent) {
        this.dialogEvent = dialogEvent;
    }

    @Override
    public void trigger() {
        world.getWorldGroup().enterDialog(dialogEvent);
    }
}
