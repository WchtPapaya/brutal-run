package com.wchtpapaya.brutalrun;

import com.wchtpapaya.brutalrun.sprites.GameObject;

public abstract class Action {

    protected final GameObject object;
    protected boolean completed = false;

    protected Action(GameObject object) {
        this.object = object;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public GameObject getObject() {
        return object;
    }


    public abstract boolean perform(float deltaTime);

    public boolean isCompleted() {
        return completed;
    }
}
