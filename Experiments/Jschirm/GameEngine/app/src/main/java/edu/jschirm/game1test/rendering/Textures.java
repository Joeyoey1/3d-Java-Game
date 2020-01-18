package edu.jschirm.game1test.rendering;

import android.graphics.Bitmap;

public class Textures {

    private int[] pixels;
    private Bitmap bitmap;
    private final int SIZE;

    public Textures(Bitmap bitmap, int size) {
        this.bitmap = bitmap;
        this.SIZE = size;
        pixels = new int[SIZE * SIZE];
        load();
    }

    /**
     * This method attempts to load the Texture into an array of pixels
     */
    private void load() {
        try {
            bitmap.getPixels(pixels, 0, (64 < bitmap.getWidth()) ? 64 : bitmap.getWidth(), 0,0, bitmap.getWidth(), bitmap.getHeight());
            System.out.println(pixels.length + " This is how many pixels are in a row.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int[] getPixels() {
        return this.pixels;
    }


}
