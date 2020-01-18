package edu.jschirm.game1test.rendering;

import android.graphics.Color;

import java.util.List;

import edu.jschirm.game1test.entity.Player;
import edu.jschirm.game1test.main.GameView;

public class Screen {

    private int[][] mapClone;
    private int mapWidth, mapHeight, width, height;
    private List<Textures> textures;


    public Screen(List<Textures> textures, int width, int height) {
        this.mapClone = GameView.map.clone();
        this.textures = textures;
        this.width = width;
        this.height = height;
    }

    public int[] update(Player player, int[] pixels) {
        for (int i = 0; i < pixels.length/2; i++) {
            if (pixels[i] != Color.DKGRAY)
        }
        for (int i = pixels.length / 2; i < pixels.length; i++) {

        }
    }


}
