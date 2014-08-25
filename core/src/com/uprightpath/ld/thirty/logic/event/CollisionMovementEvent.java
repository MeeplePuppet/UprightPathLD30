package com.uprightpath.ld.thirty.logic.event;

import com.badlogic.gdx.math.Vector2;
import com.uprightpath.ld.thirty.logic.Collision;
import com.uprightpath.ld.thirty.logic.GlobalEvent;
import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/23/2014.
 */
public class CollisionMovementEvent implements GlobalEvent {
    private Vector2[] delta;
    private Collision collision;
    private int current = 0;

    public CollisionMovementEvent() {
    }

    public CollisionMovementEvent(Collision collision, Vector2[] delta) {
        this.collision = collision;
        this.delta = delta;
    }

    @Override
    public void trigger(World world) {
        collision.setDelta(delta[current]);
        current = (current + 1) % delta.length;
    }
}
