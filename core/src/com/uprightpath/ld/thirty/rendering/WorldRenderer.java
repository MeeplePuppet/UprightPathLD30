package com.uprightpath.ld.thirty.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/22/2014.
 */
public class WorldRenderer {
    private World world;
    private ShapeRenderer shapeRenderer;
    private Vector2 position = new Vector2();
    private OrthographicCamera camera;
    private boolean front;

    public WorldRenderer() {
    }

    public WorldRenderer(World world) {
        this.world = world;
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth() / Gdx.graphics.getHeight() * 10, 10);
        this.camera.translate(0, 0, 10);
        this.camera.lookAt(0, 0, 0);
    }

    public void setFront(boolean front) {
        this.front = front;
    }

    public Vector3 getCamera() {
        world.getPlayer().getPolygon().getBoundingRectangle().getCenter(position);
        Vector3 previous = new Vector3(camera.position);
        camera.position.set(position.x, position.y, 10);
        previous.sub(camera.position);
        return previous.scl(-1);
    }

    public void render(float delta) {
        camera.update();
        if (front) {
            Gdx.gl.glLineWidth(1);
        } else {
            Gdx.gl.glLineWidth(5);
        }
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < world.getRenderables().size; i++) {
            world.getRenderables().get(i).getRenderer().render(delta, shapeRenderer, front);
        }
        shapeRenderer.end();
    }

    public void updateCamera(Vector3 update) {
        camera.position.add(update);
    }

    public void dispose() {
        this.shapeRenderer.dispose();
    }
}
