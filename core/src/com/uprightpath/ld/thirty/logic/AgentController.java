package com.uprightpath.ld.thirty.logic;

/**
 * Created by Geo on 8/22/2014.
 */
public abstract class AgentController<T extends Agent> {
    protected T agent;

    public AgentController(T agent) {
        this.agent = agent;
    }

    public abstract void updateAgentLogic();
}
