package com.example.joust;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


public class Enemy
{
    private int xPosition;
    private int yPosition;
    private Bitmap image;
    private Rect hitBox;
    private Direction currentDirection;

    enum Direction {
        NONE,
        RIGHT,
        LEFT
    }

    public Enemy(Context context, int x, int y, Direction initialDirection) {
        this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.snake_crawl_01);
        this.xPosition = x;
        this.yPosition = y;
        this.currentDirection = initialDirection;

        this.hitBox = new Rect(
                this.xPosition,
                this.yPosition,
                this.xPosition + this.image.getWidth(),
                this.yPosition + this.image.getHeight()
        );
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

    public Rect getHitBox() { return this.hitBox; }

    public Direction getCurrentDirection() { return this.currentDirection; }
    public void setCurrentDirection(Direction currentDirection)
    {
        this.currentDirection = currentDirection;
    }

    public void updatePosition(Direction direction, int steps) {
        int newPositionX = this.xPosition;
        switch (direction){
            case RIGHT:
                newPositionX = newPositionX + steps;
                this.setXPosition(newPositionX);
                break;
            case LEFT:
                newPositionX = newPositionX - steps;
                this.setXPosition(newPositionX);
                break;
            default:
                break;
        }
        this.hitBox.left = this.xPosition;
        this.hitBox.right = this.xPosition + this.image.getWidth();
    }
}
