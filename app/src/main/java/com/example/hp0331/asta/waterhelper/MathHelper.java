package com.example.hp0331.asta.waterhelper;

import java.util.Random;

/**
 * Created by hp0331 on 2017/2/7.
 */

public class MathHelper {
    public static Random rand = new Random();
    public static float randomRange(float min, float max) {


        int randomNum = rand.nextInt(((int) max - (int) min) + 1) + (int) min;

        return randomNum;
    }
}
