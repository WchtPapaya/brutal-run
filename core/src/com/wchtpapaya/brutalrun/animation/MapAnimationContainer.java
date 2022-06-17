package com.wchtpapaya.brutalrun.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapAnimationContainer implements AnimationContainer {

    @Getter
    private final String spriteName;
    private final Map<String, Animation<TextureRegion>> animations;

    public MapAnimationContainer(String spriteName) {
        this.spriteName = spriteName;
        animations = new HashMap<>();
    }

    /**
     * @return null if animation isn`t found
     */
    @Override
    public Animation<TextureRegion> getAnimation(String animName) {
        return animations.get(animName);
    }

    @Override
    public void addAnimation(String name, Animation<TextureRegion> a) {
        animations.put(name, a);
    }

    @Override
    public int getAnimationNumber() {
        return animations.size();
    }

    @Override
    public AnimationController getController() {
        return new AnimationController(this);
    }

    @Override
    public Set<String> getAnimationNames() {
        return animations.keySet();
    }

    private int getMaxFrameWidth() {
        int max = animations.get("Dying").getKeyFrames()[0].getRegionWidth();
        for (Map.Entry<String, Animation<TextureRegion>> entry : animations.entrySet()) {
            Animation<TextureRegion> animation = entry.getValue();
            int width = Arrays.stream(animation.getKeyFrames()).map(TextureRegion::getRegionWidth).max(Integer::compare).get();
            max = Math.max(max, width);
        }
        return max;
    }

    private int getMaxFrameHeight() {
        int max = animations.get("Dying").getKeyFrames()[0].getRegionHeight();
        for (Map.Entry<String, Animation<TextureRegion>> entry : animations.entrySet()) {
            Animation<TextureRegion> animation = entry.getValue();
            int height = Arrays.stream(animation.getKeyFrames()).map(TextureRegion::getRegionHeight).max(Integer::compare).get();
            max = Math.max(max, height);
        }
        return max;
    }
}
