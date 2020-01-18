package com.barbarian.game.models;

import com.badlogic.gdx.math.Vector3;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

    private float x;
    private float y;
    private float z;
    private float w;
    private float velZ;
    private float velX;

    /**
     * @param x
     * @param y
     * @param z
     * @param w
     * creates Location object with given params
     */
    public Location(@JsonProperty("x") float x, @JsonProperty("y") float y, @JsonProperty("z") float z, @JsonProperty("w") float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * creates Location object with all 0
     */
    public Location() {
        x = 0;
        y = 0;
        z = 0;
        w = 0;
        velX = 0;
        velZ = 0;
    }

    /**
     * @return the yaw the player is looking.
     */
    public float getW() {
        return w;
    }

    /**
     * @param n
     */
    public void setW(float n) {
        w = n;
    }

    /**
     * @return the z location of the player.
     */

    public float getZ() {
        return z;
    }

    /**
     * @param z_n
     */
    public void setZ(float z_n) {z = z_n;}

    /**
     * @return the y location of the player.
     */
    public float getY() {
        return y;
    }

    /**
     * @param y_n
     */
    public void setY(float y_n) {y = y_n;}

    /**
     * @return the x location of the player.
     */

    public float getX() {
        return x;
    }

    /**
     * @param x_n
     */
    public void setX(float x_n) {x = x_n;}

    /**
     * @return x velocity value of location
     */
    public float getVelX() {return velX;}

    /**
     * @param velX_n
     */
    public void setVelX(float velX_n) {velX = velX_n;}

    /**
     * @return z velocity value of location
     */
    public float getVelZ() {return velZ;}

    /**
     * @param velZ_n
     */
    public void setVelZ(float velZ_n) {velZ = velZ_n;}
}

