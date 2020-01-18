package com.barbarian.game.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.components.CollisionComponent;
import com.barbarian.game.components.TransformComponent;

public class CollisionMath {
    private static boolean flag;

    /**
     * @param aBox
     * @param bBox
     * @param aPos
     * @param bPos
     * @param aRot
     * @param bRot
     * @return 1 if collision is detected
     * checks for collision between two entities based by checking if the corner of one entity is within another
     */
    public static boolean checkCollision(Vector3 aBox, Vector3 bBox, Vector3 aPos, Vector3 bPos, float aRot, float bRot){

        Vector3[] aCorners =  new Vector3[5];
        Vector3[] bCorners =  new Vector3[5];

        float aHypot = (float)Math.sqrt((aBox.x/2 * aBox.x/2) + (aBox.z/2 * aBox.z/2));
        float bHypot = (float)Math.sqrt((bBox.x/2 * bBox.x/2) + (bBox.z/2 * bBox.z/2));

        for(int i = 0; i < 4; i++){
            aCorners[i] = new Vector3(aPos.x + (aHypot * angle(0, aRot, i+1)), 0,aPos.z + (aHypot * angle(1, aRot, i+1)));
            bCorners[i] = new Vector3(bPos.x + (bHypot * angle(0, bRot, i+1)), 0,bPos.z + (bHypot * angle(1, bRot, i+1)));
        }
        aCorners[4] = aCorners[0];
        bCorners[4] = bCorners[0];

        for(int i = 0; i < 4; i++){
            for(int n = 0; n < 4; n++){
                /* Calculate area of triangle ABC */
                float A = area (bPos.x, bPos.z, bCorners[n].x, bCorners[n].z, bCorners[n+1].x, bCorners[n+1].z);
                float A3 = area3 (bPos.x, bPos.z, bCorners[n].x, bCorners[n].z, bCorners[n+1].x, bCorners[n+1].z, aCorners[i].x, aCorners[i].z);
                if(Math.abs(A - A3) < 1 && !flag){
                    flag = true;
                    return true;
                }
                if(flag){
                    flag = false;
                }
                A = area (aPos.x, aPos.z, aCorners[n].x, aCorners[n].z, aCorners[n+1].x, aCorners[n+1].z);
                A3 = area3 (aPos.x, aPos.z, aCorners[n].x, aCorners[n].z, aCorners[n+1].x, aCorners[n+1].z, bCorners[i].x, bCorners[i].z);
                if(Math.abs(A - A3) < 1 && !flag){
                    flag = true;
                    return true;
                }
                if(flag){
                    flag = false;
                }
            }
        }
        return false;
    }

    /**
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     * @return the are of the the triangle of the three points
     */
    private static float area(float x1, float y1, float x2, float y2, float x3, float y3) {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+
                x3*(y1-y2))/2f);
    }

    /**
     * @param x1
     * @param z1
     * @param x2
     * @param z2
     * @param x3
     * @param z3
     * @param y
     * @param w
     * @return the area of three triangles created from the points
     */
    private static float area3(float x1, float z1, float x2, float z2, float x3, float z3, float y, float w){
        float A1 = area (y, w, x2, z2, x3, z3);
        float A2 = area (x1, z1, y, w, x3, z3);
        float A3 = area (x1, z1, x2, z2, y, w);
        return A1 + A2 + A3;
    }

    /**
     * @param op
     * @param rot
     * @param corner
     * @return the angle sin or cos of an angle given the parameters
     */
    private static float angle(int op, float rot, int corner){
        double angle = Math.toRadians(Math.toDegrees(rot * 360) + (corner * 90) - 45);
        if(op == 0){
            return (float)Math.sin(angle);
        }
        return (float)Math.cos(angle);
    }

}
