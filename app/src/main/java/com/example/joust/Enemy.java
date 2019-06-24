package com.example.joust;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Enemy
{
    int xPosition;
    int yPosition;
    int direction;
    Bitmap image;

    public Enemy(Context context, int x, int y) {
        this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.snake_crawl_01);
        this.xPosition = x;
        this.yPosition = y;
    }

    public void setXPosition(int x) {
        this.xPosition = x;
    }
    public void setYPosition(int y) {
        this.yPosition = y;
    }

    public int getXPosition() {
        return this.xPosition;
    }
    public int getYPosition() {
        return this.yPosition;
    }

    public Bitmap getBitmap() {
        return this.image;
    }

}
