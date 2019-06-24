package com.example.joust;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {
    // -----------------------------------
    // ## ANDROID DEBUG VARIABLES
    // -----------------------------------

    // Android debug variables
    final static String TAG="JOUST-GAME";

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



    // -----------------------------------
    // ## GAME SPECIFIC VARIABLES
    // -----------------------------------

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


    public GameEngine(Context context, int w, int h) {
        super(context);


        this.holder = this.getHolder();
        this.paintbrush = new Paint();

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
       // player.playerImage.setHeight(20);
       // player.playerImage.setWidth(20);

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
            this.updatePositions();
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
    public void updatePositions() {
        // @TODO: Update the position of the sprites

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
            gameThread.sleep(50);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {
            // user pushed down on screen
        }
        else if (userAction == MotionEvent.ACTION_UP) {
            // user lifted their finger
        }
        return true;
    }
}
