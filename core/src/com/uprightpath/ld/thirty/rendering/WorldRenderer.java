package com.uprightpath.ld.thirty.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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
    private final Agent agent;
    private final ShapeRenderer shapeRenderer;
    private final Vector2 position = new Vector2();
    private OrthographicCamera camera;

    public WorldRenderer(Main main, World world, Agent agent) {
        this.main = main;
        this.world = world;
        this.agent = agent;
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth() / Gdx.graphics.getHeight() * main.getUnits(), main.getUnits());
        this.camera.translate(0, 0, 10);
        this.camera.lookAt(0, 0, 0);
    }

    public void render(float delta) {
        agent.getPolygonCollision().getBoundingRectangle().getCenter(position);
        System.out.println(position);
        camera.position.set(position.x, position.y, 10);
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Collision collision;
        shapeRenderer.setColor(Color.BLUE);
        for (int i = 0; i < world.getCollisions().size; i++) {
            collision = world.getCollisions().get(i);
            shapeRenderer.polygon(collision.getPolygon().getTransformedVertices());
        }

        Platform platform;
        shapeRenderer.setColor(Color.BLUE);
        for (int i = 0; i < world.getPlatforms().size; i++) {
            platform = world.getPlatforms().get(i);
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.polygon(platform.getPolygon().getTransformedVertices());

            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.polygon(platform.getPolygonPlatform().getTransformedVertices());
        }

        Agent agent;
        for (int i = 0; i < world.getAgents().size; i++) {
            agent = world.getAgents().get(i);
            if (agent.getPlatform() == null) {
                shapeRenderer.setColor(Color.RED);
            } else {
                shapeRenderer.setColor(Color.ORANGE);
            }
            shapeRenderer.polygon(agent.getPolygonCollision().getTransformedVertices());
            shapeRenderer.setColor(Color.MAGENTA);
            shapeRenderer.polygon(agent.getPolygonBase().getTransformedVertices());
            shapeRenderer.setColor(Color.PURPLE);
            if (agent.getPlatform() != null) {
                shapeRenderer.polygon(agent.getPlatform().getPolygon().getTransformedVertices());
            }
        }
        shapeRenderer.end();
    }
}
