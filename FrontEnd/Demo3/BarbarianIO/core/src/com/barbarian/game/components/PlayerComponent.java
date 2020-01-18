package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.Game;
import com.barbarian.game.models.Location;
import com.barbarian.game.models.Player;

public class PlayerComponent implements Component {
    private Player player;
    private Location location;
    private int ID;

    public PlayerComponent(Player player) {
        this.player = player;
        location = player.getLocation();
        this.player.setLocation(location);
        player.setId(Game.thisID);
        this.ID = Game.thisID;
    }

    public Player getEntityPlayer() {
        return this.player;
    }
    public Location getLocation() {return this.location;}

    public int getID() {
        return this.ID;
    }
}