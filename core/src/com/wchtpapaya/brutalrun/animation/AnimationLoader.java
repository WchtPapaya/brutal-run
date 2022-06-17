package com.wchtpapaya.brutalrun.animation;

import com.badlogic.gdx.graphics.Texture;

public interface AnimationLoader {
    AnimationContainer loadAnimations(Texture sheet, String jsonFilePath);
}
