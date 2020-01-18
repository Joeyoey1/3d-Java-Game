package com.barbarian.game.colliders;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;

public class Collision {

    public static boolean checkCollision(RectangleCollider col_1, RectangleCollider col_2) {

        Vector3 aBox = col_1.box;
        Vector3 bBox = col_2.box;
        Vector3 aPos = col_1.position;
        Vector3 bPos = col_2.position;
        float aRot = col_1.rotation.getYaw();
        float bRot = col_2.rotation.getYaw();


        Vector3[] aCorners = new Vector3[5];
        Vector3[] bCorners = new Vector3[5];

        for (int i = 0; i < 4; i++) {
            aCorners[i] = getCorner(aPos, aBox, aRot, i + 1);
            bCorners[i] = getCorner(bPos, bBox, bRot, i + 1);
        }
        aCorners[4] = aCorners[0];
        bCorners[4] = bCorners[0];

        for (int i = 0; i < 4; i++) {
            for (int n = 0; n < 4; n++) {
                /* Calculate area of triangle ABC */
                float A = area(bPos.x, bPos.z, bCorners[n].x, bCorners[n].z, bCorners[n + 1].x, bCorners[n + 1].z);
                float A3 = area3(bPos.x, bPos.z, bCorners[n].x, bCorners[n].z, bCorners[n + 1].x, bCorners[n + 1].z, aCorners[i].x, aCorners[i].z);
                if (Math.abs(A - A3) < 1) return true;

                A = area(aPos.x, aPos.z, aCorners[n].x, aCorners[n].z, aCorners[n + 1].x, aCorners[n + 1].z);
                A3 = area3(aPos.x, aPos.z, aCorners[n].x, aCorners[n].z, aCorners[n + 1].x, aCorners[n + 1].z, bCorners[i].x, bCorners[i].z);
                if (Math.abs(A - A3) < 1) return true;
            }
        }
        return false;
    }

    private static float area(float x1, float y1, float x2, float y2, float x3, float y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) +
                x3 * (y1 - y2)) / 2f);
    }

    private static float area3(float x1, float z1, float x2, float z2, float x3, float z3, float y, float w) {
        float A1 = area(y, w, x2, z2, x3, z3);
        float A2 = area(x1, z1, y, w, x3, z3);
        float A3 = area(x1, z1, x2, z2, y, w);
        return A1 + A2 + A3;
    }

    private static Vector3 getCorner(Vector3 pos, Vector3 cBox, float rot, int corner) {
        rot = (float) Math.toRadians(rot);

        float x, z;
        switch (corner) {
            case 1:
                x = pos.x + ((cBox.x / 2) * (float) Math.cos(rot)) + ((cBox.z / 2) * (float) Math.sin(rot));
                z = pos.z - ((cBox.x / 2) * (float) Math.sin(rot)) + ((cBox.z / 2) * (float) Math.cos(rot));
                return new Vector3(x, 0, z);
            case 2:
                x = pos.x + ((cBox.x / 2) * (float) Math.cos(rot)) + (-(cBox.z / 2) * (float) Math.sin(rot));
                z = pos.z - ((cBox.x / 2) * (float) Math.sin(rot)) + (-(cBox.z / 2) * (float) Math.cos(rot));
                return new Vector3(x, 0, z);
            case 3:
                x = pos.x + (-(cBox.x / 2) * (float) Math.cos(rot)) + (-(cBox.z / 2) * (float) Math.sin(rot));
                z = pos.z - (-(cBox.x / 2) * (float) Math.sin(rot)) + (-(cBox.z / 2) * (float) Math.cos(rot));
                return new Vector3(x, 0, z);
            case 4:
                x = pos.x + (-(cBox.x / 2) * (float) Math.cos(rot)) + ((cBox.z / 2) * (float) Math.sin(rot));
                z = pos.z - (-(cBox.x / 2) * (float) Math.sin(rot)) + ((cBox.z / 2) * (float) Math.cos(rot));
                return new Vector3(x, 0, z);
        }
        return new Vector3();
    }

    public static boolean checkCollision(CircleCollider c1, CircleCollider c2){
        float r1 = c1.radius;
        float r2 = c2.radius;
        Vector3 pos1 = c1.position;
        Vector3 pos2 = c2.position;

        double x = Math.pow((pos2.x - pos1.x), 2);
        double y = Math.pow((pos2.y - pos1.y), 2);
        double z = Math.pow((pos2.z - pos1.z), 2);
        float dist = (float)Math.sqrt(x + y + z);

        if(dist <= r1 + r2) return true;
        return false;
    }
}
