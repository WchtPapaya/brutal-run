package com.wchtpapaya.brutalrun.animation;

import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class BatchAnimationLoaderTest {


    @Test
    public void loadTest() {
        assertTrue(true);
    }

    @Test
    public void testLoadAnimationJson() {
        BatchAnimationLoader loader = new BatchAnimationLoader();

        Texture sheet = mock(Texture.class);
        AnimationContainer controller = loader.loadAnimations(sheet, "assets/Hero/Pistol/Hero_1_tex.json");
        assertTrue(controller.getAnimationNumber() > 0);
    }
}
