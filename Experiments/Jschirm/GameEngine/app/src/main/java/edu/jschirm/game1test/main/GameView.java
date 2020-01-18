package edu.jschirm.game1test.main;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.jschirm.game1test.R;
import edu.jschirm.game1test.entity.EntitySprite;
import edu.jschirm.game1test.entity.Player;
import edu.jschirm.game1test.rendering.Textures;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private EntitySprite entitySprite;
    private static Set<EntitySprite> entitySpriteSet = new HashSet<>();
    private Player player;
    private List<Textures> wallTextures = new ArrayList<>();
    public int[] pixels;
    public static int[][] map = {
            {1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
            {1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
            {1,0,3,3,3,3,3,0,0,0,0,0,0,0,2},
            {1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
            {1,0,3,0,0,0,3,0,2,2,2,0,2,2,2},
            {1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
            {1,0,3,3,0,3,3,0,2,0,0,0,0,0,2},
            {1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
            {1,1,1,1,1,1,1,1,4,4,4,0,4,4,4},
            {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
            {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
            {1,0,0,2,0,0,1,4,0,3,3,3,3,0,4},
            {1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
            {1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
    };


    public GameView(Context context) {
        super(context);

        this.getHolder().addCallback(this);
        this.thread = new MainThread(this.getHolder(), this);
        this.setFocusable(true);
        this.player = new Player(5,5,1,1,0,-.66);
    }

    public void update() {
        for (EntitySprite entitySprites : entitySpriteSet) {
            entitySprites.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            //canvas.drawBitmap(null /*BITMAP GOES HERE*/,0,0,null);
            this.entitySprite.draw(canvas);
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        loadWallTextures();
        this.entitySprite = new EntitySprite(BitmapFactory.decodeResource(getResources(), R.drawable.avdgreen));
        this.thread.setRunning(true);
        this.thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                this.thread.setRunning(false);
                this.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }


    public static Set<EntitySprite> getNonPlayerEntities() {
        return entitySpriteSet;
    }

    public Player getPlayer() {
        return this.player;
    }

    private void loadWallTextures() {
        //Textures texture1 = new Textures(BitmapFactory.decodeFile((root + "/res/GameBack1.png")), 64);
        //this.wallTextures.add(texture1);
       // Textures texture2 = new Textures(BitmapFactory.decodeFile((root + "/res/GameBack2.png")), 64);
        //this.wallTextures.add(texture2);
        //Textures texture3 = new Textures(BitmapFactory.decodeFile((root + "/res/GameBack3.jpg")), 64);
        //this.wallTextures.add(texture1);
       // Textures texture4 = new Textures(BitmapFactory.decodeFile((root + "/res/GameBack4.jpg")), 64);
       // this.wallTextures.add(texture2);
    }

    public int[][] getMap() {
        return this.map;
    }


}
