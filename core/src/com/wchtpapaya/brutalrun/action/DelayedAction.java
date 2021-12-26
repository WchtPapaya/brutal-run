package com.wchtpapaya.brutalrun.action;

import com.wchtpapaya.brutalrun.sprite.GameObject;

public class DelayedAction extends Action {
    private final float delay;
    private final Runnable performAction;
    private final Runnable cancelAction;

    private float currentTime;

    public DelayedAction(GameObject object, float delay, Runnable perform, Runnable cancel) {
        super(object);
        this.delay = delay;
        performAction = perform;
        cancelAction = cancel;
    }

    @Override
    public void start() {
    }

    @Override
    public boolean perform(float deltaTime) {
        currentTime += deltaTime;
        if (currentTime < delay) return false;
        performAction.run();
        this.setCompleted(true);
        return true;
    }

    @Override
    public void end() {
    }

    @Override
    public void cancel() {
        cancelAction.run();
        this.setCompleted(true);
    }
}
