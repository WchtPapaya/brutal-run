package com.wchtpapaya.brutalrun.ailogic.task;

/**
 * Represents a state of any entity (GameObject)
 */
public interface EntityTask {
    void start();
    boolean doStep(float stateTime);
    void reset();

    boolean isCompleted();
}
