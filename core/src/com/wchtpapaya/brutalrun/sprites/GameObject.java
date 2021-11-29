package com.wchtpapaya.brutalrun.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.esotericsoftware.spine.Skeleton;

public class GameObject {
    private final Sprite sprite;
    private final Body box2dBody;

    public GameObject(Sprite sprite, Body box2dBody) {
        this.sprite = sprite;
        this.box2dBody = box2dBody;
    }

    public static GameObject of(Texture img) {
    }


    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
