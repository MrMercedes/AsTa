package com.example.hp0331.asta.waterhelper;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by hp0331 on 2017/2/7.
 */

public class Renderable {
    public Bitmap bitmap;
    public float x;//bitmap放置的x坐标
    public float y;//bitmap放置的y坐标

    public Renderable(Bitmap bitmap, float x, float y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    public void update(float deltaTime) {

    }

    public void draw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(bitmap, x, y, null);
        canvas.restore();
    }
}
