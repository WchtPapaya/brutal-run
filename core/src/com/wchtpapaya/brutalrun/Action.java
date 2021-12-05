package com.wchtpapaya.brutalrun;

public abstract class Action {

    protected boolean completed = false;

    public abstract boolean perform(float deltaTime);

    public boolean isCompleted() {
        return completed;
    }
}
