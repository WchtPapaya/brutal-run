package com.wchtpapaya.brutalrun;

import com.badlogic.gdx.math.MathUtils;
import com.wchtpapaya.brutalrun.sprite.GameObject;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Utils {

    public static double distance(double sourceX, double sourceY, double destinationX, double destinationY) {
        return sqrt(pow(destinationX - sourceX, 2) + pow(destinationY - sourceY, 2));
    }
    public static double distance(GameObject from, GameObject to) {
        return distance(from.getX(), from.getY(), to.getX(), to.getY());
    }
}
