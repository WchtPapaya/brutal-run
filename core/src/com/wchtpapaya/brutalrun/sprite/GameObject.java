package com.wchtpapaya.brutalrun.sprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.wchtpapaya.brutalrun.WorldHandler;

public class GameObject {

    public enum Type {
        HERO(0x0002, 0x0004),
        ENEMY(0x0004, 0x0002),
        HERO_SENSOR(0x0008, 0x0002 + 0x0004);

        private int category;
        private int collisionMask;

        Type(int category, int collisionMask) {
            this.category = category;
            this.collisionMask = collisionMask;
        }

        public int getCategory() {
            return category;
        }

        public int getCollisionMask() {
            return collisionMask;
        }
    }

    public static final float HEALTH_POS = 10.0f / 9;
    private final Sprite sprite;
    private Body box2dBody;

    private Texture healthBar;
    private float maxHealth;
    private float health;

    private GameObject enemyToAttack;
    private float attackRadius;
    private float weaponDamage;
    private boolean weaponOnDelay;
    private float weaponDelay;

    public GameObject(Sprite sprite) {
        this.sprite = sprite;
    }

    public GameObject getEnemyToAttack() {
        return enemyToAttack;
    }

    public void setEnemyToAttack(GameObject enemyToAttack) {
        this.enemyToAttack = enemyToAttack;
    }

    public float getAttackRadius() {
        return attackRadius;
    }

    public void setAttackRadius(float attackRadius) {
        this.attackRadius = attackRadius;
    }

    public void decreaseHealth(float damage) {
        health -= damage;
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

    public static GameObject of(Texture img, float height, Type type) {
        GameObject object = new GameObject(new Sprite(img));
        object.setSizeWithWidthAspect(height);
        object.createDynamicBody(type);
        object.setHealth(100.0f);
        object.setMaxHealth(100.0f);
        object.createHealthBar();
        return object;
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
        box2dBody.setTransform(position, box2dBody.getAngle());
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
        box2dBody.getFixtureList().forEach(f -> f.getShape().dispose());
        healthBar.dispose();
    }

    public void translate(float dx, float dy) {
        sprite.translate(dx, dy);
        Vector2 vec = box2dBody.getPosition();
        vec.add(dx, dy);
        box2dBody.setTransform(vec, box2dBody.getAngle());
    }

    protected void createDynamicBody(Type type) {
//First we create a body definition
        BodyDef bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world

        float halfWidth = this.sprite.getWidth() / 2;
        float halfHeight = this.sprite.getHeight() / 2;

        bodyDef.position.set(this.getPosition().x + halfWidth, this.getPosition().y + halfHeight);

// Create our body in the world using our body definition
        box2dBody = WorldHandler.getWorld().createBody(bodyDef);

// Create a circle shape and set its radius to 6
        PolygonShape box = new PolygonShape();
        box.setAsBox(halfWidth, halfHeight);
// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit
        Filter filter = fixtureDef.filter;
        filter.categoryBits = (short) type.getCategory();
        filter.maskBits = (short) type.getCollisionMask();
        box2dBody.createFixture(fixtureDef);
        box2dBody.setUserData(this);
    }

    public void setVelocity(Vector2 direction) {
        box2dBody.setLinearVelocity(direction);
    }

    public void clearVelocity() {
        box2dBody.setLinearVelocity(0.0f, 0.0f);
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
}
