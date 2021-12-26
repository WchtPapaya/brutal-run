package com.wchtpapaya.brutalrun.controller;

import com.wchtpapaya.brutalrun.action.Action;
import com.wchtpapaya.brutalrun.action.DelayedAction;
import com.wchtpapaya.brutalrun.sprite.GameObject;

import static com.wchtpapaya.brutalrun.Utils.distance;

public class AttackingController {
    public Action attackTarget(GameObject attacker) {
        GameObject targetToAttack = attacker.getTargetToAttack();

        if (!canAttack(attacker, targetToAttack)) return null;

        targetToAttack.decreaseHealth(attacker.getWeaponDamage());
        attacker.setWeaponOnDelay(true);

        if (!targetToAttack.isAlive()) {
            attacker.setTargetToAttack(null);
        }

        return new DelayedAction(attacker, attacker.getWeaponDelay(),
                () -> attacker.setWeaponOnDelay(false),
                () -> {
                    throw new RuntimeException("Delayed action canceled");
                });
    }

    public boolean canAttack(GameObject attacker, GameObject targetToAttack) {
        if (targetToAttack == null || attacker.isWeaponOnDelay()) return false;

        final float distance = (float) distance(attacker, targetToAttack);
        return distance <= attacker.getAttackRadius();
    }
}
