package com.wchtpapaya.brutalrun.controller;

import com.badlogic.gdx.math.Vector2;
import com.wchtpapaya.brutalrun.action.Action;
import com.wchtpapaya.brutalrun.action.MovingAction;
import com.wchtpapaya.brutalrun.sprite.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActionsController {

    private final List<Action> actions = new ArrayList<>();

    private AttackingController attackingController;
    private EnemyController enemyController;

    public ActionsController(AttackingController attackingController, EnemyController enemyController) {
        this.attackingController = attackingController;
        this.enemyController = enemyController;
    }

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

    public void checkAttacks(List<GameObject> sprites) {
        sprites.stream().filter(s -> !hasActions(s))
                .map(attackingController::attackEnemy)
                .filter(Objects::nonNull)
                .forEach(this::addAction);
    }

    public void moveToHeroes(List<GameObject> sprites) {
        sprites.stream().filter(s -> s.getType().equals(GameObject.Type.Enemy))
                .filter(s -> !attackingController.canAttack(s))
                .forEach(s -> {
                    clearGameObjectActions(s);
                    Vector2 destination = s.getEnemyToAttack().getPosition();
                    float halfRadius = s.getAttackRadius() / 2;
                    destination.add(-halfRadius, 0);
                    addAction(new MovingAction(destination, s));
                });
    }
}
