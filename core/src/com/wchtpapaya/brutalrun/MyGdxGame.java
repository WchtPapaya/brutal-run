package com.wchtpapaya.brutalrun;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wchtpapaya.brutalrun.sprites.GameObject;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    private SpriteBatch batch;

    private List<GameObject> sprites = new ArrayList<>();
    private List<Action> actions = new ArrayList<>();
    private GameObject selectedHero;

    private Viewport viewport;
    private OrthographicCamera camera;
    private float rotationSpeed = 0.5f;


    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1440, 900);
        Gdx.input.setInputProcessor(this);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(30, height / width * 30);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
//        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        camera.update();

        batch = new SpriteBatch();
        GameObject sprite = GameObject.of(new Texture("Hero_1.png"));
        sprite.setSizeWithWidthAspect(5);
        sprite.setPosition(new Vector2(0, 0));
        sprites.add(sprite);

        selectedHero = sprite;
    }

    @Override
    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        actions.forEach(a -> a.perform(Gdx.graphics.getDeltaTime()));
        actions.removeIf(Action::isCompleted);

        ScreenUtils.clear(1, 0, 0, 1);
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
        actions.add(new MovingAction(new Vector2(touchPoint.x, touchPoint.y), 3, selectedHero));
        return true;
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
}
