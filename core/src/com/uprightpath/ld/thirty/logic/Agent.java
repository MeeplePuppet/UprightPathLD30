package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Geo on 8/22/2014.
 */
public class Agent {
    private String name;
    private Polygon polygon;
    private Vector2 position;
    private Vector2 delta;
    private boolean falling;

    public Agent(String name, Polygon polygon) {
        this.name = name;
        this.polygon = new Polygon(polygon.getVertices());
        this.position = new Vector2();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
        this.polygon.setPosition(position.x, position.y);
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.polygon.setPosition(position.x, position.y);
    }

    public void translate(Vector2 change) {
        this.position.add(change);
        this.polygon.translate(change.x, change.y);
    }

    public void translate(float x, float y) {
        this.position.add(x, y);
        this.polygon.translate(x, y);
    }

    public Vector2 getDelta() {
        return delta;
    }

    public void setDelta(Vector2 delta) {
        this.delta.set(delta);
    }

    public void setDelta(float x, float y) {
        this.delta.set(x, y);
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public Polygon getPolygon() {
        return polygon;
    }
}
