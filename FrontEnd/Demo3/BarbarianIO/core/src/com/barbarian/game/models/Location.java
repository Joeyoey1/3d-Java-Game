package com.barbarian.game.models;

import com.badlogic.gdx.math.Vector3;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Location implements Serializable {

    private float x;
    private float y;
    private float z;
    private float w;
    private float velZ;
    private float velX;

    public Location(@JsonProperty("x") float x, @JsonProperty("y") float y, @JsonProperty("z") float z, @JsonProperty("w") float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Location() {
        x = 0;
        y = 0;
        z = 0;
        w = 0;
        velX = 0;
        velZ = 0;
    }

    public Location(float x, float y, float z, float w, float velZ, float velX) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.velZ = velZ;
        this.velX = velX;
    }

    /**
     * @return the yaw the player is looking.
     */
    public float getW() {
        return w;
    }
    public void setW(float n) {
        w = n;
    }

    /**
     * @return the z location of the player.
     */

    public float getZ() {
        return z;
    }
    public void setZ(float z_n) {z = z_n;}

    /**
     * @return the y location of the player.
     */
    public float getY() {
        return y;
    }
    public void setY(float y_n) {y = y_n;}

    /**
     * @return the x location of the player.
     */

    public float getX() {
        return x;
    }
    public void setX(float x_n) {x = x_n;}

    public float getVelX() {return velX;}
    public void setVelX(float velX_n) {velX = velX_n;}

    public float getVelZ() {return velZ;}
    public void setVelZ(float velZ_n) {velZ = velZ_n;}
}

