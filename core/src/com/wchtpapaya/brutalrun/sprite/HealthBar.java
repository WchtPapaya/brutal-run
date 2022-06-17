package com.wchtpapaya.brutalrun.sprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthBar {
    public static final float HEALTH_BAR_Y_POS = 10.0f / 9;

    private static final int HEALTH_BAR_SIZES = 10;
    private static final float HEALTH_BAR_HEIGHT = 0.5f;

    final private Texture texture;
    /**
     * coefficient, needed for resizing a health bar width
     * to the actual width of sprite texture
     */
    private float healthBarWidth;

    public HealthBar(float healthBarWidth) {
        Pixmap pixmap = new Pixmap(HEALTH_BAR_SIZES, HEALTH_BAR_SIZES, Pixmap.Format.RGB888);
        pixmap.setColor(Color.GREEN);
        pixmap.fillRectangle(0, 0, HEALTH_BAR_SIZES, HEALTH_BAR_SIZES);
        texture = new Texture(pixmap);
        this.healthBarWidth = healthBarWidth;
    }

    public void dispose() {
        texture.dispose();
    }

    public void draw(SpriteBatch batch, float x, float y, float width) {
        batch.draw(texture, x, y,
                healthBarWidth * width,
                HEALTH_BAR_HEIGHT);
    }
}
