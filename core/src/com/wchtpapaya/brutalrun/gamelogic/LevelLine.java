package com.wchtpapaya.brutalrun.gamelogic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wchtpapaya.brutalrun.ailogic.EntityController;
import com.wchtpapaya.brutalrun.sprite.GameObject;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelLine {
    private Map<String, GameObject> objects = new HashMap<>();
    private List<EntityController> controllers = new ArrayList<>();

    @Getter
    private final float groundLevel;

    public LevelLine(float groundLevel) {
        this.groundLevel = groundLevel;
    }

    public void addGameObject(GameObject object) {
        objects.put(object.getName(), object);
        object.setY(groundLevel);
    }

    public GameObject getGameObject(String name) {
        return objects.get(name);
    }

    public void doStep(float stateTime) {
        controllers.forEach(c -> c.doStep(stateTime));
    }

    public void draw(SpriteBatch batch, float stateTime) {
        objects.forEach((k, v) -> v.draw(batch, stateTime));
    }

    public void dispose() {
        objects.forEach((k, v) -> v.kill());
    }
}
