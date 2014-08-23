package com.uprightpath.ld.thirty.logic.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.uprightpath.ld.thirty.logic.AgentController;
import com.uprightpath.ld.thirty.logic.agents.Player;

/**
 * Created by Geo on 8/22/2014.
 */
public class PlayerAgentController extends AgentController<Player> {
    private int jumpTime;

    public PlayerAgentController(Player agent) {
        super(agent);
    }

    @Override
    public void updateAgentLogic() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            agent.applyDelta(-agent.getMovement().x, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            agent.applyDelta(agent.getMovement().x, 0);
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
        } else if (Gdx.input.isKeyPressed(Input.Keys.Z) && jumpTime > 0) {
            agent.applyDelta(0, agent.getMovement().y / agent.getJumpTime());
            jumpTime--;
        } else {
            jumpTime = 0;
        }

    }
}
