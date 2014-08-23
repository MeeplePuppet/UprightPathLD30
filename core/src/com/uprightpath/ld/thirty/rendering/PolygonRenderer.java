package com.uprightpath.ld.thirty.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by Geo on 8/23/2014.
 */
public class PolygonRenderer {
    public final Polygon polygon;
    public final Color color;

    public PolygonRenderer(Polygon polygon, Color color) {
        this.polygon = polygon;
        this.color = color;
    }
}
