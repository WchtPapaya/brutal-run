package com.wchtpapaya.brutalrun;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class WorldHandler {

    static World world = new World(new Vector2(0, 0), true);

    public static World getWorld() {
        return world;
    }
}