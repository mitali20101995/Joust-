package com.example.joust;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameEngine extends SurfaceView implements Runnable {
    // -----------------------------------
    // ## ANDROID DEBUG VARIABLES
    // -----------------------------------

    // Android debug variables
    final static String TAG="JOUST-GAME";
    int MIN_DISTANCE = 150;
    int VELOCITY_THRESHOLD = 75;

    // -----------------------------------
    // ## SCREEN & DRAWING SETUP VARIABLES
    // -----------------------------------

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;

    GestureDetectorCompat detector;
       // ----------------------------
    // ## SPRITES
    // ----------------------------
    Bitmap background;
    int bgX;            // x-coordinate of the top-left corner of the background

    Player player;
    Enemy enemy;
    // ----------------------------
    // ## GAME STATS - number of lives, score, etc
    // ----------------------------
    static int RELOCATE_UP_DOWN = 400;
    static int RELOCATE_RIGHT_LEFT = 200;

    static int LEFT_RELOCATION_MAX = 0;
    static int RIGHT_RELOCATION_MAX = 700;
    static int UP_RELOCATION_MAX = 100;
    static int DOWN_RELOCATION_MAX = 1300;
    enum Gesture {
        NONE,
        SWIPE_UP,
        SWIPE_DOWN,
        SWIPE_RIGHT,
        SWIPE_LEFT
    }

    public GameEngine(Context context, int w, int h) {
        super(context);


        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.detector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            private static final String DEBUG_TAG = "Gestures";

            @Override
            public boolean onDown(MotionEvent event) {
                Log.d(DEBUG_TAG,"onDown: " + event.toString());
                return true;
            }


            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2,
                                   float velocityX, float velocityY) {
                Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());

                if (event1.getX() - event2.getX() > MIN_DISTANCE && Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                    // right to left swipe
                    updatePositions(Gesture.SWIPE_LEFT);
                    Log.d(TAG, "Swipe right to left");
                    Log.d(TAG, "position:" + player.getXPosition() + "," + player.getYPosition());
                } else if (event2.getX() - event1.getX() > MIN_DISTANCE && Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                    // left to right swipe
                    updatePositions(Gesture.SWIPE_RIGHT);
                    Log.d(TAG, "Swipe left to right");
                    Log.d(TAG, "position:" + player.getXPosition() + "," + player.getYPosition());
                } else if (event1.getY() - event2.getY() > MIN_DISTANCE && Math.abs(velocityY) > VELOCITY_THRESHOLD) {
                    // bottom to top
                    updatePositions(Gesture.SWIPE_UP);
                    Log.d(TAG, "Swipe bottom to top");
                    Log.d(TAG, "position:" + player.getXPosition() + "," + player.getYPosition());
                } else if (event2.getY() - event1.getY() > MIN_DISTANCE && Math.abs(velocityY) > VELOCITY_THRESHOLD) {
                    // top to bottom
                    updatePositions(Gesture.SWIPE_DOWN);
                    Log.d(TAG, "Swipe top to bottom");
                    Log.d(TAG, "position:" + player.getXPosition() + "," + player.getYPosition());
                }
                return true;
            }
        });

        this.screenWidth = w;
        this.screenHeight = h;


        this.printScreenInfo();

        // @TODO: Add your sprites to this section
        // This is optional. Use it to:
        //  - setup or configure your sprites
        //  - set the initial position of your sprites


        // @TODO: Any other game setup stuff goes here
        this.setupBackground();
        this.spawnEnemyShips();
        this.spwanPlayer();


    }

    private void spwanPlayer() {
        player = new Player(this.getContext(),100,1300);

    }
    private void spawnEnemyShips() {
        enemy = new Enemy(this.getContext(), 400, 100);

    }

    private void setupBackground() {
        // get the background image from `res` folder and
        // scale it to be the same size as the phone
        this.background = BitmapFactory.decodeResource(this.getResources(), R.drawable.bg_spaceship);
        this.background = Bitmap.createScaledBitmap(this.background, this.screenWidth, this.screenHeight - 200, false);

    }

    // ------------------------------
    // HELPER FUNCTIONS
    // ------------------------------

    // This funciton prints the screen height & width to the screen.
    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }


    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning) {
            this.updatePositions(Gesture.NONE);
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    // 1. Tell Android the (x,y) positions of your sprites
    public void updatePositions(Gesture gesture) {
        // @TODO: Update the position of the sprites

        switch (gesture){

            case SWIPE_UP:
                player.updatePlayerPosition(Player.Direction.UP, RELOCATE_UP_DOWN);
            break;

            case SWIPE_DOWN:
                player.updatePlayerPosition(Player.Direction.DOWN, RELOCATE_UP_DOWN);
                break;

            case SWIPE_RIGHT:
                player.updatePlayerPosition(Player.Direction.RIGHT, RELOCATE_RIGHT_LEFT);
                break;
            case SWIPE_LEFT:
                player.updatePlayerPosition(Player.Direction.LEFT, RELOCATE_RIGHT_LEFT);
                break;
            default:
                break;
        }
        // @TODO: Collision detection code

    }

    // 2. Tell Android to DRAW the sprites at their positions
    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------
            // Put all your drawing code in this section

            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,0,0,255));
            paintbrush.setColor(Color.WHITE);


            //@TODO: Draw the sprites (rectangle, circle, etc)
            //@TODO: Draw the background
            canvas.drawBitmap(this.background, this.bgX , 0, null);

            //@TODO: Draw the player

            canvas.drawBitmap(this.player.getBitmap(), this.player.getXPosition(), this.player.getYPosition(), paintbrush);

            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);

            Rect playerHitbox = player.getHitbox();
            canvas.drawRect( playerHitbox.left,
                    playerHitbox.top,
                    playerHitbox.right,
                    playerHitbox.bottom,
                    paintbrush);

            //@TODO: Draw the enemy
            canvas.drawBitmap(this.enemy.getBitmap(), this.enemy.getXPosition(), this.enemy.getYPosition(), paintbrush);


            //@TODO: Draw game statistics (lives, score, etc)
            paintbrush.setTextSize(60);
            canvas.drawText("Score: 25", 20, 100, paintbrush);

            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    // Sets the frame rate of the game
    public void setFPS() {
        try {
            Thread.sleep(50);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return this.detector.onTouchEvent(event);
    }
}
