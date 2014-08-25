package com.uprightpath.ld.thirty.logic.triggers;

import com.uprightpath.ld.thirty.logic.GlobalEvent;
import com.uprightpath.ld.thirty.logic.GlobalEventTrigger;
import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/23/2014.
 */
public class TimerEventTrigger extends GlobalEventTrigger {
    private int[] timer;
    private int time;
    private int current = 0;
    private boolean looping = true;

    public TimerEventTrigger() {

    }

    public TimerEventTrigger(GlobalEvent globalEvent) {
        super(globalEvent);
    }

    @Override
    public boolean remove() {
        return !looping && current >= timer.length;
    }

    public void setTimer(int[] timer) {
        this.timer = timer;
        current = 0;
        time = timer[current];
    }

    public void reset() {
        current = 0;
        time = timer[current];
    }

    public boolean getLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public boolean canTriggerEvent(World world) {
        time -= 1;
        if (time == 0) {
            if (looping) {
                current = (current + 1) % timer.length;
            } else {
                current = (current + 1);
            }
            time = timer[current % timer.length];
            return current < timer.length;
        }
        return false;
    }

}
