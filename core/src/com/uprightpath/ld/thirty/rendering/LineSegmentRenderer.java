package com.uprightpath.ld.thirty.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Geo on 8/23/2014.
 */
public class LineSegmentRenderer {
    public final Vector2 start;
    public final Vector2 end;
    public final Color color;

    public LineSegmentRenderer(Vector2 start, Vector2 end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }
}
