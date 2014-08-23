package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;


/**
 * Created by Geo on 8/22/2014.
 */
public class Collision extends WorldObject {
    private boolean collidable = true;

    public Collision(Polygon polygon) {
        super(polygon);
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public boolean canCollide(Agent agent) {
        return collidable && Intersector.overlaps(agent.getPolygon().getBoundingRectangle(), polygon.getBoundingRectangle());
    }

    public void updatePosition() {
        polygon.setPosition(position.x, position.y);
    }
}
