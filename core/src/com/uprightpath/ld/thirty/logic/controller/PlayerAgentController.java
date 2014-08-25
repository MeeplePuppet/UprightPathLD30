package com.uprightpath.ld.thirty.logic.controller;

import com.uprightpath.ld.thirty.Controls;
import com.uprightpath.ld.thirty.Main;
import com.uprightpath.ld.thirty.logic.AgentController;
import com.uprightpath.ld.thirty.logic.agents.PlayerAgent;

/**
 * Created by Geo on 8/22/2014.
 */
public class PlayerAgentController extends AgentController<PlayerAgent> {
    private int jumpTime;

    public PlayerAgentController() {
    }

    public PlayerAgentController(PlayerAgent agent) {
        super(agent);
    }

    @Override
    public void updateAgentLogic() {
        agent.setMoving(false);
        if (Controls.LEFT.isDown()) {
            agent.applyDelta(-agent.getMovement().x / (agent.getPlatform() == null ? 10 : 1), 0);
            agent.setMoving(true);
        }
        if (Controls.RIGHT.isDown()) {
            agent.applyDelta(agent.getMovement().x / (agent.getPlatform() == null ? 10 : 1), 0);
            agent.setMoving(true);
        }
        if (Controls.DOWN.isDown()) {
            agent.setFallingThrough(true);
        } else {
            agent.setFallingThrough(false);
        }
        if (Controls.JUMP.isJustDown() && Controls.DOWN.isDown()) {
            agent.setPlatform(null);
            Main.soundManager.playSound("jump");
        } else if (Controls.JUMP.isJustDown() && agent.getPlatform() != null) {
            agent.applyDelta(0, agent.getMovement().y);
            agent.setPlatform(null);
            jumpTime = agent.getJumpTime();
            Main.soundManager.playSound("jump");
        } else if (Controls.JUMP.isDown() && agent.getPlatform() == null && jumpTime > 0) {
            agent.applyDelta(0, agent.getMovement().y / agent.getJumpTime());
            jumpTime--;
        } else {
            jumpTime = 0;
        }

    }
}
