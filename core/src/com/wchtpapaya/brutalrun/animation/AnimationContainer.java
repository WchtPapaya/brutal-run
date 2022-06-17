package com.wchtpapaya.brutalrun.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Set;

public interface AnimationContainer {
    Animation<TextureRegion> getAnimation(String animName);

    void addAnimation(String name, Animation<TextureRegion> a);

    int getAnimationNumber();

    /**
     *
     * @return new entity of a controller to manage animations at a sprite
     */
    AnimationController getController();

    Set<String> getAnimationNames();

    String getSpriteName();
}
