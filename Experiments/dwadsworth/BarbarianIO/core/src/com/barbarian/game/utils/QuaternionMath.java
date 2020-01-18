package com.barbarian.game.utils;

import com.badlogic.gdx.math.Quaternion;

public class QuaternionMath {

    /**
     * @param q1
     * @param q2
     * @return quaternion addition
     */
    public static Quaternion add(Quaternion q1, Quaternion q2)
    {
        q1.w += q2.w;
        q1.x += q2.x;
        q1.y += q2.y;
        q1.z += q2.z;
        return q1;
    }

    /**
     * @param q1
     * @param q2
     * @return quaternion subtraction
     */
    public static Quaternion subtract(Quaternion q1, Quaternion q2)
    {
        q1.w -= q2.w;
        q1.x -= q2.x;
        q1.y -= q2.y;
        q1.z -= q2.z;
        return q1;
    }

    /**
     * @param q1
     * @param q2
     * @return quaternion multiplication
     */
    public static Quaternion multiply(Quaternion q1, Quaternion q2)
    {
        q1.w *= q2.w;
        q1.x *= q2.x;
        q1.y *= q2.y;
        q1.z *= q2.z;
        return q1;
    }

    /**
     * @param q1
     * @param q2
     * @return quaternion division
     */
    public static Quaternion divide(Quaternion q1, Quaternion q2)
    {
        q1.w /= q2.w;
        q1.x /= q2.x;
        q1.y /= q2.y;
        q1.z /= q2.z;
        return q1;
    }

    /**
     * @param q
     * @return inverse of a quaternion
     */
    public static Quaternion inverse(Quaternion q)
    {
        float d = q.x*q.x + q.y*q.y + q.z*q.z + q.w*q.w;
        return new Quaternion(q.x/d, -q.y/d, -q.z/d, -q.w/d);
    }
}
