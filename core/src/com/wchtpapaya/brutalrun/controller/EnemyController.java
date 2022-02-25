package com.wchtpapaya.brutalrun.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.wchtpapaya.brutalrun.MyGdxGame;
import com.wchtpapaya.brutalrun.sprite.GameObject;

import java.util.List;

public class EnemyController {


    public boolean checkEnemySpawn(List<GameObject> sprites) {
        return sprites.stream().noneMatch(s -> s.getType().equals(GameObject.Type.Enemy));
    }

    public void spawnEnemies(List<GameObject> sprites) {
        sprites.add(createEnemy());
    }

    private GameObject createEnemy() {
        // TODO Restore Enemy
//        GameObject sprite = GameObject.of(new Texture("Enemy_1.png"), GameObject.Type.Enemy, 3, 3);
//        sprite.setPosition(new Vector2(MyGdxGame.WORLD_WIDTH * MathUtils.random(), 0));
//        sprite.setAttackRadius(5);
//        sprite.setWeaponDamage(15);
//        sprite.setWeaponDelay(2.0f);
//        return sprite;
        return null;
    }

}
