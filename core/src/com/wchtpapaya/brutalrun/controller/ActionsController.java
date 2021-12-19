package com.wchtpapaya.brutalrun.controller;

import com.badlogic.gdx.math.Vector2;
import com.wchtpapaya.brutalrun.action.Action;
import com.wchtpapaya.brutalrun.action.MovingAction;
import com.wchtpapaya.brutalrun.sprite.GameObject;

import java.util.ArrayList;
import java.util.List;

public class ActionsController {

    private final List<Action> actions = new ArrayList<>();

    public void clearGameObjectActions(GameObject object) {
        actions.removeIf(a -> {
            if (a.getObject().equals(object)) {
                a.cancel();
                return true;
            }
            return false;
        });
    }

    public void addAction(Action a) {
        a.start();
        actions.add(a);
    }

    public void perform(float deltaTime) {
        actions.forEach(a -> a.perform(deltaTime));
    }

    public void clearCompleted() {
        actions.removeIf(Action::isCompleted);
    }

    public boolean hasActions(GameObject gameObject) {
        return actions.stream().anyMatch(a -> a.getObject().equals(gameObject));
    }
}
