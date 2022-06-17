package com.wchtpapaya.brutalrun.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class AnimationController {
    private final AnimationContainer animationContainer;
    private Animation<TextureRegion> currentAnimation;
    private Queue<Animation<TextureRegion>> animationQueue = new ArrayDeque<>();

    public AnimationController(AnimationContainer animationContainer) {

        this.animationContainer = animationContainer;
    }

    /**
     * Sets current animation
     */
    public void setCurrentAnimation(String animationName, Animation.PlayMode playMode) {
        currentAnimation = animationContainer.getAnimation(animationName);
        currentAnimation.setPlayMode(playMode);
    }

    public Set<String> getAnimationNames() {
        return animationContainer.getAnimationNames();
    }

    public TextureRegion getFrame(float stateTime) {
        return currentAnimation.getKeyFrame(stateTime);
    }
}
