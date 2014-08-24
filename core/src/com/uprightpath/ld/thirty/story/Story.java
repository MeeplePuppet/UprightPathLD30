package com.uprightpath.ld.thirty.story;

import com.badlogic.gdx.utils.IntMap;
import com.uprightpath.ld.thirty.logic.WorldGroup;

/**
 * Created by Geo on 8/23/2014.
 */
public class Story {
    private  WorldGroup worldGroup;
    private IntMap<Dialog> dialogs = new IntMap<Dialog>();
    private int currentDialog = 0;

    public Story() {}
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
        int exitCode = dialogs.get(currentDialog).perform(answer);
        if (exitCode != 0) {
            currentDialog = Math.abs(exitCode);
        }
        if (exitCode <= 0) {
            worldGroup.exitDialog();
        }
    }

    public Dialog getDialog() {
        return dialogs.get(currentDialog);
    }

    public void setDialog(int id) {
        currentDialog = id;
    }

    public WorldGroup getWorldGroup() {
        return worldGroup;
    }
}
