package com.wchtpapaya.brutalrun;

import com.badlogic.gdx.math.Vector2;
import com.wchtpapaya.brutalrun.sprites.GameObject;

public class MovingAction extends Action {
    private final GameObject object;
    private final Vector2 direction;
    private final float time;
    private float currentTime;

    /**
     * @param speed speed in world units per second
     */
    public MovingAction(Vector2 destination, float speed, GameObject object) {
        this.object = object;

        float dx = destination.x - object.getPosition().x;
        float dy = destination.y - object.getPosition().y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        direction = new Vector2(dx / distance * speed, dy / distance * speed);

        time = distance / speed;
    }

    @Override
    public boolean perform(float deltaTime) {
        if (completed) return true;

        object.translate(direction.x * deltaTime, direction.y * deltaTime);
        currentTime += deltaTime;
        if (currentTime >= time) {
            completed = true;
            return true;
        }
        return false;
    }

}
