package com.wchtpapaya.brutalrun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.wchtpapaya.brutalrun.sprites.GameObject;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    List<GameObject> sprites = new ArrayList<>();
    Texture img;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("Hero_1.png");
        GameObject sprite = GameObject.of(img);

        sprite.setPosition(new Vector2(Gdx.graphics.getWidth() / 3, 0));
        sprites.add(sprite);
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        sprites.forEach(s -> s.draw(batch));
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
