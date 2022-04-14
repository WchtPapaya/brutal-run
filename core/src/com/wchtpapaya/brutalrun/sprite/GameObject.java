package com.wchtpapaya.brutalrun.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameObject {

    private float positionX;
    private float positionY;

    private boolean flipLeft;

    private Animation<TextureRegion> currentAnimation;
    private float height;
    private float width;
    private boolean scaledToHeight;

    public enum Type {
        Hero,
        Enemy
    }

    public static final float HEALTH_POS = 10.0f / 9;

    private boolean alive = true;

    Texture sheet;

    private Type type;
    private Texture healthBar;
    private float maxHealth;
    private float health;

    private GameObject targetToAttack;
    private float attackRadius;
    private float weaponDamage;
    private boolean weaponOnDelay;
    private float weaponDelay;

    private float speed;

    public GameObject() {

    }

    public void decreaseHealth(float damage) {
        health -= damage;
        if (health <= 0.0f) {
            kill();
        }
    }

    public static GameObject of(Type type, float height, float speed) {
        GameObject object = new GameObject();
        object.setWorldHeight(height);
        object.setType(type);
        object.setHealth(100.0f);
        object.setMaxHealth(100.0f);
        object.createHealthBar();
        object.setSpeed(speed);
        return object;
    }

    public void kill() {
        alive = false;
        targetToAttack = null;
        dispose();
    }

    public void draw(SpriteBatch batch, float stateTime) {

        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);

        final int regionWidth = currentFrame.getRegionWidth();
        final int regionHeight = currentFrame.getRegionHeight();

        final float widthAtWorld = height / regionHeight * regionWidth;

        if (flipLeft != currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }
        batch.draw(currentFrame, positionX, positionY, widthAtWorld, height);

        batch.draw(healthBar,
                getX(),
                getY() + HEALTH_POS * height,
                getHealth() / maxHealth * widthAtWorld,
                0.5f);
    }

    public void translate(float dx, float dy) {
        this.positionX += dx;
        this.positionY += dy;
    }

    public void dispose() {
        sheet.dispose();
        healthBar.dispose();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public GameObject getTargetToAttack() {
        return targetToAttack;
    }

    public void setTargetToAttack(GameObject targetToAttack) {
        this.targetToAttack = targetToAttack;
    }

    public float getWeaponRadius() {
        return attackRadius;
    }

    public void setAttackRadius(float attackRadius) {
        this.attackRadius = attackRadius;
    }

    public void setWorldHeight(float height) {
        scaledToHeight = true;
        this.height = height;
    }

    public void flipLeft(boolean left) {
        flipLeft = left;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getSpeed() {
        return speed;
    }

    /**
     * @param speed speed in world units per second
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isWeaponOnDelay() {
        return weaponOnDelay;
    }

    public void setWeaponOnDelay(boolean weaponOnDelay) {
        this.weaponOnDelay = weaponOnDelay;
    }

    public float getX() {
        return positionX;
    }

    public float getY() {
        return positionY;
    }

    public float getWeaponDamage() {
        return weaponDamage;
    }

    public void setWeaponDamage(float weaponDamage) {
        this.weaponDamage = weaponDamage;
    }

    public float getWeaponDelay() {
        return weaponDelay;
    }

    public void setWeaponDelay(float seconds) {
        this.weaponDelay = seconds;
    }

    protected void createHealthBar() {
        final int HEALTH_BAR_SIZES = 10;
        Pixmap pixmap = new Pixmap(HEALTH_BAR_SIZES, HEALTH_BAR_SIZES, Pixmap.Format.RGB888);
        pixmap.setColor(Color.GREEN);
        pixmap.fillRectangle(0, 0, HEALTH_BAR_SIZES, HEALTH_BAR_SIZES);
        healthBar = new Texture(pixmap);

    }
}
