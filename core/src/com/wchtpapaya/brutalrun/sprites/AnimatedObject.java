package com.wchtpapaya.brutalrun.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.esotericsoftware.spine.Skeleton;

public class AnimatedObject extends GameObject{

    private final Skeleton skeleton;

    public AnimatedObject(Sprite sprite, Body box2dBody, Skeleton skeleton) {
        super(sprite, box2dBody);
        this.skeleton = skeleton;
    }
}
