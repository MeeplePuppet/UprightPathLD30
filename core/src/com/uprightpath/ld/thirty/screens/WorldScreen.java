package com.uprightpath.ld.thirty.screens;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.uprightpath.ld.thirty.Main;
import com.uprightpath.ld.thirty.logic.Platform;
import com.uprightpath.ld.thirty.logic.World;
import com.uprightpath.ld.thirty.logic.agents.Player;
import com.uprightpath.ld.thirty.logic.controller.PlayerAgentController;
import com.uprightpath.ld.thirty.logic.event.PlatformMovementEvent;
import com.uprightpath.ld.thirty.logic.event.ReloadEvent;
import com.uprightpath.ld.thirty.logic.triggers.ExclusionTrigger;
import com.uprightpath.ld.thirty.logic.triggers.PolygonEventTrigger;
import com.uprightpath.ld.thirty.logic.triggers.TimerEventTrigger;
import com.uprightpath.ld.thirty.rendering.WorldRenderer;

/**
 * Created by Geo on 8/22/2014.
 */
public class WorldScreen extends GameScreen {
    private World world;
    private Player player;
    private WorldRenderer worldRenderer;
    private float tick = 1f / 60f;
    private float accum = 0f;

    public WorldScreen(Main main) {
        super(main);

        world = new World(main);
        player = new Player("Player", new Polygon(new float[]{0, 0, 1, 0, 1, 2, 0, 2}), new Polygon(new float[]{0, -.3f, 1, -.3f, 1, .3f, 0, .3f}));
        player.setPosition(2f, 2f);
        player.setAgentController(new PlayerAgentController(player));
        world.addAgent(player);

        Platform platform;
        PolygonEventTrigger polygonEventTrigger;
        TimerEventTrigger timerEventTrigger;


        platform = new Platform(world);
        platform.setSurface(new Vector2(-1, 1), new Vector2(0, 1), new Polygon((new float[]{-1, 0, 0, 0, 0, 1, -1, 1})));
        world.addPlatform(platform);

        timerEventTrigger = new TimerEventTrigger(world, new PlatformMovementEvent(platform, new Vector2[]{new Vector2(0, .2f), new Vector2(.2f, 0), new Vector2(0, 0), new Vector2(-.2f, 0), new Vector2(0, -.2f), new Vector2(0, 0)}));
        timerEventTrigger.setLooping(true);
        timerEventTrigger.setTimer(30);
        world.addWorldEventTrigger(timerEventTrigger);

        platform = new Platform(world);
        platform.setSurface(new Vector2(0, 1), new Vector2(10, 1), new Polygon((new float[]{0, 0, 10, 0, 10, 1, 0, 1})));
        world.addPlatform(platform);
        platform = new Platform(world);
        platform.setSurface(new Vector2(10, 1), new Vector2(12, 2), new Polygon((new float[]{10, 0, 12, 0, 12, 2, 10, 1})));
        world.addPlatform(platform);
        platform = new Platform(world);
        platform.setSurface(new Vector2(12, 1), new Vector2(14, 1), new Polygon((new float[]{12, 0, 14, 0, 14, 1, 12, 1})));
        world.addPlatform(platform);
        polygonEventTrigger = new ExclusionTrigger(world, new ReloadEvent());
        polygonEventTrigger.setPolygon(new Polygon(new float[]{-10, -10, 31, -10, 31, 31, -10, 31}));
        world.addEventTrigger(polygonEventTrigger);

        worldRenderer = new WorldRenderer(main, world, player);
    }

    @Override
    protected void renderImplement(float delta) {
        accum += delta;
        while (accum > tick) {
            accum -= tick;
            world.applyPhysics();
        }
        worldRenderer.render(delta);
    }
}
