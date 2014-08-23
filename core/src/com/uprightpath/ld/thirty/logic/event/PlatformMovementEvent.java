package com.uprightpath.ld.thirty.logic.event;

import com.badlogic.gdx.math.Vector2;
import com.uprightpath.ld.thirty.logic.Platform;
import com.uprightpath.ld.thirty.logic.World;
import com.uprightpath.ld.thirty.logic.WorldEvent;

/**
 * Created by Geo on 8/23/2014.
 */
public class PlatformMovementEvent implements WorldEvent {
    private final Vector2[] delta;
    private Platform platform;
    private int current = 0;

    public PlatformMovementEvent(Platform platform, Vector2[] delta) {
        this.platform = platform;
        this.delta = delta;
    }

    @Override
    public void trigger(World world) {
        platform.setDelta(delta[current]);
        current = (current + 1) % delta.length;
    }
}
