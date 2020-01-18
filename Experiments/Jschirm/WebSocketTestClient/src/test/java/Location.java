import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Random;

public class Location {


    private Integer id;

    private double x;
    private double y;
    private double z;
    private double w;
    private float velZ;
    private float velX;

    public Location(float x, float y, float z, float w, float velZ, float velX) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.velZ = velZ;
        this.velX = velX;
    }

    public Location() {
        Random random = new Random();
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
    public double getW() {
        return w;
    }

    /**
     * @return the z location of the player.
     */
    public double getZ() {
        return z;
    }

    /**
     * @return the y location of the player.
     */
    public double getY() {
        return y;
    }

    /**
     * @return the x location of the player.
     */
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setW(double w) {
        this.w = w;
    }

    public float getVelZ() {
        return velZ;
    }

    public float getVelX() {
        return velX;
    }
}
