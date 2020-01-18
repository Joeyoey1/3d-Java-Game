package edu.jschirm.game1test.entity;

public class Player {

    public double xPos, zPos, xDir, zDir, xPlane, zPlane;
    public boolean left, right, forward, backward;
    public final double MOVE_SPEED = .08;
    public final double ROTATION_SPEED = .045;


    public Player(double x, double z, double xDir, double zDir, double xPlane, double zPlane) {
        this.xPos = x;
        this.zPos = z;
        this.xDir = xDir;
        this.zDir = zDir;
        this.xPlane = xPlane;
        this.zPlane = zPlane;
    }


    public void update(int[][] map) {
        if(forward) {
            if(map[(int)(xPos + xDir * MOVE_SPEED)][(int)zPos] == 0) {
                xPos+=xDir*MOVE_SPEED;
            }
            if(map[(int)xPos][(int)(zPos + zDir * MOVE_SPEED)] ==0)
                zPos+=zDir*MOVE_SPEED;
        }
        if(backward) {
            if(map[(int)(xPos - xDir * MOVE_SPEED)][(int)zPos] == 0)
                xPos-=xDir*MOVE_SPEED;
            if(map[(int)xPos][(int)(zPos - zDir * MOVE_SPEED)]==0)
                zPos-=zDir*MOVE_SPEED;
        }
        if(right) {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(-ROTATION_SPEED) - zDir*Math.sin(-ROTATION_SPEED);
            zDir=oldxDir*Math.sin(-ROTATION_SPEED) + zDir*Math.cos(-ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(-ROTATION_SPEED) - zPlane*Math.sin(-ROTATION_SPEED);
            zPlane=oldxPlane*Math.sin(-ROTATION_SPEED) + zPlane*Math.cos(-ROTATION_SPEED);
        }
        if(left) {
            double oldxDir=xDir;
            xDir=xDir*Math.cos(ROTATION_SPEED) - zDir*Math.sin(ROTATION_SPEED);
            zDir=oldxDir*Math.sin(ROTATION_SPEED) + zDir*Math.cos(ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane=xPlane*Math.cos(ROTATION_SPEED) - zPlane*Math.sin(ROTATION_SPEED);
            zPlane=oldxPlane*Math.sin(ROTATION_SPEED) + zPlane*Math.cos(ROTATION_SPEED);
        }
    }



}
