package com.uprightpath.ld.thirty.logic.triggers;

import com.uprightpath.ld.thirty.logic.GlobalEventTrigger;

/**
 * Created by Geo on 8/23/2014.
 */
public class TimerEventTrigger extends GlobalEventTrigger {
    private int timer;
    private int time;
    private boolean looping;

    public TimerEventTrigger() {

    }


    public boolean getLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
        time = timer;
    }

    public boolean canTriggerEvent() {
        if (looping && time == 0) {
            time = timer;
        }
        time--;
        return time == 0;
    }

}
