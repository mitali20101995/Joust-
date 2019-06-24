package com.example.joust;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Player
{
    int xPosition;
    int yPosition;
    int direction;
    Bitmap playerImage;


            public Player(Context context, int x, int y) {
                this.playerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.skunk_walk);
                this.xPosition = x;
                this.yPosition = y;
                //this.playerImage.setWidth(width);
                //this.playerImage.setHeight(height);
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


}
