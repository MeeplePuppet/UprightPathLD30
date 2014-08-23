package com.uprightpath.ld.thirty.story;

import com.badlogic.gdx.utils.IntMap;
import com.uprightpath.ld.thirty.logic.WorldGroup;

/**
 * Created by Geo on 8/23/2014.
 */
public class Story {
    private final WorldGroup worldGroup;
    private IntMap<Dialog> dialogs = new IntMap<Dialog>();
    private int currentDialog = 0;

    public Story(WorldGroup worldGroup) {
        this.worldGroup = worldGroup;
    }

    public void addDialog(int id, Dialog dialog) {
        this.dialogs.put(id, dialog);
    }

    public boolean canPerform(int answer) {
        return dialogs.get(currentDialog).canPerform(answer);
    }

    public void perform(int answer) {
        currentDialog = dialogs.get(currentDialog).perform(answer);
        if (currentDialog == 0) {
            worldGroup.exitDialog();
        }
    }

    public void setDialog(int id) {
        currentDialog = id;
    }
}
