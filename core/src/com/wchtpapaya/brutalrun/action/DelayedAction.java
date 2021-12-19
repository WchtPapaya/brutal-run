package com.wchtpapaya.brutalrun.action;


import com.wchtpapaya.brutalrun.sprite.GameObject;

public class DelayedAction extends Action {

    private final float delay;
    private float currentTime;

    public DelayedAction(GameObject object) {
        super(object);
        delay = object.getWeaponDelay();
    }

    @Override
    public void start() {
    }

    @Override
    public boolean perform(float deltaTime) {
        currentTime += deltaTime;
        if (currentTime < delay) return false;

        object.setWeaponOnDelay(false);
        this.setCompleted(true);
        return true;
    }
}
