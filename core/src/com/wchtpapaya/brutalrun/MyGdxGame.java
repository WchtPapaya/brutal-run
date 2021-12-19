package com.wchtpapaya.brutalrun;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wchtpapaya.brutalrun.action.MovingAction;
import com.wchtpapaya.brutalrun.controller.ActionsController;
import com.wchtpapaya.brutalrun.controller.AttackingController;
import com.wchtpapaya.brutalrun.controller.EnemyController;
import com.wchtpapaya.brutalrun.sprite.GameObject;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    public static final int WORLD_WIDTH = 30;

    private ActionsController actionsController;

    private SpriteBatch batch;

    private List<GameObject> sprites = new ArrayList<>();

    private GameObject selectedHero;

    private Viewport viewport;
    private OrthographicCamera camera;

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

        batch = new SpriteBatch();

        actionsController = new ActionsController(new AttackingController(), new EnemyController());

        this.createHero();
        GameObject enemy = this.createEnemy();

        selectedHero.setEnemyToAttack(enemy);
        selectedHero.setAttackRadius(5);
        selectedHero.setWeaponDamage(10);
        selectedHero.setWeaponDelay(1.0f);
    }

    private GameObject createEnemy() {
        GameObject sprite = GameObject.of(new Texture("Enemy_1.png"), GameObject.Type.Enemy, 3, 3);
        sprite.setPosition(new Vector2(WORLD_WIDTH * 0.7f, 0));
        sprite.setEnemyToAttack(selectedHero);
        sprite.setAttackRadius(5);
        sprite.setWeaponDamage(15);
        sprite.setWeaponDelay(2.0f);
        sprites.add(sprite);

        return sprite;
    }

    private void createHero() {
        GameObject sprite = GameObject.of(new Texture("Hero_1.png"), GameObject.Type.Hero, 5, 6);
        sprite.setPosition(new Vector2(0, 0));
        sprites.add(sprite);
        selectedHero = sprite;
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        actionsController.moveToHeroes(sprites);
        actionsController.checkAttacks(sprites);
        actionsController.perform(deltaTime);
        actionsController.clearCompleted();

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
        actionsController.clearGameObjectActions(selectedHero);
        actionsController.addAction(new MovingAction(new Vector2(touchPoint.x, touchPoint.y), selectedHero));
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
