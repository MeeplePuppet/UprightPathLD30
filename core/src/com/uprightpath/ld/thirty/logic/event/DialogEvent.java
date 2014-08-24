package com.uprightpath.ld.thirty.logic.event;

import com.uprightpath.ld.thirty.logic.GlobalEvent;
import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/23/2014.
 */
public class DialogEvent implements GlobalEvent {

    private int dialogEvent;

    public DialogEvent() {
    }

    public DialogEvent(int dialogEvent) {
        this.dialogEvent = dialogEvent;
    }

    public void setDialogEvent(int dialogEvent) {
        this.dialogEvent = dialogEvent;
    }

    @Override
    public void trigger(World world) {
        world.getWorldGroup().enterDialog(dialogEvent);
    }
}
