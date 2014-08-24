package com.uprightpath.ld.thirty.rendering;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Geo on 8/23/2014.
 */
public interface Renderer {
    public void setPosition(float x, float y);

    public void render(float delta, ShapeRenderer shapeRenderer, boolean front);
}
