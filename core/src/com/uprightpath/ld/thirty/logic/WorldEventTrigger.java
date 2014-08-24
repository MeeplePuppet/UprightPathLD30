package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Polygon;
import com.uprightpath.ld.thirty.rendering.Renderable;
import com.uprightpath.ld.thirty.rendering.Renderer;

/**
 * Created by Geo on 8/22/2014.
 */
public abstract class WorldEventTrigger extends WorldObject implements Renderable {
    protected WorldEvent event;
    private Renderer renderer;
    public WorldEventTrigger() {}

    public WorldEventTrigger(Polygon polygon) {
        super(polygon);
    }

    public abstract boolean canTriggerEvent(Agent agent);

    public void triggerEvent(Agent agent) {
        event.trigger(agent);
    }

    public void setEvent(WorldEvent event) {
        this.event = event;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }


    @Override
    public void updatePosition() {
        polygon.setPosition(position.x, position.y);
        if (renderer != null) {
            renderer.setPosition(position.x, position.y);
        }
    }
}
