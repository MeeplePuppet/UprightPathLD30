package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Geo on 8/22/2014.
 */
public class Agent {
    private String name;
    private Polygon polygonCollision;
    private Polygon polygonBase;
    private Vector2 position;
    private Vector2 delta;
    private AgentController agentController;
    private Platform platform;

    public Agent(String name, Polygon polygonCollision, Polygon polygonBase) {
        this.name = name;
        this.polygonCollision = new Polygon(polygonCollision.getVertices());
        this.polygonBase = new Polygon(polygonBase.getVertices());
        this.position = new Vector2();
        delta = new Vector2();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.setPosition(position.x, position.y);
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
        this.polygonCollision.setPosition(position.x, position.y);
        this.polygonBase.setPosition(position.x, position.y);
    }

    public void translate(Vector2 change) {
        this.translate(change.x, change.y);
    }

    public void translate(float x, float y) {
        this.position.add(x, y);
        this.polygonCollision.translate(x, y);
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

    public Polygon getPolygonCollision() {
        return polygonCollision;
    }

    public void applyDelta(Vector2 delta) {
        this.applyDelta(delta.x, delta.y);
    }

    public void applyDelta(float x, float y) {
        this.delta.add(x, y);
        if (delta.x > .2f) {
            delta.x = .2f;
        } else if (delta.x < -.2f) {
            delta.x = -.2f;
        }
        if (delta.y < -1) {
            delta.y = -1;
        }
    }

    public void setAgentController(AgentController agentController) {
        this.agentController = agentController;
    }

    public AgentController getAgentController() {
        return agentController;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void applyPlatform() {
        if (platform != null) {
            this.translate(platform.getDelta());
            delta.x *= platform.getFriction();
            if (Math.abs(delta.x) < .01f) {
                delta.x = 0;
            }
        }
    }
}
