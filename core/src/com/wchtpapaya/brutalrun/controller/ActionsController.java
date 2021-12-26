package com.wchtpapaya.brutalrun.controller;

import com.badlogic.gdx.math.Vector2;
import com.wchtpapaya.brutalrun.Utils;
import com.wchtpapaya.brutalrun.action.Action;
import com.wchtpapaya.brutalrun.action.MovingAction;
import com.wchtpapaya.brutalrun.sprite.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ActionsController {

    private final List<Action> actions = new ArrayList<>();

    private AttackingController attackingController;

    public ActionsController(AttackingController attackingController) {
        this.attackingController = attackingController;
    }

    public void clearAllActions(GameObject object) {
        actions.removeIf(a -> {
            if (a.getObject().equals(object)) {
                a.cancel();
                return true;
            }
            return false;
        });
    }

    public void clearMovingActions(GameObject object) {
        actions.removeIf(a -> {
            if (a.getObject().equals(object) && a instanceof MovingAction) {
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

    public void performAttacks(List<GameObject> sprites) {
        sprites.stream().filter(s -> !hasActions(s))
                .map(attackingController::attackTarget)
                .filter(Objects::nonNull)
                .forEach(this::addAction);
    }

    public void moveToTarget(List<GameObject> sprites, GameObject.Type attackerType) {
        sprites.stream().filter(s -> s.getType().equals(attackerType))
                .filter(s -> !attackingController.isAtAttackingRange(s, s.getTargetToAttack()))
                .forEach(s -> {
                    final GameObject targetToAttack = s.getTargetToAttack();
                    if (targetToAttack == null) return;
                    clearMovingActions(s);
                    Vector2 destination = Utils.pointAtLine(s.getX(), s.getY(),
                            targetToAttack.getX(), targetToAttack.getY(),
                            s.getWeaponRadius());
                    addAction(new MovingAction(destination, s));
                });
    }

    public void setTargets(List<GameObject> sprites, GameObject.Type attackerType, GameObject.Type targetType) {
        List<GameObject> targets = sprites.stream().filter(s -> s.getType().equals(targetType))
                .collect(Collectors.toList());
        if (targets.isEmpty()) return;

        sprites.stream().filter(s -> s.getType().equals(attackerType))
                .filter(a -> a.getTargetToAttack() == null)
                .forEach(attacker -> {
                    float min = (float) Utils.distance(attacker, targets.get(0));
                    GameObject nearest = targets.get(0);
                    for (GameObject target : targets) {
                        float distance = (float) Utils.distance(attacker, target);
                        if (distance < min) {
                            min = distance;
                            nearest = target;
                        }
                    }
                    attacker.setTargetToAttack(nearest);
                });
    }

    public void removeActions(GameObject object) {
        actions.removeIf(a -> a.getObject().equals(object));
    }
}
