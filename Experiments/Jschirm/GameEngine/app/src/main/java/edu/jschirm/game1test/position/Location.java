package edu.jschirm.game1test.position;

/**
 * @author John Schirm
 */
public class Location {


    /**
     * This is the variables for the X and Z Coordinates.
     */
    private int x, z;


    /**
     * This creates a new Location instance with the give X and Z Coordinates.
     * @param x the X-Coordinate to be set.
     * @param z the Z-Coordinate to be set.
     */
    public Location(int x, int z) {
        this.x = x;
        this.z = z;
    }


    /**
     * X getter method.
     * @return returns the locations current X-Coordinate.
     */
    public int getX() {
        return this.x;
    }


    /**
     * Z getter method.
     * @return returns the locations current Z-Coordinate.
     */
    public int getZ() {
        return this.z;
    }

    /**
     * This changes the X-Coordinate of the location.
     * @param x the new X-Coordinate;
     */
    public void setX(int x) {
        this.x = x;
    }


    /**
     * This changes the Z-Coordinate of the location.
     * @param z the new Z-Coordinate;
     */
    public void setZ (int z) {
        this.z = z;
    }

}
