package com.wchtpapaya.brutalrun.action;

import com.wchtpapaya.brutalrun.sprite.GameObject;

public abstract class Action {

    protected final GameObject object;
    protected boolean completed = false;

    public abstract void start();

    public abstract boolean perform(float deltaTime);

    public abstract void end();

    public abstract void cancel();

    protected Action(GameObject object) {
        this.object = object;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public GameObject getObject() {
        return object;
    }

    public boolean isCompleted() {
        return completed;
    }
}
