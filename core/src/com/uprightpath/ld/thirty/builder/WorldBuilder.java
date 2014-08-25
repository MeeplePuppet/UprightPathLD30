package com.uprightpath.ld.thirty.builder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.uprightpath.ld.thirty.logic.*;
import com.uprightpath.ld.thirty.logic.agents.PlayerAgent;
import com.uprightpath.ld.thirty.logic.controller.PlayerAgentController;
import com.uprightpath.ld.thirty.logic.event.*;
import com.uprightpath.ld.thirty.logic.interactables.WorldInteractable;
import com.uprightpath.ld.thirty.logic.triggers.ExclusionEventTrigger;
import com.uprightpath.ld.thirty.logic.triggers.InclusionEventTrigger;
import com.uprightpath.ld.thirty.logic.triggers.ResettingEventTrigger;
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


    public static WorldGroup buildWorldGroupOne() {
        WorldGroup worldGroup = new WorldGroup();
        worldGroup.setId(0);
        worldGroup.setName("A Door of Four");

        PlayerAgent playerA, playerB;
        World worldA, worldB;
        Story story;
        Answer answer;
        Array<Answer> answers;
        WorldInteractable interactable;
        Platform platform;
        Collision collision;
        WorldEventTrigger worldEventTrigger;
        TimerEventTrigger timerEventTrigger;

        playerA = buildAisling();
        playerA.setPosition(1.5f, 1.f);
        worldA = new World(worldGroup, playerA, "Stepping Stones");
        worldA.setWorldDelta(new Vector2(0f, -.04f));
        worldA.addRenderable(new RenderableAdapter(new PolygonRenderer(new Polygon(new float[]{0.f, 1.f, 2.f, 1.f, 3.f, 0.f, 9.f, 0.f, 9.f, 3.f, 7.f, 3.f, 7.f, 4.f, 9.f, 4.f, 9.f, 6.f, 3.f, 6.f, 3.f, 4.f, 2.f, 3.f, 0.f, 3.f}), Color.BLUE)));
        worldA.addRenderable(playerA);

        playerB = buildAshley();
        playerB.setPosition(5.5f, 7.f);
        worldB = new World(worldGroup, playerB, "Falling Down");
        worldB.setWorldDelta(new Vector2(0f, -.04f));
        worldB.addRenderable(new RenderableAdapter(new PolygonRenderer(new Polygon(new float[]{5.f, 9.f, 5.f, 7.f, 7.f, 7.f, 7.f, 4.f, 9.f, 4.f, 12.f, 7.f, 14.f, 7.f, 14.f, 9.f}), Color.RED)));
        worldB.addRenderable(playerB);

        story = new Story(worldGroup);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 2));
        story.addDialog(1, new Dialog(story, playerA, "Something happened to my head;\nI feel as though I should be dead.", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 3));
        story.addDialog(2, new Dialog(story, playerB, "Who's saying that?", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 4));
        story.addDialog(3, new Dialog(story, playerA, "A door I see;\nI need a key?\nThen I shall be free?", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 0));
        story.addDialog(4, new Dialog(story, playerB, "There looks to be an exit over there.", answers));
        playerA.setStory(story);

        interactable = buildDoor("Door of Fourth", Color.PURPLE);
        interactable.setPosition(7.5f, 4.f);
        story = new Story(worldGroup);
        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "[Try <Key of Fourth>.]", 2, "key-of-fourth", 1));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[The door stands silent, still and cold;\nIts wood is weathered, hard and old;\nA number, a hole, a lock, no key;\nA passage barred, nowhere to flee.]", answers));
        answers = new Array<Answer>();
        answers.add(new ExitAnswer(story, "[Step into darkness.]", 0));
        story.addDialog(2, new Dialog(story, interactable, "[The key did turn within the lock;\nThe door did open in the rock;\nThe hinges sound a horrible creak;\nThe passage shown is dark and bleak.]", answers));
        worldA.addCollisionInteractable(interactable);
        worldA.addRenderable(interactable);


        platform = new Platform(new Polygon(new float[]{0.f, 1f, 0.f, -1.f, 2.f, -1.f, 2.f, 1.f}));
        platform.setSurface(new Vector2(0f, 1f), new Vector2(2f, 1f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{2.f, 1.f, 2.f, -1.f, 3.f, -1.f, 3.f, 0.f}));
        platform.setSurface(new Vector2(2f, 1f), new Vector2(3f, 0f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{3.f, 0.f, 3.f, -1.f, 9.f, -1.f, 9.f, 0.f}));
        platform.setSurface(new Vector2(3f, 0f), new Vector2(9f, 0f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{7.f, 4.f, 7.f, 3.f, 9.f, 3.f, 9.f, 4.f}));
        platform.setSurface(new Vector2(7f, 4f), new Vector2(9f, 4f));
        worldA.addPlatform(platform);

        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(3f, 2f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(5f, 3f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);

        collision = new Collision(new Polygon(new float[]{-1.f, 4.f, -1.f, -1f, 0.f, -1.f, 0.f, 4.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{0.f, 6.f, 0.f, 3.f, 2.f, 3.f, 3.f, 4.f, 3.f, 6.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{0.f, 7.f, 0.f, 6.f, 10.f, 6.f, 10.f, 7.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{9.f, 6.f, 9.f, -1.f, 10.f, -1.f, 10.f, 6.f}));
        worldA.addCollision(collision);

        worldEventTrigger = buildKeyOfFourth(Color.CYAN);
        worldEventTrigger.setPosition(8.f, 0.f);
        worldA.addWorldEventTrigger(worldEventTrigger);
        worldA.addRenderable(worldEventTrigger);


        interactable = buildDoor("Fourth Door", Color.PURPLE);
        interactable.setPosition(7.5f, 4.f);
        story = new Story(worldGroup);
        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "[Try <Key of Fourth>.]", 2, "key-of-fourth", 1));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[A steel door stands against the wall with a black '4' painted on it.]", answers));
        answers = new Array<Answer>();
        answers.add(new ExitAnswer(story, "[Step into darkness.]", 0));
        story.addDialog(2, new Dialog(story, interactable, "[The door swings open on oiled hinges and exposes pitch blackness.]", answers));
        worldB.addCollisionInteractable(interactable);
        worldB.addRenderable(interactable);


        platform = new Platform(new Polygon(new float[]{5.f, 7.f, 5.f, 3.f, 7.f, 3.f, 7.f, 7.f}));
        platform.setSurface(new Vector2(5f, 7f), new Vector2(7f, 7f));
        worldB.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{7.f, 4.f, 7.f, 3.f, 9.f, 3.f, 9.f, 4.f}));
        platform.setSurface(new Vector2(7f, 4f), new Vector2(9f, 4f));
        worldB.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{9.f, 4.f, 9.f, 3.f, 12.f, 3.f, 12.f, 7.f}));
        platform.setSurface(new Vector2(9f, 4f), new Vector2(12f, 7f));
        worldB.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{12.f, 7.f, 12.f, 6.f, 14.f, 6.f, 14.f, 7.f}));
        platform.setSurface(new Vector2(12f, 7f), new Vector2(14f, 7f));
        worldB.addPlatform(platform);

        collision = new Collision(new Polygon(new float[]{4.f, 9.f, 4.f, 6f, 5.f, 6.f, 5.f, 9.f}));
        worldB.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{4.f, 10.f, 4.f, 9.f, 15.f, 9.f, 15.f, 10.f}));
        worldB.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{14.f, 10.f, 14.f, 6.f, 15.f, 6.f, 15.f, 10.f}));
        worldB.addCollision(collision);

        worldEventTrigger = buildKeyOfFourth(Color.MAGENTA);
        worldEventTrigger.setPosition(13.f, 7.f);
        worldB.addWorldEventTrigger(worldEventTrigger);
        worldB.addRenderable(worldEventTrigger);

        worldGroup.setInteraction(playerA);
        worldGroup.addWorld(worldA);
        worldGroup.addWorld(worldB);

        return worldGroup;
    }

    public static WorldGroup buildWorldGroupTwo() {
        WorldGroup worldGroup = new WorldGroup();
        worldGroup.setId(1);
        worldGroup.setName("Tit for Tat");

        PlayerAgent playerA, playerB;
        World worldA, worldB;
        Story story;
        Answer answer;
        Array<Answer> answers;
        WorldInteractable interactable;
        Platform platform;
        Collision collision;
        WorldEventTrigger worldEventTrigger;
        TimerEventTrigger timerEventTrigger;

        playerA = buildAisling();
        playerA.adjustItem("key-of-fourth", 1);
        playerA.setPosition(14.6f, 0.f);
        worldA = new World(worldGroup, playerA, "Golden Golden");
        worldA.setWorldDelta(new Vector2(0f, -.04f));
        worldA.addRenderable(new RenderableAdapter(new PolygonRenderer(new Polygon(new float[]{
                8.f, 10.f,
                8.f, 6.f,
                10.f, 6.f,
                10.f, 8.f,
                12.f, 8.f,
                12.f, 4.f,
                7.f, 4.f,
                7.f, 2.f,
                12.f, 2.f,
                12.f, 0.f,
                15.f, 0.f,
                15.f, 10.f,}), Color.BLUE)));
        worldA.addRenderable(playerA);

        playerB = buildAshley();
        playerB.adjustItem("key-of-fourth", 1);
        playerB.setPosition(2.0f, 0.f);
        worldB = new World(worldGroup, playerB, "Purple shimmers");
        worldB.setWorldDelta(new Vector2(0f, -.04f));
        worldB.addRenderable(new RenderableAdapter(new PolygonRenderer(new Polygon(new float[]{
                2.f, 8.f,
                2.f, 0.f,
                5.f, 0.f,
                5.f, 2.f,
                9.f, 2.f,
                9.f, 4.f,
                5.f, 4.f,
                5.f, 6.f,
                10.f, 6.f,
                10.f, 8.f,
                12.f, 8.f,
                12.f, 10.f,
                8.f, 10.f,
                8.f, 8.f,}), Color.RED)));
        worldB.addRenderable(playerB);

        story = new Story(worldGroup);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 2));
        story.addDialog(1, new Dialog(story, playerB, "And, we're in another space?", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 3));
        story.addDialog(2, new Dialog(story, playerA, "Again a voice within my mind;\nBe a person to whom I'm twined?", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 4));
        story.addDialog(3, new Dialog(story, playerB, "More rhymes? Just who are you?", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 5));
        story.addDialog(4, new Dialog(story, playerA, "A young girl of ten;\nFrom a emerald glen;\nAway from home she ran with passion;\nBy her parents given the name Aisling.", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 0));
        story.addDialog(4, new Dialog(story, playerB, "Aisling? I'm Ashley...\nNo need to respond.", answers));
        playerA.setStory(story);

        interactable = buildShade("The Amethyst Shade", Color.PURPLE);
        interactable.setPosition(7.5f, 2.f);
        worldA.addCollisionInteractable(interactable);
        worldA.addRenderable(interactable);
        worldB.addCollisionInteractable(interactable);
        worldB.addRenderable(interactable);

        story = new Story(worldGroup);
        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemTakenAnswer(story, "[Give three <gold>.]", 2, "gold", 3));
        answers.add(new ItemTakenAnswer(story, "[Give three <crystal>.]", 3, "crystal", 3));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[A purple shade hovers before you.]\nI am plain in word and deed.\nAll that's important to me is greed.\nA key I'll give for three of something.", answers));
        answers = new Array<Answer>();
        answers.add(new ItemGivenAnswer(story, "[Take the <Key of Second>.]", -1, "key-of-second", 1));
        story.addDialog(2, new Dialog(story, interactable, "[The shade takes the offered gold.]\nHere's your key. Use it well.", answers));
        answers = new Array<Answer>();
        answers.add(new ItemGivenAnswer(story, "[Take the <Key of Second>.]", -1, "key-of-second", 1));
        story.addDialog(3, new Dialog(story, interactable, "[The shade takes the offered crystals.]\nHere's your key. Use it well.", answers));


        interactable = buildDoor("Door of Second", Color.PURPLE);
        interactable.setPosition(8.5f, 6.f);
        story = new Story(worldGroup);
        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "[Try <Key of Second>.]", 2, "key-of-second", 1));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[The door stands silent, still and cold;\nIts wood is weathered, hard and old;\nA number, a hole, a lock, no key;\nA passage barred, nowhere to flee.]", answers));
        answers = new Array<Answer>();
        answers.add(new ExitAnswer(story, "[Step into darkness.]", 0));
        story.addDialog(2, new Dialog(story, interactable, "[The key did turn within the lock;\nThe door did open in the rock;\nThe hinges sound a horrible creak;\nThe passage shown is dark and bleak.]", answers));
        worldA.addCollisionInteractable(interactable);
        worldA.addRenderable(interactable);

        platform = new Platform(new Polygon(new float[]{12.f, 0.f, 12.f, -1.f, 15.f, -1.f, 15.f, 0.f}));
        platform.setSurface(new Vector2(12f, 0f), new Vector2(15f, 0f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{7.f, 2.f, 7.f, -1.f, 12.f, -1.f, 12.f, 2.f}));
        platform.setSurface(new Vector2(7f, 2f), new Vector2(12f, 2f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{8.f, 6.f, 8.f, 4.f, 10.f, 4.f, 10.f, 6.f}));
        platform.setSurface(new Vector2(8f, 6f), new Vector2(10f, 6f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{10.f, 8.f, 10.f, 4.f, 12.f, 4.f, 12.f, 8.f}));
        platform.setSurface(new Vector2(10f, 8f), new Vector2(12f, 8f));
        worldA.addPlatform(platform);

        collision = new Collision(new Polygon(new float[]{6.f, 5.f, 6.f, 1.f, 7.f, 1.f, 7.f, 5.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{7.f, 11.f, 7.f, 5.f, 8.f, 5.f, 8.f, 11.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{7.f, 11.f, 7.f, 10.f, 16.f, 10.f, 16.f, 11.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{15.f, 11.f, 15.f, -1.f, 16.f, -1.f, 16.f, 11.f}));
        worldA.addCollision(collision);

        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(14f, 3f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(14f, 7f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(12f, 5f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);

        worldEventTrigger = buildGoldItem();
        worldEventTrigger.setPosition(14f, 3f);
        worldA.addWorldEventTrigger(worldEventTrigger);
        worldA.addRenderable(worldEventTrigger);
        worldEventTrigger = buildGoldItem();
        worldEventTrigger.setPosition(14f, 7f);
        worldA.addWorldEventTrigger(worldEventTrigger);
        worldA.addRenderable(worldEventTrigger);
        worldEventTrigger = buildGoldItem();
        worldEventTrigger.setPosition(12f, 5f);
        worldA.addWorldEventTrigger(worldEventTrigger);
        worldA.addRenderable(worldEventTrigger);


        interactable = buildDoor("Second Door", Color.PURPLE);
        interactable.setPosition(8.5f, 6.f);
        story = new Story(worldGroup);
        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "[Try <Key of Second>.]", 2, "key-of-second", 1));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[A steel door stands against the wall with a black '2' painted on it.]", answers));
        answers = new Array<Answer>();
        answers.add(new ExitAnswer(story, "[Step into darkness.]", 0));
        story.addDialog(2, new Dialog(story, interactable, "[The door swings open on oiled hinges and exposes pitch blackness.]", answers));
        worldB.addCollisionInteractable(interactable);
        worldB.addRenderable(interactable);

        platform = new Platform(new Polygon(new float[]{2.f, 0.f, 2.f, -1.f, 5.f, -1.f, 5.f, 0.f}));
        platform.setSurface(new Vector2(2f, 0f), new Vector2(5f, 0f));
        worldB.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{5.f, 2.f, 5.f, -1.f, 9.f, -1.f, 9.f, 2.f}));
        platform.setSurface(new Vector2(5f, 2f), new Vector2(9f, 2f));
        worldB.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{5.f, 6.f, 5.f, 4.f, 10.f, 4.f, 10.f, 6.f}));
        platform.setSurface(new Vector2(5f, 6f), new Vector2(10f, 6f));
        worldB.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{10.f, 8.f, 10.f, 4.f, 12.f, 4.f, 12.f, 8.f}));
        platform.setSurface(new Vector2(10f, 8f), new Vector2(12f, 8f));
        worldB.addPlatform(platform);

        collision = new Collision(new Polygon(new float[]{1.f, 9.f, 1.f, -1.f, 2.f, -1.f, 2.f, 9.f}));
        worldB.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{2.f, 11.f, 2.f, 8.f, 8.f, 8.f, 8.f, 11.f}));
        worldB.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{8.f, 11.f, 8.f, 10.f, 12.f, 10.f, 12.f, 11.f}));
        worldB.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{12.f, 11.f, 12.f, 7.f, 13.f, 7.f, 13.f, 11.f}));
        worldB.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{9.f, 5.f, 9.f, 1.f, 10.f, 1.f, 10.f, 5.f}));
        worldB.addCollision(collision);

        platform = buildPlatformByMeter(1, false, Color.MAGENTA);
        platform.setPosition(2f, 3f);
        worldB.addPlatform(platform);
        worldB.addRenderable(platform);
        platform = buildPlatformByMeter(1, false, Color.MAGENTA);
        platform.setPosition(4f, 5f);
        worldB.addPlatform(platform);
        worldB.addRenderable(platform);

        worldEventTrigger = buildCrystalItem();
        worldEventTrigger.setPosition(2f, 3f);
        worldA.addWorldEventTrigger(worldEventTrigger);
        worldA.addRenderable(worldEventTrigger);
        worldB.addWorldEventTrigger(worldEventTrigger);
        worldB.addRenderable(worldEventTrigger);
        worldEventTrigger = buildCrystalItem();
        worldEventTrigger.setPosition(5.5f, 2f);
        worldA.addWorldEventTrigger(worldEventTrigger);
        worldA.addRenderable(worldEventTrigger);
        worldB.addWorldEventTrigger(worldEventTrigger);
        worldB.addRenderable(worldEventTrigger);
        worldEventTrigger = buildCrystalItem();
        worldEventTrigger.setPosition(10.5f, 8f);
        worldA.addWorldEventTrigger(worldEventTrigger);
        worldA.addRenderable(worldEventTrigger);
        worldB.addWorldEventTrigger(worldEventTrigger);
        worldB.addRenderable(worldEventTrigger);

        worldGroup.addWorld(worldA);
        worldGroup.addWorld(worldB);

        return worldGroup;
    }

    public static WorldGroup buildWorldGroupThree() {
        WorldGroup worldGroup = new WorldGroup();
        worldGroup.setId(2);
        worldGroup.setName("To the Top");

        PlayerAgent playerA, playerB;
        World worldA, worldB;
        Story story;
        Answer answer;
        Array<Answer> answers;
        WorldInteractable interactable;
        Platform platform;
        Collision collision;
        WorldEventTrigger worldEventTrigger;
        TimerEventTrigger timerEventTrigger;


        playerA = buildAisling();
        playerA.adjustItem("key-of-fourth", 1);
        playerA.adjustItem("key-of-second", 1);
        playerA.setPosition(4.7f, 0.f);
        worldA = new World(worldGroup, playerA, "Up the Left");
        worldA.setWorldDelta(new Vector2(0f, -.04f));
        worldA.addRenderable(new RenderableAdapter(new PolygonRenderer(new Polygon(new float[]{
                1.f, 11.f,
                1.f, 6.f,
                3.f, 6.f,
                3.f, 5.f,
                1.f, 5.f,
                1.f, 0.f,
                6.f, 0.f,
                6.f, 2.f,
                4.f, 2.f,
                4.f, 8.f,
                3.f, 8.f,
                3.f, 9.f,
                6.f, 9.f,
                6.f, 11.f}), Color.BLUE)));
        worldA.addRenderable(playerA);

        playerB = buildAshley();
        playerB.adjustItem("key-of-fourth", 1);
        playerB.adjustItem("key-of-second", 1);
        playerB.setPosition(4.7f, 0.f);
        worldB = new World(worldGroup, playerB, "Up the Right");
        worldB.setWorldDelta(new Vector2(0f, -.04f));
        worldB.addRenderable(new RenderableAdapter(new PolygonRenderer(new Polygon(new float[]{
                4.f, 11.f,
                4.f, 9.f,
                7.f, 9.f,
                7.f, 8.f,
                6.f, 8.f,
                6.f, 2.f,
                4.f, 2.f,
                4.f, 0.f,
                9.f, 0.f,
                9.f, 5.f,
                7.f, 5.f,
                7.f, 6.f,
                9.f, 6.f,
                9.f, 11.f}), Color.RED)));
        worldB.addRenderable(playerB);

        story = new Story(worldGroup);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 2));
        story.addDialog(1, new Dialog(story, playerA, "Things go up and things go down;\nPlatforms going all around?.", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 3));
        story.addDialog(2, new Dialog(story, playerB, "Elevators, Aisling, elevators.", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 4));
        story.addDialog(3, new Dialog(story, playerA, "Elevat--...?\nAttempting to speak that word;\nMakes my thoughts slow, silly and slurred!", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 0));
        story.addDialog(4, new Dialog(story, playerB, "Hah! Makes me want to get you to say orange.", answers));
        playerA.setStory(story);

        interactable = buildDoor("Door of Fourth", Color.PURPLE);
        interactable.setPosition(4.5f, 9.f);
        story = new Story(worldGroup);
        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "[Try <Key of Fourth>.]", 2, "key-of-fourth", 1));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[The door stands silent, still and cold;\nIts wood is weathered, hard and old;\nA number, a hole, a lock, no key;\nA passage barred, nowhere to flee.]", answers));
        answers = new Array<Answer>();
        answers.add(new ExitAnswer(story, "[Step into darkness.]", 0));
        story.addDialog(2, new Dialog(story, interactable, "[The key did turn within the lock;\nThe door did open in the rock;\nThe hinges sound a horrible creak;\nThe passage shown is dark and bleak.]", answers));
        worldA.addCollisionInteractable(interactable);
        worldA.addRenderable(interactable);

        platform = new Platform(new Polygon(new float[]{1.f, 0.f, 1.f, -1.f, 6.f, -1.f, 6.f, 0.f}));
        platform.setSurface(new Vector2(1f, 0f), new Vector2(6f, 0f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{1.f, 6.0f, 1.f, 5.0f, 3.f, 5.0f, 3.f, 6.0f}));
        platform.setSurface(new Vector2(1f, 6f), new Vector2(3f, 6f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{3.f, 9.f, 3.f, 8.f, 6.f, 8.f, 6f, 9.f}));
        platform.setSurface(new Vector2(3.f, 9.f), new Vector2(6.f, 9.f));
        worldA.addPlatform(platform);

        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(3f, 3f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(3f, 4.5f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(3f, 6f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);

        collision = new Collision(new Polygon(new float[]{0.f, 12.f, 0.f, -1f, 1.f, -1.f, 1.f, 12.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{6.f, 12.f, 6.f, -1.f, 7.f, -1.f, 7.f, 12.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{0.f, 12.f, 0.f, 11.f, 7.f, 11.f, 7.f, 12.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{4.f, 8.f, 4.f, 2.f, 6.f, 2.f, 6.f, 8.f}));
        worldA.addCollision(collision);

        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(2.f, -1.f);
        platform.setDelta(0.f, 9.f / 120.f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{120, 120, 120, 120});
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(-3.f / 120.0f, 0.f), new Vector2(0.f, -9.f / 120f), new Vector2(3.f / 120f, 0.f), new Vector2(0.f, 9.f / 120f)}));
        worldA.addGlobalEventTrigger(timerEventTrigger);
        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(2.f, 8.f);
        platform.setDelta(-3.f / 120.f, 0.f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{120, 120, 120, 120});
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(0.f, -9.f / 120f), new Vector2(3.f / 120f, 0.f), new Vector2(0.f, 9.f / 120f), new Vector2(-3.f / 120.0f, 0.f)}));
        worldA.addGlobalEventTrigger(timerEventTrigger);
        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(-1.f, 8.f);
        platform.setDelta(0.f, -9.f / 120.f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{120, 120, 120, 120});
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(3.f / 120f, 0.f), new Vector2(0.f, 9.f / 120f), new Vector2(-3.f / 120.0f, 0.f), new Vector2(0.f, -9.f / 120f)}));
        worldA.addGlobalEventTrigger(timerEventTrigger);
        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(-1.f, -1.f);
        platform.setDelta(3.f / 120.f, 0.f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{120, 120, 120, 120});
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(0.f, 9.f / 120f), new Vector2(-3.f / 120.0f, 0.f), new Vector2(0.f, -9.f / 120f), new Vector2(3.f / 120f, 0.f)}));
        worldA.addGlobalEventTrigger(timerEventTrigger);


        interactable = buildDoor("Fourth Door", Color.PURPLE);
        interactable.setPosition(4.5f, 9.f);
        story = new Story(worldGroup);
        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "[Try <Key of Fourth>.]", 2, "key-of-fourth", 1));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[A steel door stands against the wall with a black '4' painted on it.]", answers));
        answers = new Array<Answer>();
        answers.add(new ExitAnswer(story, "[Step into darkness.]", 0));
        story.addDialog(2, new Dialog(story, interactable, "[The door swings open on oiled hinges and exposes pitch blackness.]", answers));
        worldB.addCollisionInteractable(interactable);
        worldB.addRenderable(interactable);


        platform = new Platform(new Polygon(new float[]{4.f, 0.f, 4.f, -1.f, 9.f, -1.f, 9.f, 0.f}));
        platform.setSurface(new Vector2(4f, 0f), new Vector2(9f, 0f));
        worldB.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{7.f, 6.0f, 7.f, 5.0f, 9.f, 5.0f, 9.f, 6.0f}));
        platform.setSurface(new Vector2(7f, 6f), new Vector2(9f, 6f));
        worldB.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{4.f, 9.f, 4.f, 8.f, 7.f, 8.f, 7f, 9.f}));
        platform.setSurface(new Vector2(4.f, 9.f), new Vector2(7.f, 9.f));
        worldB.addPlatform(platform);

        platform = buildPlatformByMeter(1, false, Color.MAGENTA);
        platform.setPosition(6f, 3f);
        worldB.addPlatform(platform);
        worldB.addRenderable(platform);
        platform = buildPlatformByMeter(1, false, Color.MAGENTA);
        platform.setPosition(6f, 4.5f);
        worldB.addPlatform(platform);
        worldB.addRenderable(platform);
        platform = buildPlatformByMeter(1, false, Color.MAGENTA);
        platform.setPosition(6f, 6f);
        worldB.addPlatform(platform);
        worldB.addRenderable(platform);

        collision = new Collision(new Polygon(new float[]{9.f, 12.f, 9.f, -1f, 10.f, -1.f, 10.f, 12.f}));
        worldB.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{3.f, 12.f, 3.f, -1.f, 4.f, -1.f, 4.f, 12.f}));
        worldB.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{3.f, 12.f, 3.f, 11.f, 10.f, 11.f, 10.f, 12.f}));
        worldB.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{4.f, 8.f, 4.f, 2.f, 6.f, 2.f, 6.f, 8.f}));
        worldB.addCollision(collision);

        platform = buildPlatformByMeter(1, false, Color.MAGENTA);
        platform.setPosition(7.f, -1.f);
        platform.setDelta(0.f, 9.f / 120.f);
        worldB.addPlatform(platform);
        worldB.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{120, 120, 120, 120});
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(3.f / 120f, 0.f), new Vector2(0.f, -9.f / 120f), new Vector2(-3.f / 120f, 0.f), new Vector2(0.f, 9.f / 120f)}));
        worldB.addGlobalEventTrigger(timerEventTrigger);
        platform = buildPlatformByMeter(1, false, Color.MAGENTA);
        platform.setPosition(7.f, 8.f);
        platform.setDelta(3.f / 120.f, 0.f);
        worldB.addPlatform(platform);
        worldB.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{120, 120, 120, 120});
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(0.f, -9.f / 120f), new Vector2(-3.f / 120f, 0.f), new Vector2(0.f, 9.f / 120f), new Vector2(3.f / 120f, 0.f)}));
        worldB.addGlobalEventTrigger(timerEventTrigger);
        platform = buildPlatformByMeter(1, false, Color.MAGENTA);
        platform.setPosition(10.f, 8.f);
        platform.setDelta(0.f, -9.f / 120.f);
        worldB.addPlatform(platform);
        worldB.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{120, 120, 120, 120});
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(-3.f / 120f, 0.f), new Vector2(0.f, 9.f / 120f), new Vector2(3.f / 120f, 0.f), new Vector2(0.f, -9.f / 120f)}));
        worldB.addGlobalEventTrigger(timerEventTrigger);
        platform = buildPlatformByMeter(1, false, Color.MAGENTA);
        platform.setPosition(10.f, -1.f);
        platform.setDelta(-3.f / 120.f, 0.f);
        worldB.addPlatform(platform);
        worldB.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{120, 120, 120, 120});
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(0.f, 9.f / 120f), new Vector2(3.f / 120f, 0.f), new Vector2(0.f, -9.f / 120f), new Vector2(-3.f / 120f, 0.f)}));
        worldB.addGlobalEventTrigger(timerEventTrigger);

        worldGroup.setInteraction(playerA);
        worldGroup.addWorld(worldA);
        worldGroup.addWorld(worldB);

        return worldGroup;
    }

    public static WorldGroup buildWorldGroupFour() {
        WorldGroup worldGroup = new WorldGroup();
        worldGroup.setId(3);
        worldGroup.setName("Turn-key Elevator");

        PlayerAgent playerA;
        World worldA, worldB;
        Story story;
        Answer answer;
        Array<Answer> answers;
        WorldInteractable interactable;
        Platform platform;
        Collision collision;
        WorldEventTrigger worldEventTrigger;
        TimerEventTrigger timerEventTrigger;
        GlobalEvent[] globalEvents;


        playerA = buildAisling();
        playerA.adjustItem("key-of-fourth", 1);
        playerA.adjustItem("key-of-second", 1);
        playerA.setPosition(1.2f, 3.f);
        worldA = new World(worldGroup, playerA, "Lifts Alone");
        worldA.setWorldDelta(new Vector2(0f, -.04f));
        worldA.addRenderable(new RenderableAdapter(new PolygonRenderer(new Polygon[]{new Polygon(new float[]{
                0.f, 9.f,
                0.f, 0.f,
                12.f, 0.f,
                12.f, 7.f,
                14.f, 7.f,
                14.f, 9f}), new Polygon(new float[]{
                1.f, 3.f,
                1.f, 2.f,
                5.f, 2.f,
                5.f, 3.f,}), new Polygon(new float[]{
                6.f, 6.f,
                6.f, 5.f,
                7.f, 5.f,
                7.f, 6.f}), new Polygon(new float[]{
                1.f, 6.f,
                1.f, 5.f,
                2.f, 5.f,
                2.f, 6.f})}, Color.BLUE)));
        worldA.addRenderable(playerA);

        story = new Story(worldGroup);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 2));
        story.addDialog(1, new Dialog(story, playerA, "In my mind alone at last!\nPerhaps the other is gone at last?", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 3));
        story.addDialog(2, new Dialog(story, playerA, "Alas I'm still here;\nBut now alone with my fear.", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 4));
        story.addDialog(3, new Dialog(story, playerA, "But I learned of the lift;\nFrom the other a gift.", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 0));
        story.addDialog(4, new Dialog(story, playerA, "From here I can see;\nThe exit to which I flee.", answers));
        playerA.setStory(story);

        interactable = buildDoor("Door of First", Color.PURPLE);
        interactable.setPosition(12.5f, 7.f);
        story = new Story(worldGroup);
        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "[Try <Key of First>.]", 2, "key-of-first", 1));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[The door stands silent, still and cold;\nIts wood is weathered, hard and old;\nA number, a hole, a lock, no key;\nA passage barred, nowhere to flee.]", answers));
        answers = new Array<Answer>();
        answers.add(new ExitAnswer(story, "[Step into darkness.]", 0));
        story.addDialog(2, new Dialog(story, interactable, "[The key did turn within the lock;\nThe door did open in the rock;\nThe hinges sound a horrible creak;\nThe passage shown is dark and bleak.]", answers));
        worldA.addCollisionInteractable(interactable);
        worldA.addRenderable(interactable);

        platform = new Platform(new Polygon(new float[]{0.f, 0.f, 0.f, -1.f, 12.f, -1.f, 12.f, 0.f}));
        platform.setSurface(new Vector2(0f, 0f), new Vector2(12f, 0f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{1.f, 3.0f, 1.f, 2.0f, 5.f, 2.0f, 5.f, 3.0f}));
        platform.setSurface(new Vector2(1f, 3f), new Vector2(5f, 3f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{1.f, 6.f, 1.f, 5.f, 2.f, 5.f, 2.f, 6.f}));
        platform.setSurface(new Vector2(1.f, 6.f), new Vector2(2.f, 6.f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{6.f, 6.f, 6.f, 5.f, 7.f, 5.f, 7.f, 6.f}));
        platform.setSurface(new Vector2(6.f, 6.f), new Vector2(7.f, 6.f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{12.f, 7.f, 12.f, -1.f, 14.f, -1.f, 14.f, 7.f}));
        platform.setSurface(new Vector2(12.f, 7.f), new Vector2(14.f, 7.f));
        worldA.addPlatform(platform);

        collision = new Collision(new Polygon(new float[]{-1.f, 10.f, -1.f, -1f, 0.f, -1.f, 0.f, 10.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{-1.f, 11.f, -1.f, 9.f, 15.f, 9.f, 15.f, 11.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{14.f, 11.f, 14.f, -1.f, 15.f, -1.f, 15.f, 11.f}));
        worldA.addCollision(collision);

        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(0.f, 0.f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{30, 60, 30, 60});
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(0.f, .05f), new Vector2(0.f, 0.f), new Vector2(0.f, -.05f), new Vector2(0.f, 0.f)}));
        worldA.addGlobalEventTrigger(timerEventTrigger);


        interactable = buildKeyPlate("Key-plate of Fourth", Color.CYAN);
        interactable.setPosition(4.f, 3.f);
        story = new Story(worldGroup);

        globalEvents = new GlobalEvent[2];
        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(6.f, 3.f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{120, 80, 40, 80, 40, 100, 60, 100, 140, 10});
        timerEventTrigger.setLooping(false);
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(.05f, 0), new Vector2(0.f, 0.05f), new Vector2(-.05f, 0), new Vector2(0.f, 0.05f), new Vector2(.05f, 0), new Vector2(0.f, 0.05f), new Vector2(-.05f, 0), new Vector2(0.f, -0.05f), new Vector2(0, 0)}));
        globalEvents[0] = new GlobalEventTriggerEvent(timerEventTrigger);
        timerEventTrigger = new TimerEventTrigger(new DialogEvent(story, 1));
        timerEventTrigger.setTimer(new int[]{761, 1});
        timerEventTrigger.setLooping(false);
        globalEvents[1] = new GlobalEventTriggerEvent(timerEventTrigger);


        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "[Try <Key of Fourth>.]", 2, "key-of-fourth", 1));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[The plate glistens and gleams;\nThe keyhole speaks of someone's schemes.]", answers));
        answers = new Array<Answer>();
        answers.add(new GlobalEventAnswer(story, "[Back away.]", -3, globalEvents));
        story.addDialog(2, new Dialog(story, interactable, "[The key turns within the hole;\nClockwork sounds towards someone's goal.]", answers));
        answers = new Array<Answer>();
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(3, new Dialog(story, interactable, "[Clockwork grinds within the rock;\nThe rumbles shake each and every block.]", answers));
        worldA.addCollisionInteractable(interactable);
        worldA.addRenderable(interactable);

        platform = buildPlatformByMeter(1, false, Color.CYAN);
        platform.setPosition(3.5f, 6.f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setLooping(false);
        timerEventTrigger.setTimer(new int[]{20, 1});
        timerEventTrigger.setEvent(new PlatformSolidityEvent(platform, false));
        worldEventTrigger = new ResettingEventTrigger(new Polygon(new float[]{4.f, 7.f, 4.f, 6.f, 5.f, 6.f, 5.f, 7.f}), new GlobalEventTriggerEvent(timerEventTrigger), 140);
        worldA.addWorldEventTrigger(worldEventTrigger);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setLooping(false);
        timerEventTrigger.setTimer(new int[]{140, 1});
        timerEventTrigger.setEvent(new PlatformSolidityEvent(platform, true));
        worldEventTrigger = new ResettingEventTrigger(new Polygon(new float[]{4.f, 7.f, 4.f, 6.f, 5.f, 6.f, 5.f, 7.f}), new GlobalEventTriggerEvent(timerEventTrigger), 140);
        worldA.addWorldEventTrigger(worldEventTrigger);

        worldEventTrigger = buildKeyOfFirst(Color.CYAN);
        worldEventTrigger.setPosition(1.f, 6.f);
        worldA.addWorldEventTrigger(worldEventTrigger);
        worldA.addRenderable(worldEventTrigger);

        worldGroup.setInteraction(playerA);
        worldGroup.addWorld(worldA);

        return worldGroup;
    }

    public static WorldGroup buildWorldGroupFifth() {
        WorldGroup worldGroup = new WorldGroup();
        worldGroup.setId(4);
        worldGroup.setName("Disappearing Paths");

        PlayerAgent playerA;
        World worldA, worldB;
        Story story;
        Answer answer;
        Array<Answer> answers;
        WorldInteractable interactable;
        Platform platform;
        Collision collision;
        WorldEventTrigger worldEventTrigger;
        TimerEventTrigger timerEventTrigger;
        GlobalEvent[] globalEvents;


        playerA = buildAshley();
        playerA.adjustItem("key-of-fourth", 1);
        playerA.adjustItem("key-of-second", 1);
        playerA.setPosition(1.2f, 3.f);
        worldA = new World(worldGroup, playerA, "Disappearing Platforms");
        worldA.setWorldDelta(new Vector2(0f, -.04f));
        worldA.addRenderable(new RenderableAdapter(new PolygonRenderer(new Polygon[]{new Polygon(new float[]{
                0.f, 10.f,
                0.f, 6.f,
                3.f, 6.f,
                3.f, 5.f,
                0.f, 5.f,
                0.f, 0.f,
                12.f, 0.f,
                12.f, 3.f,
                14.f, 3.f,
                14.f, 10f}), new Polygon(new float[]{
                1.f, 3.f,
                1.f, 2.f,
                5.f, 2.f,
                5.f, 3.f,})}, Color.RED)));
        worldA.addRenderable(playerA);

        story = new Story(worldGroup);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 2));
        story.addDialog(1, new Dialog(story, playerA, "Aisling's gone I guess.", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 3));
        story.addDialog(2, new Dialog(story, playerA, "It's nice for the rhymes to be gone as well.", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 4));
        story.addDialog(3, new Dialog(story, playerA, "But I'm still stuck here.", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 0));
        story.addDialog(4, new Dialog(story, playerA, "Guess I should get going.", answers));
        playerA.setStory(story);

        interactable = buildDoor("First Door", Color.MAGENTA);
        interactable.setPosition(0.5f, 6.f);
        story = new Story(worldGroup);
        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "[Try <Key of First>.]", 2, "key-of-first", 1));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[A steel door stands against the wall with a black '1' painted on it.]", answers));
        answers = new Array<Answer>();
        answers.add(new ExitAnswer(story, "[Step into darkness.]", 0));
        story.addDialog(2, new Dialog(story, interactable, "[The door swings open on oiled hinges and exposes pitch blackness.]", answers));
        worldA.addCollisionInteractable(interactable);
        worldA.addRenderable(interactable);

        platform = new Platform(new Polygon(new float[]{0.f, 0.f, 0.f, -1.f, 12.f, -1.f, 12.f, 0.f}));
        platform.setSurface(new Vector2(0f, 0f), new Vector2(12f, 0f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{1.f, 3.0f, 1.f, 2.0f, 5.f, 2.0f, 5.f, 3.0f}));
        platform.setSurface(new Vector2(1f, 3f), new Vector2(5f, 3f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{0.f, 6.f, 0.f, 5.f, 3.f, 5.f, 3.f, 6.f}));
        platform.setSurface(new Vector2(0.f, 6.f), new Vector2(3.f, 6.f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{12.f, 3.f, 12.f, -1.f, 14.f, -1.f, 14.f, 3.f}));
        platform.setSurface(new Vector2(12.f, 3.f), new Vector2(14.f, 3.f));
        worldA.addPlatform(platform);

        collision = new Collision(new Polygon(new float[]{-1.f, 10.f, -1.f, -1f, 0.f, -1.f, 0.f, 10.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{-1.f, 11.f, -1.f, 10.f, 15.f, 10.f, 15.f, 11.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{14.f, 11.f, 14.f, -1.f, 15.f, -1.f, 15.f, 11.f}));
        worldA.addCollision(collision);

        platform = buildPlatformByMeter(1, false, Color.MAGENTA);
        platform.setPosition(13.f, 4.5f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        platform = buildPlatformByMeter(1, false, Color.MAGENTA);
        platform.setPosition(13.f, 6.0f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);

        platform = buildPlatformByMeter(1, false, Color.MAGENTA);
        platform.setPosition(0.f, 0.f);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{30, 60, 30, 60});
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(0.f, .05f), new Vector2(0.f, 0.f), new Vector2(0.f, -.05f), new Vector2(0.f, 0.f)}));
        worldA.addGlobalEventTrigger(timerEventTrigger);


        interactable = buildKeyPlate("Key-plate of Fourth", Color.MAGENTA);
        interactable.setPosition(4.f, 3.f);
        story = new Story(worldGroup);


        Vector2[] vectors = new Vector2[]{new Vector2(5, 2), new Vector2(6, 2), new Vector2(7, 3), new Vector2(8, 2), new Vector2(9, 2), new Vector2(10, 3), new Vector2(11, 3), new Vector2(12, 6), new Vector2(11, 7), new Vector2(10, 8), new Vector2(9, 6), new Vector2(8, 6), new Vector2(7, 7), new Vector2(6, 6), new Vector2(5, 6), new Vector2(4, 7), new Vector2(3, 6)};
        globalEvents = new GlobalEvent[2 * vectors.length];
        for (int i = 0; i < vectors.length; i++) {
            platform = buildPlatformByMeter(1, true, Color.MAGENTA);
            platform.setPosition(vectors[i]);
            globalEvents[i * 2] = new PlatformSolidityEvent(platform, true);
            timerEventTrigger = new TimerEventTrigger(new PlatformSolidityEvent(platform, false));
            timerEventTrigger.setTimer(new int[]{30 + 20 * (i + 1), 1});
            timerEventTrigger.setLooping(false);
            globalEvents[i * 2 + 1] = new GlobalEventTriggerEvent(timerEventTrigger);
        }

        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "[Try <Key of Fourth>.]", 2, "key-of-fourth", 1));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[A plate in the wall with a '4' painted on it and a keyhole.]", answers));
        answers = new Array<Answer>();
        answers.add(new GlobalEventAnswer(story, "[Back away.]", -3, globalEvents));
        story.addDialog(2, new Dialog(story, interactable, "[The key turns within the hole and rumbles begin.]", answers));
        answers = new Array<Answer>();
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(3, new Dialog(story, interactable, "[The rumbling continues.]", answers));
        worldA.addCollisionInteractable(interactable);
        worldA.addRenderable(interactable);

        worldEventTrigger = buildKeyOfFirst(Color.MAGENTA);
        worldEventTrigger.setPosition(9.f, 6.f);
        worldA.addWorldEventTrigger(worldEventTrigger);
        worldA.addRenderable(worldEventTrigger);

        worldGroup.setInteraction(playerA);
        worldGroup.addWorld(worldA);

        return worldGroup;
    }


    public static WorldGroup buildWorldGroupSix() {
        WorldGroup worldGroup = new WorldGroup();
        worldGroup.setId(5);
        worldGroup.setName("Worlds Connected");

        PlayerAgent playerA, playerB;
        World worldA, worldB;
        Story story;
        Answer answer;
        Array<Answer> answers;
        WorldInteractable interactable;
        Platform platform;
        Collision collision;
        WorldEventTrigger worldEventTrigger;
        TimerEventTrigger timerEventTrigger;

        playerA = buildAisling();
        playerA.adjustItem("key-of-first", 1);
        playerA.adjustItem("key-of-second", 1);
        playerA.adjustItem("key-of-fourth", 1);
        playerA.setPosition(13.2f, 6.f);
        worldA = new World(worldGroup, playerA, "Pushing Blocks");
        worldA.setWorldDelta(new Vector2(0f, -.04f));
        worldA.addRenderable(new RenderableAdapter(new PolygonRenderer(new Polygon(new float[]{2.f, 8f, 2.f, 6.f, 0.f, 6.f, 0.f, 4.f, 4.f, 4.f, 4.f, 6.f, 14.f, 6.f, 14.f, 8.f}), Color.BLUE)));
        worldA.addRenderable(playerA);

        playerB = buildAshley();
        playerB.adjustItem("key-of-first", 1);
        playerB.adjustItem("key-of-second", 1);
        playerB.adjustItem("key-of-fourth", 1);
        playerB.setPosition(13.2f, 4.f);
        worldB = new World(worldGroup, playerB, "Lifting Stones");
        worldB.setWorldDelta(new Vector2(0f, -.04f));
        worldB.addRenderable(new RenderableAdapter(new PolygonRenderer(new Polygon(new float[]{0.f, 6.f, 0.f, 4.f, 2.f, 4.f, 2.f, 2.f, 14.f, 2.f, 14.f, 4.f, 4.f, 4.f, 4.f, 6.f}), Color.RED)));
        worldB.addRenderable(playerB);

        story = new Story(worldGroup);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 2));
        story.addDialog(1, new Dialog(story, playerA, "It seems we're back in each other's heads;\n... What was that you just said?", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 3));
        story.addDialog(2, new Dialog(story, playerB, "Nothing! I swear. Let's just keep going.", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 4));
        story.addDialog(3, new Dialog(story, playerA, "These stones they seem linked to another side;\nLet me go first and be your guide!", answers));
        answers = new Array<Answer>();
        answers.add(new TapAnswer(story, 0));
        story.addDialog(4, new Dialog(story, playerB, "YES. Let's just keep going.", answers));
        playerA.setStory(story);

        interactable = buildDoor("Door of First", Color.PURPLE);
        interactable.setPosition(.5f, 4.f);
        story = new Story(worldGroup);
        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "[Try <Key of Fourth>.]", 2, "key-of-first", 1));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[The door stands silent, still and cold;\nIts wood is weathered, hard and old;\nA number, a hole, a lock, no key;\nA passage barred, nowhere to flee.]", answers));
        answers = new Array<Answer>();
        answers.add(new ExitAnswer(story, "[Step into darkness.]", 0));
        story.addDialog(2, new Dialog(story, interactable, "[The key did turn within the lock;\nThe door did open in the rock;\nThe hinges sound a horrible creak;\nThe passage shown is dark and bleak.]", answers));
        worldA.addCollisionInteractable(interactable);
        worldA.addRenderable(interactable);

        platform = new Platform(new Polygon(new float[]{0.f, 4f, 0.f, -1.f, 4.f, -1.f, 4.f, 4.f}));
        platform.setSurface(new Vector2(0f, 4f), new Vector2(4f, 4f));
        worldA.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{4.f, 6.f, 4.f, -1.f, 14.f, -1.f, 14.f, 6.f}));
        platform.setSurface(new Vector2(4f, 6f), new Vector2(14f, 6f));
        worldA.addPlatform(platform);

        collision = new Collision(new Polygon(new float[]{-1.f, 7.f, -1.f, -1f, 0.f, -1.f, 0.f, 7.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{-1.f, 9.f, -1.f, 6.f, 2.f, 6.f, 2.f, 9.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{1.f, 9.f, 1.f, 8.f, 15.f, 8.f, 15.f, 9.f}));
        worldA.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{14.f, 9.f, 14.f, -1.f, 15.f, -1.f, 15.f, 9.f}));
        worldA.addCollision(collision);


        interactable = buildDoor("First Door", Color.PURPLE);
        interactable.setPosition(.5f, 4.f);
        story = new Story(worldGroup);
        interactable.setStory(story);
        story.setDialog(1);
        answers = new Array<Answer>();
        answers.add(new ItemRequiredAnswer(story, "[Try <Key of First>.]", 2, "key-of-first", 1));
        answers.add(new DialogAnswer(story, "[Back away.]", 0));
        story.addDialog(1, new Dialog(story, interactable, "[A steel door stands against the wall with a black '1' painted on it.]", answers));
        answers = new Array<Answer>();
        answers.add(new ExitAnswer(story, "[Step into darkness.]", 0));
        story.addDialog(2, new Dialog(story, interactable, "[The door swings open on oiled hinges and exposes pitch blackness.]", answers));
        worldB.addCollisionInteractable(interactable);
        worldB.addRenderable(interactable);


        platform = new Platform(new Polygon(new float[]{0.f, 4.f, 0.f, -1.f, 2.f, -1.f, 2.f, 4.f}));
        platform.setSurface(new Vector2(0f, 4f), new Vector2(2f, 4f));
        worldB.addPlatform(platform);
        platform = new Platform(new Polygon(new float[]{2.f, 2.f, 2.f, -1.f, 14.f, -1.f, 14.f, 2.f}));
        platform.setSurface(new Vector2(2f, 2f), new Vector2(14f, 2f));
        worldB.addPlatform(platform);

        collision = new Collision(new Polygon(new float[]{-1.f, 7.f, -1.f, -1f, 0.f, -1.f, 0.f, 7.f}));
        worldB.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{-1.f, 9.f, -1.f, 6.f, 4.f, 6.f, 4.f, 9.f}));
        worldB.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{4.f, 9.f, 4.f, 4.f, 15.f, 4.f, 15.f, 9.f}));
        worldB.addCollision(collision);
        collision = new Collision(new Polygon(new float[]{14.f, 9.f, 14.f, -1.f, 15.f, -1.f, 15.f, 9.f}));
        worldB.addCollision(collision);

        for (int i = 0; i < 10; i++) {
            platform = buildPlatformByMeter(1, 2, true, Color.PURPLE);
            platform.setPosition(i + 2, 4);
            worldA.addPlatform(platform);
            worldA.addRenderable(platform);
            worldB.addPlatform(platform);
            worldB.addRenderable(platform);
            worldEventTrigger = new InclusionEventTrigger(new Polygon(new float[]{i + 2, 8, i + 2, 4, i + 3, 4, i + 3, 8}));
            worldEventTrigger.setEvent(new PositionWorldObjectEvent(platform, new Vector2(i + 2, 2)));
            worldA.addWorldEventTrigger(worldEventTrigger);
            worldEventTrigger = new ExclusionEventTrigger(new Polygon(new float[]{i + 2, 8, i + 2, 4, i + 3, 4, i + 3, 8}));
            worldEventTrigger.setEvent(new PositionWorldObjectEvent(platform, new Vector2(i + 2, 4)));
            worldA.addWorldEventTrigger(worldEventTrigger);
        }

        platform = buildPlatformByMeter(2, 2, true, Color.PURPLE);
        platform.setDelta(.1f, 0);
        platform.setPosition(0, 8);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        worldB.addPlatform(platform);
        worldB.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{100, 20, 100, 20});
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(0.f, .1f), new Vector2(-.1f, 0.f), new Vector2(0.f, -.1f), new Vector2(0.1f, 0)}));
        worldA.addGlobalEventTrigger(timerEventTrigger);
        worldB.addGlobalEventTrigger(timerEventTrigger);

        platform = buildPlatformByMeter(2, 2, true, Color.PURPLE);
        platform.setDelta(-.1f, 0);
        platform.setPosition(10, 10);
        worldA.addPlatform(platform);
        worldA.addRenderable(platform);
        worldB.addPlatform(platform);
        worldB.addRenderable(platform);
        timerEventTrigger = new TimerEventTrigger();
        timerEventTrigger.setTimer(new int[]{100, 20, 100, 20});
        timerEventTrigger.setEvent(new PlatformMovementEvent(platform, new Vector2[]{new Vector2(0.f, -.1f), new Vector2(0.1f, 0), new Vector2(0.f, .1f), new Vector2(-.1f, 0.f)}));
        worldA.addGlobalEventTrigger(timerEventTrigger);
        worldB.addGlobalEventTrigger(timerEventTrigger);


        worldGroup.setInteraction(playerA);
        worldGroup.addWorld(worldA);
        worldGroup.addWorld(worldB);

        return worldGroup;
    }

    private static PlayerAgent buildPlayer(String name) {
        PlayerAgent player = new PlayerAgent(name, new Polygon(new float[]{0, 0, .6f, 0, .6f, 1.3f, 0, 1.3f}), new Polygon(new float[]{0, -.3f, .6f, -.3f, .6f, .3f, 0, .3f}));
        player.setRenderer(new PolygonRenderer(new Polygon(new float[]{0, 0, .6f, 0, .6f, 1.3f, 0, 1.3f}), Color.GREEN));
        player.setAgentController(new PlayerAgentController(player));
        player.setMaxDelta(new Vector2(.2f, 1f));
        player.setMovement(new Vector2(.05f, .38f));
        player.setJumpTime(120);
        return player;
    }

    private static PlayerAgent buildAisling() {
        PlayerAgent player = new PlayerAgent("Aisling", new Polygon(new float[]{0, 0, .6f, 0, .6f, 1.3f, 0, 1.3f}), new Polygon(new float[]{0, -.3f, .6f, -.3f, .6f, .3f, 0, .3f}));
        player.setRenderer(new PolygonRenderer(new Polygon(new float[]{
                .1f, 1.3f,
                .0f, 1.1f,
                .1f, 1.2f,
                .2f, 1.2f,
                .2f, 1.1f,
                0.f, 1.f,
                .15f, 1.f,
                .15f, .9f,
                .25f, .8f,
                .1f, .7f,
                .0f, .5f,
                .2f, .6f,
                .2f, .5f,
                .1f, .4f,
                .2f, .4f,
                .2f, .2f,
                .1f, .0f,
                .3f, .2f,
                .3f, .3f,
                .4f, .2f,
                .5f, .0f,
                .5f, .2f,
                .4f, .4f,
                .5f, .4f,
                .4f, .5f,
                .4f, .6f,
                .6f, .5f,
                .5f, .7f,
                .35f, .8f,
                .45f, .9f,
                .45f, 1.0f,
                .6f, 1.0f,
                .4f, 1.1f,
                .4f, 1.2f,
                .3f, 1.3f}), Color.GREEN));
        player.setAgentController(new PlayerAgentController(player));
        player.setMaxDelta(new Vector2(.2f, 1f));
        player.setMovement(new Vector2(.05f, .38f));
        player.setJumpTime(120);
        return player;
    }

    private static PlayerAgent buildAshley() {
        PlayerAgent player = new PlayerAgent("Ashley", new Polygon(new float[]{0, 0, .6f, 0, .6f, 1.3f, 0, 1.3f}), new Polygon(new float[]{0, -.3f, .6f, -.3f, .6f, .3f, 0, .3f}));
        player.setRenderer(new PolygonRenderer(new Polygon(new float[]{.25f, 1.3f, .15f, 1.2f, .15f, 1.f, .25f, .95f, .1f, .9f, .0f, .7f, .0f, .5f, .1f, .7f, .2f, .8f, .2f, .6f, .15f, .5f, .15f, .3f, .0f, .0f, .2f, .2f, .3f, .4f, .4f, .2f, .4f, .0f, .5f, .2f, .45f, .5f, .4f, .6f, .4f, .8f, .45f, .65f, .6f, .5f, .5f, .9f, .35f, .95f, .45f, 1.f, .45f, 1.1f, .6f, 1.1f, .45f, 1.15f, .45f, 1.25f, .35f, 1.3f}), Color.GREEN));
        player.setAgentController(new PlayerAgentController(player));
        player.setMaxDelta(new Vector2(.2f, 1f));
        player.setMovement(new Vector2(.05f, .38f));
        player.setJumpTime(120);
        return player;
    }

    private static WorldInteractable buildWorldInteractable(String name, Color color) {
        WorldInteractable interactable = new WorldInteractable(name, new Polygon(new float[]{0, 0, 1, 0, 1, 2, 0, 2}), 1);
        interactable.setRenderer(new PolygonRenderer(interactable.getPolygon(), color));
        return interactable;
    }


    private static WorldInteractable buildShade(String name, Color color) {
        WorldInteractable interactable = new WorldInteractable(name, new Polygon(new float[]{0, 0, 1, 0, 1, 2, 0, 2}), 1);
        interactable.setRenderer(new PolygonRenderer(new Polygon[]{new Polygon(new float[]{.35f, 1.65f, .45f, 1.55f, .5f, 1.575f, .55f, 1.55f, .65f, 1.65f, .5f, 1.6f}), new Polygon(new float[]{.4f, 1.2f, .3f, 1.1f, .7f, 1.1f, .6f, 1.2f}), new Polygon(new float[]{.3f, 1.1f, .3f, .9f, .7f, .9f, .7f, 1.1f}), new Polygon(new float[]{.3f, 1.7f, .3f, 1.5f, .4f, 1.4f, .2f, 1.3f, .1f, 1.2f, .2f, .8f, .4f, .6f, .3f, .6f, .5f, .4f, .4f, .4f, .5f, .2f, .5f, .3f, .7f, .3f, .6f, .5f, .8f, .5f, .7f, .7f, .8f, .8f, .9f, 1.2f, .8f, 1.3f, .6f, 1.4f, .7f, 1.5f, .7f, 1.7f, .6f, 1.8f, .4f, 1.8f})}, color));
        return interactable;
    }

    private static WorldInteractable buildDoor(String name, Color color) {
        WorldInteractable interactable = new WorldInteractable(name, new Polygon(new float[]{0, 0, 1, 0, 1, 2, 0, 2}), 1);
        interactable.setRenderer(new PolygonRenderer(new Polygon[]{new Polygon(new float[]{0.f, 1.7f, 0.f, 0.f, 1.f, 0.f, 1.f, 1.7f, .8f, 1.9f, .2f, 1.9f}), new Polygon(new float[]{.1f, .7f, .2f, .6f, .3f, .7f, .2f, .8f})}, color));
        return interactable;
    }

    public static WorldInteractable buildKeyPlate(String name, Color color) {
        WorldInteractable interactable = new WorldInteractable(name, new Polygon(new float[]{0, 0, 1, 0, 1, 1.4f, 0, 1.4f}), 1);
        interactable.setRenderer(new PolygonRenderer(new Polygon[]{new Polygon(new float[]{.1f, 1f, .5f, .6f, .9f, 1f, .5f, 1.4f}), new Polygon(new float[]{.45f, 1.2f, .45f, .8f, .55f, .8f, .55f, 1.2f})}, color));
        return interactable;
    }

    private static WorldEventTrigger buildGoldItem() {
        WorldEventTrigger worldEventTrigger = new InclusionEventTrigger(new Polygon(new float[]{.2f, .8f, .2f, 0.f, .8f, 0.f, .8f, .8f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.2f, .6f, .2f, .2f, .5f, 0.f, .8f, .2f, .8f, .6f, .5f, .8f}), Color.YELLOW));
        worldEventTrigger.setEvent(new ItemEvent("gold", 1, worldEventTrigger));
        return worldEventTrigger;
    }

    private static WorldEventTrigger buildCrystalItem() {
        WorldEventTrigger worldEventTrigger = new InclusionEventTrigger(new Polygon(new float[]{.1f, 0, .9f, 0, .9f, 1, .1f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.2f, .6f, .5f, .2f, .8f, .6f, .5f, 1.f}), Color.PURPLE));
        worldEventTrigger.setEvent(new AllItemEvent("crystal", 1, worldEventTrigger));
        return worldEventTrigger;
    }

    private static WorldEventTrigger buildKeyOfFourth(Color color) {
        WorldEventTrigger worldEventTrigger = new InclusionEventTrigger(new Polygon(new float[]{.2f, 0, .8f, 0, .8f, 1, .2f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.3f, .8f, .3f, .7f, .4f, .7f, .4f, .4f, .3f, .3f, .3f, .2f, .3f, .1f, .4f, 0.f, .5f, 0.f, .6f, .1f, .6f, .2f, .5f, .3f, .5f, .7f, .7f, .7f, .7f, .9f, .6f, 1.f, .6f, .8f, .5f, .8f, .5f, 1.f, .4f, 1.f, .4f, .8f}), color));
        worldEventTrigger.setEvent(new ItemEvent("key-of-fourth", 1, worldEventTrigger));
        return worldEventTrigger;
    }

    private static WorldEventTrigger buildKeyOfThird(Color color) {
        WorldEventTrigger worldEventTrigger = new InclusionEventTrigger(new Polygon(new float[]{.2f, 0, .8f, 0, .8f, 1, .2f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.2f, .9f, .2f, .8f, .3f, .8f, .4f, .9f, .5f, .8f, .4f, .7f, .5f, .6f, .4f, .5f, .3f, .6f, .2f, .6f, .2f, .5f, .3f, .4f, .5f, .4f, .6f, .5f, .6f, .3f, .5f, .2f, .5f, .1f, .6f, 0.f, .7f, 0.f, .8f, .1f, .8f, .2f, .7f, .3f, .7f, 1.f, .6f, 1.f, .6f, .9f, .5f, 1.f, .3f, 1.f}), color));
        worldEventTrigger.setEvent(new ItemEvent("key-of-third", 1, worldEventTrigger));
        return worldEventTrigger;
    }

    private static WorldEventTrigger buildKeyOfSecond(Color color) {
        WorldEventTrigger worldEventTrigger = new InclusionEventTrigger(new Polygon(new float[]{.2f, 0, .8f, 0, .8f, 1, .2f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.3f, .9f, .3f, .8f, .4f, .8f, .5f, .9f, .6f, .8f, .3f, .5f, .3f, .4f, .5f, .4f, .5f, .3f, .4f, .2f, .4f, .1f, .5f, .0f, .6f, .0f, .7f, .1f, .7f, .2f, .6f, .3f, .6f, .4f, .7f, .4f, .7f, .5f, .5f, .5f, .7f, .7f, .7f, .9f, .6f, 1.f, .4f, 1.f}), color));
        worldEventTrigger.setEvent(new ItemEvent("key-of-second", 1, worldEventTrigger));
        return worldEventTrigger;
    }

    private static WorldEventTrigger buildKeyOfFirst(Color color) {
        WorldEventTrigger worldEventTrigger = new InclusionEventTrigger(new Polygon(new float[]{.2f, 0, .8f, 0, .8f, 1, .2f, 1f}));
        worldEventTrigger.setRenderer(new PolygonRenderer(new Polygon(new float[]{.35f, .9f, .45f, .9f, .45f, .5f, .35f, .5f, .35f, .4f, .45f, .4f, .35f, .2f, .35f, .1f, .45f, 0.f, .55f, 0.f, .65f, .1f, .65f, .2f, .55f, .3f, .55f, .4f, .65f, .4f, .65f, .5f, .55f, .5f, .55f, 1.f, .45f, 1.f}), color));
        worldEventTrigger.setEvent(new ItemEvent("key-of-first", 1, worldEventTrigger));
        return worldEventTrigger;
    }

    private static Platform buildPlatformByMeter(int meters, boolean collides, Color color) {
        Platform platform = new Platform(new Polygon((new float[]{0, 0, 0, -.5f, meters, -.5f, meters, 0})));
        platform.setCollidable(collides);
        platform.setSurface(new Vector2(0, 0), new Vector2(meters, 0));
        if (collides) {
            platform.setRenderer(new PolygonRenderer(platform.getPolygon(), color));
        } else {
            platform.setRenderer(new PolygonRenderer(platform.getPolygonPlatform(), color));
        }
        return platform;
    }

    private static Platform buildPlatformByMeter(int width, int depth, boolean collides, Color color) {
        Platform platform = new Platform(new Polygon((new float[]{0, 0, 0, -depth, width, -depth, width, 0})));
        platform.setCollidable(collides);
        platform.setSurface(new Vector2(0, 0), new Vector2(width, 0));
        if (collides) {
            platform.setRenderer(new PolygonRenderer(platform.getPolygon(), color));
        } else {
            platform.setRenderer(new PolygonRenderer(platform.getPolygonPlatform(), color));
        }
        return platform;
    }
}
