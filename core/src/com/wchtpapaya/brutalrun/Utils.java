package com.wchtpapaya.brutalrun;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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

    /**
     *
     * @param range represent distance from second point (x2, y2)
     * @return point at line between points (x1, y1), (x2, y2)
     */
    public static Vector2 pointAtLine(double x1, double y1, double x2, double y2, double range) {
        float distance = (float) distance(x1, y1, x2, y2);
        float x = (float) (x1 + (distance - range) * (x2 - x1) / distance);
        float y = (float) (y1 + (distance - range) * (y2 - y1) / distance);
        return new Vector2(x, y);
    }
}
