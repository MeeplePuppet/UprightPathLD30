package com.uprightpath.ld.thirty.logic;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.uprightpath.ld.thirty.Controls;
import com.uprightpath.ld.thirty.Main;
import com.uprightpath.ld.thirty.logic.agents.PlayerAgent;
import com.uprightpath.ld.thirty.logic.interactables.WorldInteractable;
import com.uprightpath.ld.thirty.rendering.Renderable;
import com.uprightpath.ld.thirty.rendering.WorldRenderer;


/**
 * Created by Geo on 8/22/2014.
 */
public class World {
    private WorldGroup worldGroup;
    private String name;
    private PlayerAgent player;
    private WorldRenderer worldRenderer;
    private Array<Agent> agents = new Array<Agent>();
    private Array<Collision> collisions = new Array<Collision>();
    private Array<Platform> platforms = new Array<Platform>();
    private Array<WorldEventTrigger> worldEventTriggers = new Array<WorldEventTrigger>();
    private Array<GlobalEventTrigger> globalEventTriggers = new Array<GlobalEventTrigger>();
    private Array<WorldInteractable> collisionInteractables = new Array<WorldInteractable>();
    private Array<Renderable> renderables = new Array<Renderable>();
    private Array<Agent> agentsRemove = new Array<Agent>();
    private Array<Collision> collisionsRemove = new Array<Collision>();
    private Array<Platform> platformsRemove = new Array<Platform>();
    private Array<WorldEventTrigger> worldEventTriggersRemove = new Array<WorldEventTrigger>();
    private Array<GlobalEventTrigger> globalEventTriggersRemove = new Array<GlobalEventTrigger>();
    private Array<WorldInteractable> collisionInteractablesRemove = new Array<WorldInteractable>();
    private Array<Renderable> renderablesRemove = new Array<Renderable>();
    private Vector2 worldDelta = new Vector2();
    private Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();

    public World() {

    }

    public World(WorldGroup worldGroup, PlayerAgent player, String name) {
        this.player = player;
        this.worldGroup = worldGroup;
        this.name = name;
        this.agents.add(player);
    }

    public void setWorldDelta(float x, float y) {
        worldDelta.set(x, y);
    }

    public void setWorldDelta(Vector2 worldDelta) {
        this.worldDelta.set(worldDelta);
    }

    public void applyPhysics() {
        Agent agent;
        Agent agentCollide;
        Collision collision;
        Platform platform, collided = null;
        WorldEventTrigger worldEventTrigger;
        GlobalEventTrigger globalEventTrigger;
        WorldInteractable collisionInteractable;

        for (int j = 0; j < globalEventTriggers.size; j++) {
            globalEventTrigger = globalEventTriggers.get(j);
            if (globalEventTrigger.canTriggerEvent(this)) {
                globalEventTrigger.triggerEvent(this);
            }
            if (globalEventTrigger.remove()) {
                globalEventTriggersRemove.add(globalEventTrigger);
            }
        }

        for (int j = 0; j < platforms.size; j++) {
            platform = platforms.get(j);
            platform.translate(platform.getDelta());
        }

        for (int j = 0; j < collisions.size; j++) {
            collision = collisions.get(j);
            collision.translate(collision.getDelta());
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
                if ((!agent.isFallingThrough() || platform.isCollidable()) && position != null && position.y >= agent.getPosition().y) {
                    agent.setPosition(position);
                    collided = platform;
                    agent.getDelta().y = 0;
                }
            }
            if (agent.getPlatform() == null && collided != null) {
                Main.soundManager.playSound("land");
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

            for (int j = 0; j < agents.size; j++) {
                agentCollide = agents.get(j);
                if (agent == agentCollide) {
                    continue;
                }
                if (agentCollide.canCollide(agent)) {
                    if (agentCollide.collides(agent)) {
                        if (Intersector.overlapConvexPolygons(agent.getPolygon(), agentCollide.getPolygonBase(), mtv)) {
                            if (mtv.depth > 0) {
                                agent.translate(mtv.depth * mtv.normal.x, mtv.depth * mtv.normal.y);
                                if (mtv.depth * mtv.normal.x != 0) {
                                    agent.getDelta().x = 0;
                                }
                                if (mtv.depth * mtv.normal.y != 0) {
                                    agent.getDelta().y = 0;
                                }
                                agentCollide.collidedWith(agent);
                            }
                        }
                    }
                }
            }

            for (int j = 0; j < collisionInteractables.size; j++) {
                collisionInteractable = collisionInteractables.get(j);
                if (collisionInteractable.canCollide(agent) && agent == this.player && Controls.INTERACT.isDown()) {
                    collisionInteractable.interact(this, agent);
                }
            }

            for (int j = 0; j < worldEventTriggers.size; j++) {
                worldEventTrigger = worldEventTriggers.get(j);
                if (worldEventTrigger.canTriggerEvent(this, agent)) {
                    worldEventTrigger.triggerEvent(this, agent);
                }
            }
        }
    }

