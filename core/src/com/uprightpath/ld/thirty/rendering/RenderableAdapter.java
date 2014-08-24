package com.uprightpath.ld.thirty.rendering;

/**
 * Created by Geo on 8/23/2014.
 */
public class RenderableAdapter implements Renderable {
    private Renderer renderer;

    public RenderableAdapter() {}

    public RenderableAdapter(Renderer renderer) {
        this.renderer = renderer;
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
