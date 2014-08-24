package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.uprightpath.ld.thirty.rendering.Renderable;
import com.uprightpath.ld.thirty.rendering.Renderer;


/**
 * Created by Geo on 8/22/2014.
 */
public class Platform extends Collision implements Renderable {
    private float friction = .7f;
    private Vector2 start;
    private Vector2 end;
    private Vector2 initStart = new Vector2();
    private Vector2 initEnd = new Vector2();
    private Vector2 normal;
    private Polygon polygonPlatform;
    private Renderer renderer;

    public Platform() {
    }

    public Platform(Polygon polygon) {
        super(polygon);
        normal = new Vector2();
    }

    public void setSurface(Vector2 start, Vector2 end) {
        this.start = start;
        this.end = end;
        this.initStart.set(start);
        this.initEnd.set(end);
        this.normal.set(-(end.y - start.y), end.x - start.x);
        polygonPlatform = new Polygon(new float[]{start.x, start.y, start.x, start.y - .2f, end.x, end.y - .2f, end.x, end.y});
    }

    public Vector2 getPosition(Agent agent) {
        if (!Intersector.overlapConvexPolygons(agent.getPolygonBase(), polygonPlatform) || agent.getDelta().dot(normal) > 0 && agent.getDelta().y > 0) {
            return null;
        }
        float minX = agent.getPolygon().getBoundingRectangle().x;
        float maxX = minX + agent.getPolygon().getBoundingRectangle().width;
        Vector2 left = new Vector2();
        Vector2 right = new Vector2();
        if (minX >= start.x && maxX <= end.x) {
            Intersector.intersectLines(minX, 0, minX, 1, start.x, start.y, end.x, end.y, left);
            Intersector.intersectLines(maxX, 0, maxX, 1, start.x, start.y, end.x, end.y, right);
            left.x = agent.getPolygon().getBoundingRectangle().x;
            right.x = agent.getPolygon().getBoundingRectangle().x;
            return right.y > left.y ? right : left;
        } else if (maxX >= start.x && maxX <= end.x) {
            Intersector.intersectLines(start.x, 0, start.x, 1, start.x, start.y, end.x, end.y, left);
            Intersector.intersectLines(maxX, 0, maxX, 1, start.x, start.y, end.x, end.y, right);
            left.x = agent.getPolygon().getBoundingRectangle().x;
            right.x = agent.getPolygon().getBoundingRectangle().x;
            return right.y > left.y ? right : left;
        } else if (minX <= end.x && minX >= start.x) {
            Intersector.intersectLines(minX, 0, minX, 1, start.x, start.y, end.x, end.y, left);
            Intersector.intersectLines(end.x, 0, end.x, 1, start.x, start.y, end.x, end.y, right);
            left.x = agent.getPolygon().getBoundingRectangle().x;
            right.x = agent.getPolygon().getBoundingRectangle().x;
            return right.y > left.y ? right : left;
        } else {
            return null;
        }
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public Polygon getPolygonPlatform() {
        return polygonPlatform;
    }

    public void updatePosition() {
        polygonPlatform.setPosition(position.x, position.y);
        polygon.setPosition(position.x, position.y);
        start.set(position).add(initStart);
        end.set(position).add(initEnd);
        if (renderer != null) {
            renderer.setPosition(position.x, position.y);
        }
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
