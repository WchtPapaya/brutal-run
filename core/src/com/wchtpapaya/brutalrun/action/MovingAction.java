package com.wchtpapaya.brutalrun.action;

import com.badlogic.gdx.math.Vector2;
import com.wchtpapaya.brutalrun.sprite.GameObject;

public class MovingAction extends Action {
    private final Vector2 direction;
    private final float time;
    private float currentTime;

    public MovingAction(Vector2 destination, GameObject object) {
        super(object);
        float speed = object.getSpeed();
        float dx = destination.x - object.getPosition().x;
        float dy = destination.y - object.getPosition().y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        direction = new Vector2(dx / distance * speed, dy / distance * speed);

        time = distance / speed;
    }

    @Override
    public void start() {
        object.flipLeft(direction.x < 0);
    }

    @Override
    public boolean perform(float deltaTime) {
        if (completed) return true;

        currentTime += deltaTime;
        object.translate(direction.x * deltaTime, direction.y * deltaTime);
        if (currentTime >= time) {
            completed = true;
            return true;
        }
        return false;
    }

    @Override
    public void end() {
    }

    @Override
    public void cancel() {
        completed = true;
    }

}
