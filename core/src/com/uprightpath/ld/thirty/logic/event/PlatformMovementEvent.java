package com.uprightpath.ld.thirty.logic.event;

import com.badlogic.gdx.math.Vector2;
import com.uprightpath.ld.thirty.logic.GlobalEvent;
import com.uprightpath.ld.thirty.logic.Platform;

/**
 * Created by Geo on 8/23/2014.
 */
public class PlatformMovementEvent implements GlobalEvent {
    private  Vector2[] delta;
    private Platform platform;
    private int current = 0;

    public PlatformMovementEvent() {}

    public PlatformMovementEvent(Platform platform, Vector2[] delta) {
        this.platform = platform;
        this.delta = delta;
    }

    @Override
    public void trigger() {
        platform.setDelta(delta[current]);
        current = (current + 1) % delta.length;
    }
}