    public void cleanWorld() {
        for (int i = 0; i < agentsRemove.size; i++) {
            agents.removeValue(agentsRemove.get(i), true);
            renderables.removeValue(agentsRemove.get(i), true);
        }
        renderablesRemove.clear();
        for (int i = 0; i < collisionsRemove.size; i++) {
            collisions.removeValue(collisionsRemove.get(i), true);
            if (collisionsRemove.get(i) instanceof Renderable) {
                renderables.removeValue((Renderable) collisionsRemove.get(i), true);
            }
        }
        collisionsRemove.clear();
        for (int i = 0; i < platformsRemove.size; i++) {
            platforms.removeValue(platformsRemove.get(i), true);
            renderables.removeValue(platformsRemove.get(i), true);
            for (int j = 0; j < agents.size; j++) {
                if (agents.get(j).getPlatform() == platformsRemove.get(i)) {
                    agents.get(j).setPlatform(null);
                }

            }
        }
        platformsRemove.clear();
        for (int i = 0; i < globalEventTriggersRemove.size; i++) {
            globalEventTriggers.removeValue(globalEventTriggersRemove.get(i), true);
            if (globalEventTriggersRemove.get(i) instanceof Renderable) {
                renderables.removeValue((Renderable) globalEventTriggersRemove.get(i), true);
            }
        }
        globalEventTriggersRemove.clear();
        for (int i = 0; i < worldEventTriggersRemove.size; i++) {
            worldEventTriggers.removeValue(worldEventTriggersRemove.get(i), true);
            if (worldEventTriggersRemove.get(i) instanceof Renderable) {
                renderables.removeValue((Renderable) worldEventTriggersRemove.get(i), true);
            }
        }
        worldEventTriggersRemove.clear();
        for (int i = 0; i < collisionInteractablesRemove.size; i++) {
            collisionInteractables.removeValue(collisionInteractablesRemove.get(i), true);
            if (collisionInteractablesRemove.get(i) instanceof Renderable) {
                renderables.removeValue((Renderable) collisionInteractablesRemove.get(i), true);
            }
        }
        collisionInteractablesRemove.clear();
        for (int i = 0; i < renderablesRemove.size; i++) {
            renderables.removeValue(renderablesRemove.get(i), true);
        }
        renderablesRemove.clear();
    }

    public void addPlatform(Platform platform) {
        this.platforms.add(platform);
    }

    public void removePlatform(Platform platform) {
        this.platformsRemove.add(platform);
    }

    public void addWorldEventTrigger(WorldEventTrigger eventTrigger) {
        this.worldEventTriggers.add(eventTrigger);
    }

    public void removeWorldEventTrigger(WorldEventTrigger eventTrigger) {
        this.worldEventTriggersRemove.add(eventTrigger);
    }

    public void addAgent(Agent agent) {
        this.agents.add(agent);
    }

    public void removeAgent(Agent agent) {
        this.agentsRemove.add(agent);
    }

    public void addCollision(Collision collision) {
        this.collisions.add(collision);
    }

    public void removeCollision(Collision collision) {
        this.collisionsRemove.add(collision);
    }

    public void addGlobalEventTrigger(GlobalEventTrigger worldEventTrigger) {
        this.globalEventTriggers.add(worldEventTrigger);
    }

    public void removeGlobalEventTrigger(GlobalEventTrigger worldEventTrigger) {
        this.globalEventTriggersRemove.add(worldEventTrigger);
    }

    public void addCollisionInteractable(WorldInteractable interactable) {
        this.collisionInteractables.add(interactable);
    }

    public void removeCollisionInteractable(WorldInteractable interactable) {
        this.collisionInteractablesRemove.add(interactable);
    }

    public void addRenderable(Renderable renderable) {
        this.renderables.add(renderable);
    }

    public void removeRenderable(Renderable renderable) {
        this.renderablesRemove.add(renderable);
    }

    public Main getMain() {
        return worldGroup.getMain();
    }

    public PlayerAgent getPlayer() {
        return player;
    }

    public WorldRenderer getWorldRenderer() {
        return worldRenderer;
    }

    public WorldGroup getWorldGroup() {
        return worldGroup;
    }

    public String getName() {
        return name;
    }

    public Array<Renderable> getRenderables() {
        return renderables;
    }

    public void exit() {
        worldGroup.exit(this);
    }

    public Array<Agent> getAgents() {
        return agents;
    }

    public Array<Collision> getCollisions() {
        return collisions;
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }

    public Array<WorldInteractable> getCollisionInteractables() {
        return collisionInteractables;
    }

    public Array<GlobalEventTrigger> getGlobalEventTriggers() {
        return globalEventTriggers;
    }

    public Array<WorldEventTrigger> getWorldEventTriggers() {
        return worldEventTriggers;
    }

    public void createDisply() {
        destoryDisply();
        this.worldRenderer = new WorldRenderer(this);
    }

    public void destoryDisply() {
        if (worldRenderer != null) {
            worldRenderer.dispose();
            worldRenderer = null;
        }
    }
}
