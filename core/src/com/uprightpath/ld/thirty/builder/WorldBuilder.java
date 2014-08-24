package com.uprightpath.ld.thirty.builder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.uprightpath.ld.thirty.logic.*;
import com.uprightpath.ld.thirty.logic.agents.PlayerAgent;
import com.uprightpath.ld.thirty.logic.controller.PlayerAgentController;
import com.uprightpath.ld.thirty.logic.event.ItemEvent;
import com.uprightpath.ld.thirty.logic.interactables.WorldInteractable;
import com.uprightpath.ld.thirty.logic.triggers.InclusionTrigger;
import com.uprightpath.ld.thirty.logic.triggers.TimerEventTrigger;
import com.uprightpath.ld.thirty.rendering.PolygonRenderer;
import com.uprightpath.ld.thirty.rendering.RenderableAdapter;
import com.uprightpath.ld.thirty.story.*;

/**
 * Created by Geo on 8/23/2014.
 */
public class WorldBuilder {
    private IntMap<World> worlds = new IntMap<World>();
    private IntMap<Agent> agents = new IntMap<Agent>();
    private IntMap<Collision> collisions = new IntMap<Collision>();
    private IntMap<Platform> platforms = new IntMap<Platform>();
    private IntMap<WorldInteractable> collisionInteractables = new IntMap<WorldInteractable>();
    private IntMap<GlobalEventTrigger> globalEventTriggers = new IntMap<GlobalEventTrigger>();
    private IntMap<GlobalEvent> globalEvents = new IntMap<GlobalEvent>();
    private IntMap<WorldEventTrigger> worldEventTriggers = new IntMap<WorldEventTrigger>();
    private IntMap<WorldEvent> worldEvents = new IntMap<WorldEvent>();
    private IntMap<Story> stories = new IntMap<Story>();
    private IntMap<Dialog> dialogs = new IntMap<Dialog>();
    private IntMap<Answer> answers = new IntMap<Answer>();

    public WorldBuilder() {

    }


