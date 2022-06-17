package com.wchtpapaya.brutalrun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wchtpapaya.brutalrun.animation.AnimationContainer;
import com.wchtpapaya.brutalrun.animation.AnimationController;
import com.wchtpapaya.brutalrun.animation.AnimationLoader;
import com.wchtpapaya.brutalrun.animation.BatchAnimationLoader;
import com.wchtpapaya.brutalrun.gamelogic.LevelLine;
import com.wchtpapaya.brutalrun.input.DesktopInputProcessor;
import com.wchtpapaya.brutalrun.sprite.GameObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MyGdxGame extends ApplicationAdapter {
    public static final int WORLD_WIDTH = 60;

    private List<LevelLine> levelLines = new ArrayList<>();

    private SpriteBatch batch;

    private Viewport viewport;
    @Getter
    private OrthographicCamera camera;

    private float stateTime;
    private AnimationContainer animationContainer;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1440, 900);

        // Input
        Gdx.input.setInputProcessor(new DesktopInputProcessor(this));

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(WORLD_WIDTH, height / width * WORLD_WIDTH);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
//        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        camera.update();

        batch = new SpriteBatch();

        stateTime = 0f;

        AnimationLoader animationLoader = new BatchAnimationLoader();
        log.info(Gdx.files.getLocalStoragePath());
        log.info(Gdx.files.getExternalStoragePath());


        // Animation loading
        Texture heroSheet = new Texture(Gdx.files.local("core/assets/Hero/PPSH/Hero_2_tex.png"));
        animationContainer = animationLoader.loadAnimations(heroSheet, "core/assets/Hero/PPSH/Hero_2_tex.json");
        log.info("Loaded sprite sheet - {}", animationContainer.getSpriteName());
        animationContainer.getAnimationNames().forEach(s -> log.info("Sheet has animation - {}", s));
        // Sprites
        AnimationController heroController = animationContainer.getController();
        heroController.setCurrentAnimation("Fire", Animation.PlayMode.LOOP);
        GameObject soldier1 =  GameObject.of("Soldier1", GameObject.Type.Hero, heroController, 10, 1);

        LevelLine line = new LevelLine(0);
        line.addGameObject(soldier1);
        levelLines.add(line);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stateTime += deltaTime;

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        levelLines.forEach(line -> line.doStep(stateTime));

        ScreenUtils.clear(0.6f, 0.5f, 0.5f, 1);
        batch.begin();
        levelLines.forEach(line -> line.draw(batch, stateTime));
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        levelLines.forEach(LevelLine::dispose);
    }

    @Override
    public void resize(int width, int height) {
//        viewport.update(width, height);
    }

}
