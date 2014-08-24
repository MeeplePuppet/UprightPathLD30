package com.uprightpath.ld.thirty.logic.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.uprightpath.ld.thirty.logic.AgentController;
import com.uprightpath.ld.thirty.logic.agents.PlayerAgent;

/**
 * Created by Geo on 8/22/2014.
 */
public class PlayerAgentController extends AgentController<PlayerAgent> {
    private int jumpTime;

    public PlayerAgentController() {}

    public PlayerAgentController(PlayerAgent agent) {
        super(agent);
    }

    @Override
    public void updateAgentLogic() {
        agent.setMoving(false);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            agent.applyDelta(-agent.getMovement().x / (agent.getPlatform() == null ? 10 : 1), 0);
            agent.setMoving(true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            agent.applyDelta(agent.getMovement().x / (agent.getPlatform() == null ? 10 : 1), 0);
            agent.setMoving(true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            agent.setFallingThrough(true);
        } else {
            agent.setFallingThrough(false);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            agent.setPlatform(null);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && agent.getPlatform() != null) {
            agent.applyDelta(0, agent.getMovement().y);
            agent.setPlatform(null);
            jumpTime = agent.getJumpTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.Z) && agent.getPlatform() == null && jumpTime > 0) {
            agent.applyDelta(0, agent.getMovement().y / agent.getJumpTime());
            jumpTime--;
        } else {
            jumpTime = 0;
        }

    }
}
