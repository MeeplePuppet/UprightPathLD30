package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.uprightpath.ld.thirty.Main;


/**
 * Created by Geo on 8/22/2014.
 */
public class World {
    private final Main main;
    private Array<Agent> agents = new Array<Agent>();
    private Array<Collision> collisions = new Array<Collision>();
    private Array<Platform> platforms = new Array<Platform>();
    private Array<EventTrigger> eventTriggers = new Array<EventTrigger>();
    private Vector2 worldDelta;
    private Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();

    public World(Main main) {
        this.main = main;
        worldDelta = new Vector2(0, -.25f);
    }

    public void setWorldDelta(float x, float y) {
        worldDelta.set(x, y);
    }

    public void setWorldDelta(Vector2 worldDelta) {
        this.worldDelta.set(worldDelta);
    }

    public void applyPhysics() {
        Agent agent;
        Collision collision;
        Platform platform, collided = null;
        EventTrigger eventTrigger;
        for (int i = 0; i < agents.size; i++) {
            agent = agents.get(i);
            agent.getAgentController().updateAgentLogic();
            if (agent.getPlatform() == null) {
                agent.applyDelta(worldDelta);
                agent.translate(agent.getDelta());
            } else {
                agent.applyDelta(worldDelta.x, 0);
                agent.translate(agent.getDelta().x, 0);
                Vector2 position = agent.getPlatform().getPosition(agent);
                if (position != null) {
                    agent.setPosition(position);
                    collided = agent.getPlatform();
                    if (agent.getDelta().y < 0) {
                        agent.getDelta().y = 0;
                    }
                } else {
                    agent.setPlatform(null);
                }
                agent.translate(0, agent.getDelta().y);
            }
            agent.applyPlatform();
            for (int j = 0; j < platforms.size; j++) {
                platform = platforms.get(j);
                Vector2 position = platform.getPosition(agent);
                if (position != null) {
                    if (position.y > agent.getPosition().y) {
                        agent.setPosition(position);
                        collided = platform;
                        agent.getDelta().y = 0;
                    }
                }
            }
            agent.setPlatform(collided);

            for (int j = 0; j < platforms.size; j++) {
                platform = platforms.get(j);
                if (platform.canCollide(agent) && platform.getPosition(agent) == null) {
                    if (Intersector.overlapConvexPolygons(agent.getPolygonCollision(), platform.getPolygon(), mtv)) {
                        if (mtv.depth > 0) {
                            agent.translate(mtv.depth * mtv.normal.x, mtv.depth * mtv.normal.y);
                            if (mtv.depth * mtv.normal.x != 0) {
                                agent.getDelta().x = 0;
                            }
                            if (mtv.depth * mtv.normal.y != 0) {
                                agent.getDelta().y = 0;
                            }
                        }
                    }
                }
            }

            for (int j = 0; j < collisions.size; j++) {
                collision = collisions.get(j);
                if (collision.canCollide(agent)) {
                    if (Intersector.overlapConvexPolygons(agent.getPolygonCollision(), collision.getPolygon(), mtv)) {
                        if (mtv.depth > 0) {
                            agent.translate(mtv.depth * mtv.normal.x, mtv.depth * mtv.normal.y);
                            if (mtv.depth * mtv.normal.x != 0) {
                                agent.getDelta().x = 0;
                            }
                            if (mtv.depth * mtv.normal.y != 0) {
                                agent.getDelta().y = 0;
                            }
                        }
                    }
                }
            }
            for (int j = 0; j < eventTriggers.size; j++) {
                eventTrigger = eventTriggers.get(i);
                if (eventTrigger.canTriggerEvent(agent)) {
                    eventTrigger.triggerEvent(agent);
                }
            }
            System.out.println(agent.getDelta());
        }
    }

    public Array<Collision> getCollisions() {
        return collisions;
    }

    public Array<Agent> getAgents() {
        return agents;
    }

    public void addAgent(Agent agent) {
        this.agents.add(agent);
    }

    public void addCollision(Collision collision) {
        this.collisions.add(collision);
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }

    public void addPlatform(Platform platform) {
        this.platforms.add(platform);
    }

    public void addEventTrigger(PolygonEventTrigger eventTrigger) {
        this.eventTriggers.add(eventTrigger);
    }

    public Main getMain() {
        return main;
    }
}