    public static WorldGroup buildWorldGroup() {
        WorldGroup worldGroup = new WorldGroup();

        PlayerAgent player = new PlayerAgent("Player", new Polygon(new float[]{0, 0, .7f, 0, .7f, 1.6f, 0, 1.6f}), new Polygon(new float[]{0, -.3f, .7f, -.3f, .7f, .3f, 0, .3f}));
        player.setRenderer(new PolygonRenderer(new Polygon(new float[]{0, 0, .7f, 0, .7f, 1.6f, 0, 1.6f}), Color.GREEN));
        player.setPosition(2.5f, 0f);
        player.setAgentController(new PlayerAgentController(player));
        player.setMaxDelta(new Vector2(.2f, 1f));
        player.setMovement(new Vector2(.05f, .38f));
        player.setJumpTime(30);


        World world = new World(worldGroup, player, "Man of Blue: Sewer");
        world.setWorldDelta(new Vector2(0f, -.04f));
        world.addRenderable(new RenderableAdapter(new PolygonRenderer(new Polygon(new float[]{0, 2, 1, 2, 1, 1, 2, 0, 4, 0, 5, 1, 5, 2, 15, 2, 15, 10, 0, 10}), Color.BLUE)));


        world.addRenderable(player);

        Story story;
        Answer answer;
        Array<Answer> answers;
        WorldInteractable interactable;
        story = new Story(worldGroup);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 0));
        story.addDialog(1, new Dialog(story, player, "I feel as though\rthis world's my foe!", answers));
        player.setStory(story);

        interactable = new WorldInteractable("Man of Blue", world, new Polygon(new float[]{0, 0, 1, 0, 1, 2, 0, 2}), 1);
        interactable.setPosition(5f, 2f);
        interactable.setStory(story);
        interactable.setRenderer(new PolygonRenderer(interactable.getPolygon(), Color.MAGENTA));
        story = new Story(worldGroup);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 2));
        story.addDialog(1, new Dialog(story, player, "O Man of Blue,\ntell me of you?", answers));
        answers = new Array<Answer>();
        answers.add(new DialogAnswer(story, "Yes!", 3));
        answers.add(new DialogAnswer(story, "No!", 0));
        story.addDialog(2, new Dialog(story, interactable, "My name is Steve.\nYou wish to leave?", answers));
        answers = new Array<Answer>();
        answers.add(new ItemTakenAnswer(story, "Yes!", 4, "gold", 7));
        answers.add(new DialogAnswer(story, "No!", 0));
        story.addDialog(3, new Dialog(story, interactable, "For a wish so bold\nI desire [ORANGE]seven[] [BLUE][[gold]!", answers));
        answers = new Array<Answer>();
        answers.add(new ItemGivenAnswer(story, "Take [BLUE][[Key][]", -5, "third-key", 1));
        story.addDialog(4, new Dialog(story, interactable, "A man of my word\nThe door is third!", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 0));
        story.addDialog(5, new Dialog(story, interactable, "What do you need?\nYou're now freed!", answers));
        world.addCollisionInteractable(interactable);
        world.addRenderable(interactable);

        interactable = new WorldInteractable("Door of First", world, new Polygon(new float[]{0, 0, 1, 0, 1, 2, 0, 2}), 1);
        story = new Story(worldGroup);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "Try [[key]", 2, "third-key", 1));
        answers.add(new DialogAnswer(story, "Leave.", 0));
        story.addDialog(1, new Dialog(story, interactable, "[[A door and a lock.]", answers));
        answers = new Array<Answer>();
        answers.add(new DialogAnswer(story, "Leave.", -1));
        story.addDialog(2, new Dialog(story, interactable, "[[The door remains unopened.]?", answers));
        interactable.setPosition(9f, 2f);
        interactable.setStory(story);
        interactable.setRenderer(new PolygonRenderer(interactable.getPolygon(), Color.MAGENTA));
        world.addCollisionInteractable(interactable);
        world.addRenderable(interactable);

        interactable = new WorldInteractable("Door of Second", world, new Polygon(new float[]{0, 0, 1, 0, 1, 2, 0, 2}), 1);
        story = new Story(worldGroup);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "Try [[key]", 2, "third-key", 1));
        answers.add(new DialogAnswer(story, "Leave.", 0));
        story.addDialog(1, new Dialog(story, interactable, "[[A door and a lock.]", answers));
        answers = new Array<Answer>();
        answers.add(new DialogAnswer(story, "Leave.", -1));
        story.addDialog(2, new Dialog(story, interactable, "[[The door remains unopened.]", answers));
        interactable.setPosition(11f, 2f);
        interactable.setStory(story);
        interactable.setRenderer(new PolygonRenderer(interactable.getPolygon(), Color.MAGENTA));
        world.addCollisionInteractable(interactable);
        world.addRenderable(interactable);

        interactable = new WorldInteractable("Door of Third", world, new Polygon(new float[]{0, 0, 1, 0, 1, 2, 0, 2}), 1);
        story = new Story(worldGroup);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "Try [[key]", 2, "third-key", 1));
        answers.add(new DialogAnswer(story, "Leave.", 0));
        story.addDialog(1, new Dialog(story, interactable, "[[A door and a lock.]", answers));
        answers = new Array<Answer>();
        answers.add(new ExitAnswer(story, "Step through.", -1));
        story.addDialog(2, new Dialog(story, interactable, "[[The door opens into inky blackness.]", answers));
        interactable.setPosition(13f, 2f);
        interactable.setStory(story);
        interactable.setRenderer(new PolygonRenderer(interactable.getPolygon(), Color.MAGENTA));
        world.addCollisionInteractable(interactable);
        world.addRenderable(interactable);

        Platform platform;
        Collision collision;
        WorldEventTrigger worldEventTrigger;
        TimerEventTrigger timerEventTrigger;

        collision = new Collision(new Polygon(new float[]{-1, -2, 0, -1, 0, 10, -1, 11}));
        world.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{-1, -2, 16, -2, 15, -1, 0, -1}));
        world.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{15, -1, 16, -2, 16, 11, 15, 10}));
        world.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{-1, 11, 0, 10, 15, 10, 16, 11}));
        world.addCollision(collision);


        platform = new Platform(new Polygon((new float[]{0, 2, 0, -1, 1, -1, 1, 2})));
        platform.setSurface(new Vector2(0, 2), new Vector2(1, 2));
        world.addPlatform(platform);
        platform = new Platform(new Polygon((new float[]{1, 1, 1, -1, 2, -1, 2, 0})));
        platform.setSurface(new Vector2(1, 1), new Vector2(2, 0));
        world.addPlatform(platform);
        platform = new Platform(new Polygon((new float[]{2, 0, 2, -1, 4, -1, 4, 0})));
        platform.setSurface(new Vector2(2, 0), new Vector2(4, 0));
        world.addPlatform(platform);
        platform = new Platform(new Polygon((new float[]{4, 0, 4, -1, 5, -1, 5, 1})));
        platform.setSurface(new Vector2(4, 0), new Vector2(5, 1));
        world.addPlatform(platform);
        platform = new Platform(new Polygon((new float[]{5, 2, 5, -1, 15, -1, 15, 2})));
        platform.setSurface(new Vector2(5, 2), new Vector2(15, 2));
        world.addPlatform(platform);

        platform = new Platform(new Polygon((new float[]{0, 0, 0, -.5f, 1, -.5f, 1, 0})));
        platform.setCollidable(false);
        platform.setSurface(new Vector2(0, 0), new Vector2(1, 0));
        platform.setRenderer(new PolygonRenderer(platform.getPolygonPlatform(), Color.CYAN));
        platform.setPosition(1, 4);
        world.addPlatform(platform);
        world.addRenderable(platform);
        platform = new Platform(new Polygon((new float[]{0, 0, 0, -.5f, 1, -.5f, 1, 0})));
        platform.setCollidable(false);
        platform.setSurface(new Vector2(0, 0), new Vector2(1, 0));
        platform.setPosition(3, 5);
        platform.setRenderer(new PolygonRenderer(platform.getPolygonPlatform(), Color.CYAN));
        world.addPlatform(platform);
        world.addRenderable(platform);
        platform = new Platform(new Polygon((new float[]{0, 0, 0, -.5f, 1, -.5f, 1, 0})));
        platform.setCollidable(false);
        platform.setSurface(new Vector2(0, 0), new Vector2(1, 0));
        platform.setPosition(5, 6);
        platform.setRenderer(new PolygonRenderer(platform.getPolygonPlatform(), Color.CYAN));
        world.addPlatform(platform);
        world.addRenderable(platform);
        platform = new Platform(new Polygon((new float[]{0, 0, 0, -.5f, 1, -.5f, 1, 0})));
        platform.setCollidable(false);
        platform.setSurface(new Vector2(0, 0), new Vector2(1, 0));
        platform.setPosition(7, 7);
        platform.setRenderer(new PolygonRenderer(platform.getPolygonPlatform(), Color.CYAN));
        world.addPlatform(platform);
        world.addRenderable(platform);
        platform = new Platform(new Polygon((new float[]{0, 0, 0, -.5f, 1, -.5f, 1, 0})));
        platform.setCollidable(false);
        platform.setSurface(new Vector2(0, 0), new Vector2(1, 0));
        platform.setPosition(1, 8);
        platform.setRenderer(new PolygonRenderer(platform.getPolygonPlatform(), Color.CYAN));
        world.addPlatform(platform);
        world.addRenderable(platform);

        platform = new Platform(new Polygon((new float[]{0, 0, 0, -.5f, 5, -.5f, 5, 0})));
        platform.setCollidable(false);
        platform.setSurface(new Vector2(0, 0), new Vector2(5, 0));
        platform.setPosition(10, 7);
        platform.setRenderer(new PolygonRenderer(platform.getPolygonPlatform(), Color.CYAN));
        world.addPlatform(platform);
        world.addRenderable(platform);

        worldEventTrigger = new InclusionTrigger(new Polygon(new float[]{0, 0, .8f, 0, .8f, 1, 0f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.4f, 0, .8f, .2f, .8f, .8f, .4f, 1f, 0f, .8f, 0f, .2f}), Color.YELLOW));
        worldEventTrigger.setEvent(new ItemEvent(world, "gold", 1, worldEventTrigger));
        worldEventTrigger.setPosition(1, 4);
        world.addRenderable(worldEventTrigger);
        world.addWorldEventTrigger(worldEventTrigger);
        worldEventTrigger = new InclusionTrigger(new Polygon(new float[]{0, 0, .8f, 0, .8f, 1, 0f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.4f, 0, .8f, .2f, .8f, .8f, .4f, 1f, 0f, .8f, 0f, .2f}), Color.YELLOW));
        worldEventTrigger.setEvent(new ItemEvent(world, "gold", 1, worldEventTrigger));
        worldEventTrigger.setPosition(3, 5);
        world.addRenderable(worldEventTrigger);
        world.addWorldEventTrigger(worldEventTrigger);
        worldEventTrigger = new InclusionTrigger(new Polygon(new float[]{0, 0, .8f, 0, .8f, 1, 0f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.4f, 0, .8f, .2f, .8f, .8f, .4f, 1f, 0f, .8f, 0f, .2f}), Color.YELLOW));
        worldEventTrigger.setEvent(new ItemEvent(world, "gold", 1, worldEventTrigger));
        worldEventTrigger.setPosition(5, 6);
        world.addRenderable(worldEventTrigger);
        world.addWorldEventTrigger(worldEventTrigger);
        worldEventTrigger = new InclusionTrigger(new Polygon(new float[]{0, 0, .8f, 0, .8f, 1, 0f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.4f, 0, .8f, .2f, .8f, .8f, .4f, 1f, 0f, .8f, 0f, .2f}), Color.YELLOW));
        worldEventTrigger.setEvent(new ItemEvent(world, "gold", 1, worldEventTrigger));
        worldEventTrigger.setPosition(7, 7);
        world.addRenderable(worldEventTrigger);
        world.addWorldEventTrigger(worldEventTrigger);
        worldEventTrigger = new InclusionTrigger(new Polygon(new float[]{0, 0, .8f, 0, .8f, 1, 0f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.4f, 0, .8f, .2f, .8f, .8f, .4f, 1f, 0f, .8f, 0f, .2f}), Color.YELLOW));
        worldEventTrigger.setEvent(new ItemEvent(world, "gold", 1, worldEventTrigger));
        worldEventTrigger.setPosition(1, 8);
        world.addRenderable(worldEventTrigger);
        world.addWorldEventTrigger(worldEventTrigger);
        worldEventTrigger = new InclusionTrigger(new Polygon(new float[]{0, 0, .8f, 0, .8f, 1, 0f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.4f, 0, .8f, .2f, .8f, .8f, .4f, 1f, 0f, .8f, 0f, .2f}), Color.YELLOW));
        worldEventTrigger.setEvent(new ItemEvent(world, "gold", 1, worldEventTrigger));
        worldEventTrigger.setPosition(11, 7);
        world.addRenderable(worldEventTrigger);
        world.addWorldEventTrigger(worldEventTrigger);
        worldEventTrigger = new InclusionTrigger(new Polygon(new float[]{0, 0, .8f, 0, .8f, 1, 0f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.4f, 0, .8f, .2f, .8f, .8f, .4f, 1f, 0f, .8f, 0f, .2f}), Color.YELLOW));
        worldEventTrigger.setEvent(new ItemEvent(world, "gold", 1, worldEventTrigger));
        worldEventTrigger.setPosition(13, 7);
        world.addRenderable(worldEventTrigger);
        world.addWorldEventTrigger(worldEventTrigger);

        world.addPlatform(platform);

        worldGroup.addWorld(world);


        worldGroup.setInteraction(player);

        return worldGroup;
    }

    private PlayerAgent buildPlayer() {
        PlayerAgent player = new PlayerAgent("Player", new Polygon(new float[]{0, 0, .7f, 0, .7f, 1.6f, 0, 1.6f}), new Polygon(new float[]{0, -.3f, .7f, -.3f, .7f, .3f, 0, .3f}));
        player.setRenderer(new PolygonRenderer(new Polygon(new float[]{0, 0, .7f, 0, .7f, 1.6f, 0, 1.6f}), Color.GREEN));
        player.setAgentController(new PlayerAgentController(player));
        player.setMaxDelta(new Vector2(.2f, 1f));
        player.setMovement(new Vector2(.05f, .38f));
        player.setJumpTime(30);
        return player;
    }

    public WorldInteractable buildWorldInteractable(String name, Color color) {
        WorldInteractable interactable = new WorldInteractable(name, new Polygon(new float[]{0, 0, 1, 0, 1, 2, 0, 2}), 1);
        interactable.setRenderer(new PolygonRenderer(interactable.getPolygon(), color));
        return interactable;
    }

    public WorldEventTrigger buildGoldItem() {
        WorldEventTrigger worldEventTrigger = new InclusionTrigger(new Polygon(new float[]{.1f, 0, .9f, 0, .9f, 1, .1f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.5f, 0, .9f, .2f, .9f, .8f, .5f, 1f, .1f, .8f, .1f, .2f}), Color.YELLOW));
        worldEventTrigger.setEvent(new ItemEvent("gold", 1, worldEventTrigger));
        return worldEventTrigger;
    }

    public WorldEventTrigger buildCrystalItem() {
        WorldEventTrigger worldEventTrigger = new InclusionTrigger(new Polygon(new float[]{.1f, 0, .9f, 0, .9f, 1, .1f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.5f, 0, .9f, .2f, .9f, .8f, .5f, 1f, .1f, .8f, .1f, .2f}), Color.PURPLE));
        worldEventTrigger.setEvent(new ItemEvent("crystal", 1, worldEventTrigger));
        return worldEventTrigger;
    }

    public Platform buildPlatformByMeter(int meters, boolean collides, Color color) {

    }
}
