package edu.jschirm.game1test.main;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    /**
     * This is our instance of SurfaceHolder.
     */
    private SurfaceHolder holder;

    /**
     * This is our instance of GameView.
     */
    private GameView gameView;

    /**
     * This denotes whether the Thread should be running or not.
     */
    private boolean running;

    /**
     * This is the variable for which we are painting our pretty picture.
     */
    public static Canvas canvas;

    /**
     * This is the constructor for our MainThread this is the primary constructor.
     * @param holder this is the SurfaceHolder instance that is passed in.
     * @param gameView this is the GameView instance that is passed in.
     */
    public MainThread(SurfaceHolder holder, GameView gameView) {
        super();

        this.holder = holder;
        this.gameView = gameView;
    }


    /**
     * This method sets the game in a running "STATE" (not really a state persay but you get the gist.)
     * @param running this is the new "STATE" its really just a "running? Yes : No"
     */
    public void setRunning(boolean running) {
        this.running = running;
    }


    /**
     * This is the main game loop that controls updates.
     */
    @Override
    public void run() {

        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0; //60 updates per second
        double delta = 0;

        while(this.running) {
            canvas = null;
            long now = System.nanoTime();
            delta = delta + ((now - lastTime) / ns); // checks how much time has passed since last "update"
            lastTime = now;
            while (delta >= 1) { // This makes sure the game stays at 60fps so that it doesn't look super choppy.
                try {
                    // This locks the canvas so that it is only being painted by one thread.. Wouldn't want other threads overlapping.
                    canvas = this.holder.lockCanvas();

                    // This runs a synchronized task that updates the canvas.
                    synchronized (holder) {
                        this.gameView.getPlayer().update(GameView.map);
                        this.gameView.update();
                        this.gameView.draw(canvas);
                    }
                    // Yes this is bad practice I shouldn't catch general Exceptions... Bad slap on the wrist for me :C
                } catch (Exception e) {
                } finally {
                    if (canvas != null) {
                        try {
                            this.holder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                delta--;
            }

        }


    }



}
