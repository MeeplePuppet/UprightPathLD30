package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Geo on 8/22/2014.
 */
public class WorldObject {
    private final World world;
    private Array<Polygon> polygons = new Array<Polygon>();
    private Rectangle boundingRectangle;
    private boolean collidable;

    public WorldObject(World world) {
        this.world = world;
        this.boundingRectangle = new Rectangle();
    }

    public void addPolygon(Polygon polygon) {
        this.polygons.add(polygon);
        if (polygons.size == 1) {
            this.boundingRectangle.set(polygon.getBoundingRectangle());
        } else {
            boundingRectangle.x = Math.min(boundingRectangle.x, polygon.getBoundingRectangle().x);
            boundingRectangle.y = Math.min(boundingRectangle.y, polygon.getBoundingRectangle().y);
            boundingRectangle.width = Math.max(boundingRectangle.width, polygon.getBoundingRectangle().width);
            boundingRectangle.height = Math.max(boundingRectangle.height, polygon.getBoundingRectangle().height);
        }
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public boolean canCollide(Polygon polygon) {
        return collidable && Intersector.overlaps(polygon.getBoundingRectangle(), boundingRectangle);
    }

    public Array<Polygon> getPolygons() {
        return polygons;
    }
}
