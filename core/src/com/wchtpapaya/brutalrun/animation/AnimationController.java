package com.wchtpapaya.brutalrun.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class AnimationController {

    private String spriteName;
    private Map<String, Animation<TextureRegion>> animations;

    public AnimationController(String spriteName, Map<String, Animation<TextureRegion>> animations) {
        this.spriteName = spriteName;
        this.animations = animations;
    }
    public AnimationController(String spriteName) {
        this.spriteName = spriteName;
        animations = new HashMap<>();
    }

    public Animation<TextureRegion> getAnimation(String animName) {
        //TODO fix return
        return null;
    }
}
