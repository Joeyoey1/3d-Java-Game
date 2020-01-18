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

    /**
     * cretaes new player component
     */
    public PlayerComponent() {
        this.player = new Player();
        location = new Location();
        this.player.setLocation(location);
        player.setId(Game.thisID);
        this.ID = Game.thisID;
    }

    /**
     * @return player object
     */
    public Player getEntityPlayer() {
        return this.player;
    }

    /**
     * @return location object of player
     */
    public Location getLocation() {return this.location;}

    /**
     * @return id of player
     */
    public int getID() {
        return this.ID;
    }
}