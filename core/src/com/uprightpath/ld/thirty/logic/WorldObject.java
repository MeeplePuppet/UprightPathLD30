package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Geo on 8/23/2014.
 */
public abstract class WorldObject {
    protected Polygon polygon;
    protected Vector2 position = new Vector2();
    protected Vector2 delta = new Vector2();

    public WorldObject(Polygon polygon) {
        this.polygon = polygon;
    }


    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Vector2 getDelta() {
        return delta;
    }

    public void setPosition(Vector2 delta) {
        setPosition(delta.x, delta.y);
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
        updatePosition();
    }

    public void translate(Vector2 delta) {
        translate(delta.x, delta.y);
    }

    public void translate(float x, float y) {
        position.add(x, y);
        updatePosition();
    }

    public void setDelta(Vector2 delta) {
        this.setDelta(delta.x, delta.y);
    }

    public void setDelta(float x, float y) {
        this.delta.set(x, y);
    }


    public Vector2 getPosition() {
        return position;
    }

    public abstract void updatePosition();
}
