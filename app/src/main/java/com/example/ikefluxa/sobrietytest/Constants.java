package com.example.ikefluxa.sobrietytest;

/**
 * Created by Ike&Fluxa on 1/5/2018.
 */

public class Constants {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static double getDist(float x1, float y1, float x2, float y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
