package com.wchtpapaya.brutalrun.ailogic.task;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.wchtpapaya.brutalrun.animation.AnimationController;
import com.wchtpapaya.brutalrun.sprite.GameObject;

public class MoveToPointTask implements EntityTask {
    public static final String START_MOVING_NAME = "StartRunning";
    private static final float MOVE_ERROR = 0.5f;

    private float destinationX;

    private GameObject object;
    private float speed;

    public MoveToPointTask(float destinationX, GameObject object, float speed) {
        this.destinationX = destinationX;
        this.object = object;
        this.speed = speed;
    }

    @Override
    public void start() {
        AnimationController controller = object.getAnimationController();
        controller.setCurrentAnimation(START_MOVING_NAME, Animation.PlayMode.NORMAL);
        controller.
    }

    @Override
    public boolean doStep(float stateTime) {
        if (isCompleted()) return true;

        object.move(speed);
        return isCompleted();
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean isCompleted() {
        float x = object.getX();
        return destinationX - MOVE_ERROR <= x && x <= destinationX + MOVE_ERROR;
    }
}
