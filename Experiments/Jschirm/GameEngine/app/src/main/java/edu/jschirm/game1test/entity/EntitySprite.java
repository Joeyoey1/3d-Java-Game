package edu.jschirm.game1test.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import edu.jschirm.game1test.main.GameView;
import edu.jschirm.game1test.position.Location;


/**
 * @author John Schirm
 */
public class EntitySprite {

    /**
     * This is the image of the Entity.
     */
    private Bitmap img;

    /**
     * This is the being's X and Z coordinates.
     */
    private Location location;

    /**
     * This is the basic constructor that ties the image to the entity.
     * @param bMap The image of the entity.
     */
    public EntitySprite(Bitmap bMap) {
        this.img = bMap;
        GameView.getNonPlayerEntities().add(this);
        this.location = new Location(0,0);
    }

    /**
     * This constructor creates the given sprite to a specific location.
     * @param bMap The image of the entity.
     * @param x The X-Coordinate of the entity.
     * @param z The Z-Coordinate of the entity.
     */
    public EntitySprite(Bitmap bMap, int x, int z) {
        this.img = bMap;
        this.location = new Location(x,z);
        GameView.getNonPlayerEntities().add(this);
    }


    /**
     * This takes in a canvas and draws the Sprite from this instance on it.
     * @param canvas The canvas that is to be drawn on.
     */
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.img, (int) this.location.getX(), (int) this.location.getZ(),null);
    }

    /**
     * This method updates and moves the entity in a specific direction.
     */
    public void update() {
        this.location.setZ(this.location.getZ() + 1);
    }


}
