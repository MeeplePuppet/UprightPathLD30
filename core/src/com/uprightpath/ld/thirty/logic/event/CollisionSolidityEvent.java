package com.uprightpath.ld.thirty.logic.event;

import com.uprightpath.ld.thirty.logic.*;

/**
 * Created by Geo on 8/24/2014.
 */
public class CollisionSolidityEvent implements GlobalEvent, WorldEvent {

    private boolean solidity;
    private Collision collision;

    public CollisionSolidityEvent() {
    }

    public CollisionSolidityEvent(Collision collision, boolean solidity) {
        this.collision = collision;
        this.solidity = solidity;
    }

    @Override
    public void trigger(World world) {
        triggerEvent(world);
    }

    private void triggerEvent(World world) {
        if (solidity) {
            world.addCollision(collision);
        } else {
            world.removeCollision(collision);
        }
    }

    @Override
    public void trigger(World world, Agent agent) {
        triggerEvent(world);
    }
}
