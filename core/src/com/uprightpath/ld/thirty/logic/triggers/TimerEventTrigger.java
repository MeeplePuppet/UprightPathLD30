package com.uprightpath.ld.thirty.logic.triggers;

import com.uprightpath.ld.thirty.logic.World;
import com.uprightpath.ld.thirty.logic.WorldEvent;
import com.uprightpath.ld.thirty.logic.WorldEventTrigger;

/**
 * Created by Geo on 8/23/2014.
 */
public class TimerEventTrigger extends WorldEventTrigger {
    private int timer;
    private int time;
    private boolean looping;

    public TimerEventTrigger(World world) {
        super(world);
    }

    public TimerEventTrigger(World world, WorldEvent worldEvent) {
        super(world, worldEvent);
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
