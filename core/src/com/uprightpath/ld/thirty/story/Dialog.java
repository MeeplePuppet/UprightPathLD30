package com.uprightpath.ld.thirty.story;

import com.badlogic.gdx.utils.Array;
import com.uprightpath.ld.thirty.logic.Agent;

/**
 * Created by Geo on 8/23/2014.
 */
public abstract class Dialog {
    private Agent agent;
    private String dialog;
    private Array<String> answers;

    public Dialog(Agent agent, String dialog, Array<String> answers) {
        this.agent = agent;
        this.dialog = dialog;
        this.answers = answers;
    }

    public abstract boolean canPerform(int answer);

    public abstract int perform(int answer);
}
