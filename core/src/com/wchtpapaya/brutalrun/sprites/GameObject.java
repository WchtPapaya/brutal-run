package com.wchtpapaya.brutalrun.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.esotericsoftware.spine.Skeleton;
import com.wchtpapaya.brutalrun.WorldHandler;

public class GameObject {
    private final Sprite sprite;
    private Body box2dBody;

    public GameObject(Sprite sprite) {
        this.sprite = sprite;
    }

    public static GameObject of(Texture img, int height) {
        GameObject object =  new GameObject(new Sprite(img));
        object.setSizeWithHeightAspect(height);
        object.createDynamicBody();
        return object;
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);

    }

    public void setPosition(Vector2 position) {
        sprite.setPosition(position.x, position.y);
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
    }

    public void translate(float dx, float dy) {
        sprite.translate(dx, dy);
    }

    protected void createDynamicBody() {
        //First we create a body definition
        BodyDef bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world
        bodyDef.position.set(this.getPosition().x, this.getPosition().y);

// Create our body in the world using our body definition
        box2dBody = WorldHandler.getWorld().createBody(bodyDef);

// Create a circle shape and set its radius to 6
        PolygonShape box = new PolygonShape();
        box.setAsBox(this.sprite.getWidth(), this.sprite.getHeight());

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        box2dBody.createFixture(fixtureDef);

    }

    public void moveTo(Vector2 destination, int speed) {
        float dx = destination.x - getPosition().x;
        float dy = destination.y - getPosition().y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        Vector2 direction = new Vector2(dx / distance * speed, dy / distance * speed);

        box2dBody.setLinearVelocity(direction);
    }
}
