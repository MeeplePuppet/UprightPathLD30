package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.uprightpath.ld.thirty.Main;
import com.uprightpath.ld.thirty.logic.agents.Player;
import com.uprightpath.ld.thirty.rendering.WorldRenderer;


/**
 * Created by Geo on 8/22/2014.
 */
public class World {
    private final Main main;
    private final WorldGroup worldGroup;
    private Player player;
    private final WorldRenderer worldRenderer;
    private Array<Agent> agents = new Array<Agent>();
    private Array<Collision> collisions = new Array<Collision>();
    private Array<Platform> platforms = new Array<Platform>();
    private Array<AgentEventTrigger> agentEventTriggers = new Array<AgentEventTrigger>();
    private Array<WorldEventTrigger> worldEventTriggers = new Array<WorldEventTrigger>();
    private Vector2 worldDelta = new Vector2();
    private Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();

    public World(Main main, WorldGroup worldGroup, Player player) {
        this.player = player;
        this.worldGroup = worldGroup;
        this.main = main;
        this.agents.add(player);
        worldRenderer = new WorldRenderer(this.main, this);
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
        AgentEventTrigger agentEventTrigger;
        WorldEventTrigger worldEventTrigger;

        for (int j = 0; j < worldEventTriggers.size; j++) {
            worldEventTrigger = worldEventTriggers.get(j);
            if (worldEventTrigger.canTriggerEvent()) {
                worldEventTrigger.triggerEvent();
            }
        }

        for (int j = 0; j < platforms.size; j++) {
            platform = platforms.get(j);
            platform.translate(platform.getDelta());
        }

        for (int j = 0; j < collisions.size; j++) {
            collision = collisions.get(j);
            collided.translate(collision.getDelta());
        }


        for (int i = 0; i < agents.size; i++) {
            agent = agents.get(i);
            agent.getAgentController().updateAgentLogic();
            agent.applyPlatform();
            if (agent.getPlatform() == null) {
                agent.applyDelta(worldDelta);
                agent.applyDeltaLimits();
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
                agent.applyDeltaLimits();
                agent.translate(0, agent.getDelta().y);
            }
            agent.applyDeltaLimits();
            for (int j = 0; j < platforms.size; j++) {
                platform = platforms.get(j);
                Vector2 position = platform.getPosition(agent);
                System.out.println("Pos: " + position);
                if ((!agent.isFallingThrough() || platform.isCollidable()) && position != null && position.y >= agent.getPosition().y) {
                    agent.setPosition(position);
                    collided = platform;
                    agent.getDelta().y = 0;
                }
            }
            agent.setPlatform(collided);

            for (int j = 0; j < platforms.size; j++) {
                platform = platforms.get(j);
                if (platform.canCollide(agent) && platform.getPosition(agent) == null) {
                    if (Intersector.overlapConvexPolygons(agent.getPolygon(), platform.getPolygon(), mtv)) {
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
                    if (Intersector.overlapConvexPolygons(agent.getPolygon(), collision.getPolygon(), mtv)) {
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
            for (int j = 0; j < agentEventTriggers.size; j++) {
                agentEventTrigger = agentEventTriggers.get(j);
                if (agentEventTrigger.canTriggerEvent(agent)) {
                    agentEventTrigger.triggerEvent(agent);
                }
            }
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

    public void addEventTrigger(AgentEventTrigger eventTrigger) {
        this.agentEventTriggers.add(eventTrigger);
    }

    public Main getMain() {
        return main;
    }

    public void addWorldEventTrigger(WorldEventTrigger worldEventTrigger) {
        this.worldEventTriggers.add(worldEventTrigger);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public WorldRenderer getWorldRenderer() {
        return worldRenderer;
    }

    public WorldGroup getWorldGroup() {
        return worldGroup;
    }
}
