package com.example.joust;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.joust.GameEngine.DOWN_RELOCATION_MAX;
import static com.example.joust.GameEngine.LEFT_RELOCATION_MAX;
import static com.example.joust.GameEngine.RIGHT_RELOCATION_MAX;
import static com.example.joust.GameEngine.UP_RELOCATION_MAX;

public class Player {
    private int xPosition;
    private int yPosition;
    private Rect hitBox;
    Bitmap playerImage;

    enum Direction {
        NONE,
        UP,
        DOWN,
        RIGHT,
        LEFT
    }


    public Player(Context context, int x, int y) {
        this.playerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.skunk_walk);
        this.xPosition = x;
        this.yPosition = y;
        this.hitBox = new Rect(
                this.xPosition,
                this.yPosition,
                this.xPosition + this.playerImage.getWidth(),
                this.yPosition + this.playerImage.getHeight()
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
        return this.playerImage;
    }

    public Rect getHitbox() {
        return this.hitBox;
    }
    public int getWidth() { return this.playerImage.getWidth(); }

    public void updatePlayerPosition(Direction direction, int steps) {
        int newPositionX = this.xPosition;
        int newPositionY = this.yPosition;
        switch (direction){

            case UP:
                newPositionY = newPositionY - steps;
                if(newPositionY < UP_RELOCATION_MAX){
                    newPositionY = DOWN_RELOCATION_MAX;
                }
                this.setYPosition(newPositionY);
                break;

            case DOWN:
                if(newPositionY > DOWN_RELOCATION_MAX){
                    newPositionY = UP_RELOCATION_MAX;
                }
                newPositionY = newPositionY + steps;
                this.setYPosition(newPositionY);
                break;

            case RIGHT:
                newPositionX = newPositionX + steps;
                if(newPositionX > RIGHT_RELOCATION_MAX){
                    newPositionX = LEFT_RELOCATION_MAX;
                }
                this.setXPosition(newPositionX);
                break;
            case LEFT:
                newPositionX = newPositionX - steps;
                if(newPositionX < LEFT_RELOCATION_MAX){
                    newPositionX = RIGHT_RELOCATION_MAX;
                }
                this.setXPosition(newPositionX);
                break;
            default:
                break;
        }
        updateHitBoxPosition();
    }

    private void updateHitBoxPosition() {
        this.hitBox.left = this.xPosition;
        this.hitBox.top = this.yPosition;
        this.hitBox.right = this.xPosition + this.playerImage.getWidth();
        this.hitBox.bottom = this.yPosition + this.playerImage.getHeight();
    }

    public void updatePlayerPosition(int xPosition, int yPosition){
        this.setXPosition(xPosition);
        this.setYPosition(yPosition);
        updateHitBoxPosition();
    }
}
