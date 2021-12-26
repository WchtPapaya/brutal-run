package com.wchtpapaya.brutalrun.sprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameObject {

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isAlive() {
        return alive;
    }

    public enum Type {
        Hero,
        Enemy
    }

    public static final float HEALTH_POS = 10.0f / 9;

    private boolean alive = true;

    private final Sprite sprite;
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

    public GameObject(Sprite sprite) {
        this.sprite = sprite;
    }

    public GameObject getTargetToAttack() {
        return targetToAttack;
    }

    public void setTargetToAttack(GameObject targetToAttack) {
        this.targetToAttack = targetToAttack;
    }

    public float getAttackRadius() {
        return attackRadius;
    }

    public void setAttackRadius(float attackRadius) {
        this.attackRadius = attackRadius;
    }

    public void decreaseHealth(float damage) {
        health -= damage;
        if (health <= 0.0f) {
            kill();
        }
    }

    public boolean isWeaponOnDelay() {
        return weaponOnDelay;
    }

    public void setWeaponOnDelay(boolean weaponOnDelay) {
        this.weaponOnDelay = weaponOnDelay;
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
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

    public static GameObject of(Texture img, Type type, float height, float speed) {
        GameObject object = new GameObject(new Sprite(img));
        object.setSizeWithWidthAspect(height);
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

    protected void createHealthBar() {
        final int HEALTH_BAR_SIZES = 10;
        Pixmap pixmap = new Pixmap(HEALTH_BAR_SIZES, HEALTH_BAR_SIZES, Pixmap.Format.RGB888);
        pixmap.setColor(Color.GREEN);
        pixmap.fillRectangle(0, 0, HEALTH_BAR_SIZES, HEALTH_BAR_SIZES);
        healthBar = new Texture(pixmap);

    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
        batch.draw(healthBar,
                sprite.getX(),
                sprite.getY() + HEALTH_POS * sprite.getHeight(),
                getHealth() / maxHealth * sprite.getWidth(),
                0.5f);
    }


    public void setPosition(Vector2 position) {
        sprite.setPosition(position.x, position.y);
        position.add(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public void setSize(float width, float height) {
        sprite.setSize(width, height);
    }

    public void setSizeWithHeightAspect(float width) {
        sprite.setSize(width, width / sprite.getWidth() * sprite.getHeight());
    }

    public void setSizeWithWidthAspect(float height) {
        sprite.setSize(height / sprite.getHeight() * sprite.getWidth(), height);
    }

    public void dispose() {
        sprite.getTexture().dispose();
        healthBar.dispose();
    }

    public void translate(float dx, float dy) {
        sprite.translate(dx, dy);
    }

    public void flipLeft(boolean left) {
        sprite.setFlip(left, sprite.isFlipY());
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
}
