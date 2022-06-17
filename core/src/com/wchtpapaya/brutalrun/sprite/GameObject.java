package com.wchtpapaya.brutalrun.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wchtpapaya.brutalrun.animation.AnimationController;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GameObject {

    public enum Type {
        Hero,
        Enemy
    }

    @Getter
    private final String name;
    /**
     * x coordinate of an object center position
     */
    @Setter
    private float x;
    /**
     * y coordinate of an object center position
     */
    @Setter
    private float y;
    private boolean flipLeft;
    /**
     * height at world coordinates
     */
    private float height;
    /**
     * width at world coordinates
     */
    private float width;
    private boolean scaledToHeight;
    private final AnimationController animationController;

    private boolean alive = true;
    @Setter
    private Type type;
    @Setter
    private float maxHealth;
    @Setter
    private float health;
    /**
     * speed in world units per second
     */
    @Setter
    private float speed;

    private HealthBar healthBar;

    public static GameObject of(String name, Type type, AnimationController controller, float height, float speed) {
        GameObject object = new GameObject(name, controller);
        object.setType(type);
        object.setWorldHeight(height);
        object.setHealth(100.0f);
        object.setMaxHealth(100.0f);
        object.createHealthBar();
        object.setSpeed(speed);
        return object;
    }

    public GameObject(String name, AnimationController controller) {
        this.name = name;
        animationController = controller;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setWorldHeight(float height) {
        scaledToHeight = true;
        this.height = height;
    }

    public void flipLeft(boolean left) {
        flipLeft = left;
    }

    public void kill() {
        alive = false;
        dispose();
    }

    public void draw(SpriteBatch batch, float stateTime) {

        TextureRegion currentFrame = animationController.getFrame(stateTime);

        final int regionWidth = currentFrame.getRegionWidth();
        final int regionHeight = currentFrame.getRegionHeight();

        final float widthAtWorld = height / regionHeight * regionWidth;

        if (flipLeft != currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }
        batch.draw(currentFrame, x, y, widthAtWorld, height);
        healthBar.draw(batch,
                this.getX() + widthAtWorld / 2,
                this.getY() + HealthBar.HEALTH_BAR_Y_POS * height,
                getHealth() / maxHealth * widthAtWorld);
    }

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }

    public void decreaseHealth(float damage) {
        health -= damage;
        if (health <= 0.0f) {
            kill();
        }
    }

    public float move(float speed) {
        return x += speed;
    }

    private void dispose() {
        healthBar.dispose();
    }

    protected void createHealthBar() {
        healthBar = new HealthBar(0.3f);
    }
}
