package com.wchtpapaya.brutalrun;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Utils {

    public static double distance(double sourceX, double sourceY, double destinationX, double destinationY) {
        return sqrt(pow(destinationX - sourceX, 2) + pow(destinationY - sourceY, 2));
    }
}
