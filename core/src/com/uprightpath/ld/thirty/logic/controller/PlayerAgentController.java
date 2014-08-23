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
            agent.applyDelta(-.025f, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            agent.applyDelta(.025f, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && agent.getPlatform() != null) {
            agent.applyDelta(0, 1f);
            jumpTime = 10;
        } else if (Gdx.input.isKeyPressed(Input.Keys.Z) && jumpTime > 0) {
            agent.applyDelta(0, .2f);
            jumpTime--;
        } else {
            jumpTime = 0;
        }

    }
}
