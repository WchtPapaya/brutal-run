package com.wchtpapaya.brutalrun.controller;

import com.wchtpapaya.brutalrun.action.Action;
import com.wchtpapaya.brutalrun.action.DelayedAction;
import com.wchtpapaya.brutalrun.sprite.GameObject;

import static com.wchtpapaya.brutalrun.Utils.distance;

public class AttackingController {
    public Action attackEnemy(GameObject attacker) {
        GameObject enemyToAttack = attacker.getEnemyToAttack();
        if (enemyToAttack == null) return null;
        if (attacker.isWeaponOnDelay()) return null;

        final float distance = (float) distance(attacker.getX(), attacker.getY(), enemyToAttack.getX(), enemyToAttack.getY());
        if (distance > attacker.getAttackRadius()) return null;
        enemyToAttack.decreaseHealth(attacker.getWeaponDamage());
        attacker.setWeaponOnDelay(true);

        return new DelayedAction(attacker);
    }
}
