package com.wchtpapaya.brutalrun.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class SequenceAnimationLoader implements AnimationLoader {

    // TODO need rework, can`t be used
    private Map<AnimationController, Texture> animations = new HashMap<>();

    @Override
    public AnimationController getAnimations(String objectName) {
        loadHeroAnimations();
        return null;
    }

    private void loadHeroAnimations() {
        Texture sheet = new Texture(Gdx.files.internal("Hero_1/Running.png"));

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        final int FRAME_COLS = 6;
        final int FRAME_ROWS = 4;
        TextureRegion[][] tmp = TextureRegion.split(sheet,
                sheet.getWidth() / FRAME_COLS,
                sheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        float frameDuration = 1 / 24f;
//        animations.put(new AnimationsContainer(spriteName, animations), sheet);
    }
}
