package com.jschirm.playerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column @NotNull
    private float x;
    @Column @NotNull
    private float y;
    @Column @NotNull
    private float z;
    @Column @NotNull
    private float w;
    @Transient
    private float velZ;
    @Transient
    private float velX;

    /**
     * @param x
     * @param y
     * @param z
     * @param w
     * @param velZ
     * @param velX
     * new Location object of player with given params
     */
    public Location(@JsonProperty("x") float x, @JsonProperty("y") float y, @JsonProperty("z") float z, @JsonProperty("w") float w, @JsonProperty("velZ") float velZ, @JsonProperty("velX") float velX) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.velZ = velZ;
        this.velX = velX;
    }

    /**
     * new Location object with all 0s
     */
    public Location() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
        this.velX = 0;
        this.velZ = 0;
    }


    /**
     * @return the yaw the player is looking.
     */
    public float getW() {
        return w;
    }

    /**
     * @return the z location of the player.
     */
    public float getZ() {
        return z;
    }

    /**
     * @return the y location of the player.
     */
    public float getY() {
        return y;
    }

    /**
     * @return the x location of the player.
     */
    public float getX() {
        return x;
    }

    /**
     * @return z velocity
     */
    public float getVelZ() {
        return velZ;
    }

    /**
     * @return x velocity
     */
    public float getVelX() {
        return velX;
    }

    /**
     * @param x
     * set x position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @param y
     * set y position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * @param z
     * setz position
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * @param w
     * set rotation
     */
    public void setW(float w) {
        this.w = w;
    }

    /**
     * @param velZ
     * set z velocity
     */
    public void setVelZ(float velZ) {
        this.velZ = velZ;
    }

    /**
     * @param velX
     * set x velocity
     */
    public void setVelX(float velX) {
        this.velX = velX;
    }
}
