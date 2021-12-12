package com.wchtpapaya.brutalrun;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wchtpapaya.brutalrun.sprites.GameObject;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    private static final float TIME_STEP = 0.01f;
    private static final int VELOCITY_ITERATIONS = 4;
    private static final int POSITION_ITERATIONS = 4;
    public static final int WORLD_WIDTH = 30;

    private SpriteBatch batch;

    private List<GameObject> sprites = new ArrayList<>();
    private List<Action> actions = new ArrayList<>();
    private GameObject selectedHero;

    private Viewport viewport;
    private OrthographicCamera camera;
    private float rotationSpeed = 0.5f;

    private Box2DDebugRenderer debugRenderer;
    private World world;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1440, 900);
        Gdx.input.setInputProcessor(this);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(WORLD_WIDTH, height / width * WORLD_WIDTH);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
//        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        camera.update();

        world = WorldHandler.getWorld();
        debugRenderer = new Box2DDebugRenderer();

        batch = new SpriteBatch();

        this.createHero();
        GameObject enemy = this.createEnemy();

        selectedHero.setEnemyToAttack(enemy);
        selectedHero.setAttackRadius(5);
        selectedHero.setWeaponDamage(10);
    }

    private GameObject createEnemy() {
        GameObject sprite = GameObject.of(new Texture("Enemy_1.png"), 3, GameObject.Type.ENEMY);
        sprite.setPosition(new Vector2(WORLD_WIDTH * 0.7f, 0));
        sprites.add(sprite);
        return sprite;
    }

    private void createHero() {
        GameObject sprite = GameObject.of(new Texture("Hero_1.png"), 5, GameObject.Type.HERO);
        sprite.setPosition(new Vector2(0, 0));
        sprites.add(sprite);
        selectedHero = sprite;
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        actions.forEach(a -> a.perform(deltaTime));
        actions.removeIf(Action::isCompleted);
        doPhysicsStep(deltaTime);
        sprites.forEach(GameObject::attackEnemy);
        ScreenUtils.clear(1, 0, 0, 1);
        debugRenderer.render(world, camera.combined);
        batch.begin();
        sprites.forEach(s -> s.draw(batch));
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        sprites.forEach(GameObject::dispose);
    }

    @Override
    public void resize(int width, int height) {
//        viewport.update(width, height);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        Vector3 touchPoint = camera.unproject(new Vector3(screenX, screenY, 0));
        Gdx.app.log("Debug", String.format("Mouse touch at x: %f, y: %f", touchPoint.x, touchPoint.y));
        addMovingAction(new Vector2(touchPoint.x, touchPoint.y), selectedHero);
        return true;
    }

    private void addMovingAction(Vector2 destination, GameObject object) {
        actions.removeIf(a -> a.getObject().equals(object));
        addAction(destination, object);
    }

    private void addAction(Vector2 destination, GameObject object) {
        Action action = new MovingAction(destination, 3, object);
        action.start();
        actions.add(action);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    private float accumulator = 0;

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            accumulator -= TIME_STEP;
        }
        ;
    }
}
