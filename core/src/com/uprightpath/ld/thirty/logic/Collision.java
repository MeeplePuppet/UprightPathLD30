package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;


/**
 * Created by Geo on 8/22/2014.
 */
public class Collision {
    private final World world;
    private Polygon polygon;
    private boolean collidable = true;

    public Collision(World world) {
        this.world = world;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public boolean canCollide(Agent agent) {
        return collidable && Intersector.overlaps(agent.getPolygonCollision().getBoundingRectangle(), polygon.getBoundingRectangle());
    }

    public Polygon getPolygon() {
        return polygon;
    }
}
