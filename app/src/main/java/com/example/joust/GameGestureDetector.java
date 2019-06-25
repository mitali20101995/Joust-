package com.example.joust;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import static com.example.joust.GameEngine.TAG;

public class GameGestureDetector extends GestureDetector.SimpleOnGestureListener {
    int MIN_DISTANCE = 150;
    int VELOCITY_THRESHOLD = 75;
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling");

        if (e1.getX() - e2.getX() > MIN_DISTANCE && Math.abs(velocityX) > VELOCITY_THRESHOLD) {
            // right to left swipe
            Log.d(TAG, "Swipe right to left");
        } else if (e2.getX() - e1.getX() > MIN_DISTANCE && Math.abs(velocityX) > VELOCITY_THRESHOLD) {
            // left to right swipe
            Log.d(TAG, "Swipe left to right");
        } else if (e1.getY() - e2.getY() > MIN_DISTANCE && Math.abs(velocityY) > VELOCITY_THRESHOLD) {
            // bottom to top
            Log.d(TAG, "Swipe bottom to top");
        } else if (e2.getY() - e1.getY() > MIN_DISTANCE && Math.abs(velocityY) > VELOCITY_THRESHOLD) {
            // top to bottom
            Log.d(TAG, "Swipe top to bottom");
        }
        return true;
      }
}
