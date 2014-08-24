package com.uprightpath.ld.thirty.story;

import com.badlogic.gdx.utils.Array;
import com.uprightpath.ld.thirty.logic.Interactable;

/**
 * Created by Geo on 8/23/2014.
 */
public class Dialog {
    private Story story;
    private Interactable interactable;
    private String dialog;
    private Array<Answer> answers;

    public Dialog() {}

    public Dialog(Story story, Interactable interactable, String dialog, Array<Answer> answers) {
        this.story = story;
        this.interactable = interactable;
        this.dialog = dialog;
        this.answers = answers;
    }

    public Array<Answer> getAnswers() {
        return answers;
    }

    public boolean canPerform(int answer) {
        return answers.get(answer).canPerform(story.getWorldGroup().getCurrentWorld());
    }

    public int perform(int answer) {
        return answers.get(answer).perform(story.getWorldGroup().getCurrentWorld());
    }

    public Interactable getInteractable() {
        return interactable;
    }

    public String getDialog() {
        return dialog;
    }
}
