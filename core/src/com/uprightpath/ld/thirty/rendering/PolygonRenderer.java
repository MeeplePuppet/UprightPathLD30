package com.uprightpath.ld.thirty.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;

/**
 * Created by Geo on 8/23/2014.
 */
public class PolygonRenderer implements Renderer {
    private Polygon polygon[];
    private Color colorFront;
    private Color colorBack;

    public PolygonRenderer() {
    }

    public PolygonRenderer(Polygon polygon, Color color) {
        this.polygon = new Polygon[]{polygon};
        this.colorFront = color;
        this.colorBack = new Color(colorFront.r, colorFront.g, colorFront.b, .2f);
    }

    public PolygonRenderer(Polygon[] polygon, Color color) {
        this.polygon = polygon;
        this.colorFront = color;
        this.colorBack = new Color(colorFront.r, colorFront.g, colorFront.b, .2f);
    }

    public void setPosition(float x, float y) {
        for (int i = 0; i < polygon.length; i++) {
            polygon[i].setPosition(x, y);
        }
    }

    public void render(float delta, ShapeRenderer shapeRenderer, boolean front) {
        shapeRenderer.setColor(front ? colorFront : colorBack);
        for (int i = 0; i < polygon.length; i++) {
            shapeRenderer.polygon(polygon[i].getTransformedVertices());
        }
    }
}
