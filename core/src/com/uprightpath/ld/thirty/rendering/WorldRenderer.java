package com.uprightpath.ld.thirty.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.uprightpath.ld.thirty.Main;
import com.uprightpath.ld.thirty.logic.Agent;
import com.uprightpath.ld.thirty.logic.Collision;
import com.uprightpath.ld.thirty.logic.Platform;
import com.uprightpath.ld.thirty.logic.World;

/**
 * Created by Geo on 8/22/2014.
 */
public class WorldRenderer {
    private final Main main;
    private final World world;
    private final ShapeRenderer shapeRenderer;
    private final Vector2 position = new Vector2();
    private OrthographicCamera camera;
    private boolean front;

    public WorldRenderer(Main main, World world) {
        this.main = main;
        this.world = world;
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth() / Gdx.graphics.getHeight() * main.getUnits(), main.getUnits());
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
        float alpha;
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        if (front) {
            Gdx.gl.glLineWidth(1);
            alpha = 1;
        } else {
            Gdx.gl.glLineWidth(3);
            alpha = .2f;
        }
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Collision collision;
        shapeRenderer.setColor(0, 1, 0, alpha);
        for (int i = 0; i < world.getCollisions().size; i++) {
            collision = world.getCollisions().get(i);
            shapeRenderer.polygon(collision.getPolygon().getTransformedVertices());
        }

        Platform platform;
        shapeRenderer.setColor(Color.BLUE);
        for (int i = 0; i < world.getPlatforms().size; i++) {
            platform = world.getPlatforms().get(i);
            shapeRenderer.setColor(0, 1, 0, alpha);
            shapeRenderer.polygon(platform.getPolygon().getTransformedVertices());
            shapeRenderer.setColor(0, .5f, .5f, alpha);
            shapeRenderer.polygon(platform.getPolygonPlatform().getTransformedVertices());
        }

        Agent agent;
        for (int i = 0; i < world.getAgents().size; i++) {
            agent = world.getAgents().get(i);
            if (world.getPlayer() == agent) {
                shapeRenderer.setColor(0, 0, 1, alpha);
            } else {
                shapeRenderer.setColor(1, 0, 0, alpha);
            }
            shapeRenderer.polygon(agent.getPolygon().getTransformedVertices());
            shapeRenderer.setColor(0, .5f, 1f, alpha);
            shapeRenderer.polygon(agent.getPolygonBase().getTransformedVertices());
            shapeRenderer.setColor(1, .5f, .5f, alpha);
            if (agent.getPlatform() != null) {
                shapeRenderer.polygon(agent.getPlatform().getPolygonPlatform().getTransformedVertices());
            }
        }
        shapeRenderer.end();
    }

    public void updateCamera(Vector3 update) {
        camera.position.add(update);
    }
}
