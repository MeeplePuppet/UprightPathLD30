package com.uprightpath.ld.thirty.logic.event;

import com.badlogic.gdx.math.Vector2;
import com.uprightpath.ld.thirty.logic.*;

/**
 * Created by Geo on 8/24/2014.
 */
public class PositionWorldObjectEvent implements GlobalEvent, WorldEvent {
    private Vector2 position;
    private WorldObject worldObject;

    public PositionWorldObjectEvent() {
    }

    public PositionWorldObjectEvent(WorldObject worldObject, Vector2 position) {
        this.worldObject = worldObject;
        this.position = position;
    }

    @Override
    public void trigger(World world) {
        triggered(world);
    }

    private void triggered(World world) {
        worldObject.setPosition(position);
    }

    @Override
    public void trigger(World world, Agent agent) {
        triggered(world);
    }
}
