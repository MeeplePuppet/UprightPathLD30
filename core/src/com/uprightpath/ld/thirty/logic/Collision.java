package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by Geo on 8/22/2014.
 */
public class Collision {
    private final World world;
    private Polygon polygon;
    private Vector2 delta = new Vector2();
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

    public Vector2 getDelta() {
        return delta;
    }

    public void setPosition(Vector2 delta) {
        setPosition(delta.x, delta.y);
    }

    public void setPosition(float x, float y) {
        polygon.setPosition(x, y);
    }

    public void translate(Vector2 delta) {
        translate(delta.x, delta.y);
    }

    public void translate(float x, float y) {
        polygon.translate(x, y);
    }

    public void setDelta(Vector2 delta) {
        this.delta = delta;
    }
}
