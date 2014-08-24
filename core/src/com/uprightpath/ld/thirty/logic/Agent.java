package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.uprightpath.ld.thirty.rendering.Renderable;
import com.uprightpath.ld.thirty.rendering.Renderer;

/**
 * Created by Geo on 8/22/2014.
 */
public abstract class Agent extends WorldObject implements Renderable {
    protected String name;
    protected Polygon polygonBase;
    protected Vector2 maxDelta;
    protected Vector2 movement;
    protected boolean fallingThrough = false;
    protected boolean moving = false;
    protected AgentController agentController;
    protected Platform platform;
    private int jumpTime;
    private Renderer renderer;

    public Agent() {

    }

    public Agent(String name, Polygon polygon, Polygon polygonBase) {
        super(polygon);
        this.name = name;
        this.polygonBase = new Polygon(polygonBase.getVertices());
    }

    public void updatePosition() {
        this.polygon.setPosition(position.x, position.y);
        this.polygonBase.setPosition(position.x, position.y);
        if (renderer != null) {
            renderer.setPosition(position.x, position.y);
        }
    }

    public void applyDelta(Vector2 delta) {
        this.applyDelta(delta.x, delta.y);
    }

    public void applyDelta(float x, float y) {
        this.delta.add(x, y);
    }

    public void applyDeltaLimits() {
        if (delta.x > maxDelta.x) {
            delta.x = maxDelta.x;
        } else if (delta.x < -maxDelta.x) {
            delta.x = -maxDelta.x;
        }
        if (delta.y > maxDelta.y) {
            delta.y = maxDelta.y;
        } else if (delta.y < -maxDelta.y) {
            delta.y = -maxDelta.y;
        }
    }

    public AgentController getAgentController() {
        return agentController;
    }

    public void setAgentController(AgentController agentController) {
        this.agentController = agentController;
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
            if (!moving) {
                delta.x *= platform.getFriction();
                if (Math.abs(delta.x) < .01f) {
                    delta.x = 0;
                }
            }
        }
    }

    public Polygon getPolygonBase() {
        return polygonBase;
    }

    public boolean isFallingThrough() {
        return fallingThrough;
    }

    public void setFallingThrough(boolean fallingThrough) {
        this.fallingThrough = fallingThrough;
    }

    public Vector2 getMaxDelta() {
        return maxDelta;
    }

    public void setMaxDelta(Vector2 maxDelta) {
        this.maxDelta = maxDelta;
    }

    public Vector2 getMovement() {
        return movement;
    }

    public void setMovement(Vector2 movement) {
        this.movement = movement;
    }

    public int getJumpTime() {
        return jumpTime;
    }

    public void setJumpTime(int jumpTime) {
        this.jumpTime = jumpTime;
    }

    public abstract boolean collides(Agent agent);

    public abstract void collidedWith(Agent agent);

    public String getName() {
        return name;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    @Override
    public Renderer getRenderer() {
        return renderer;
    }

    @Override
    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}
