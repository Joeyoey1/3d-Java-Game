package com.barbarian.game.utils;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class VectorMath {

    /**
     * @param vec1
     * @param vec2
     * @return vector 3 addition
     */
    public static Vector3 add(Vector3 vec1, Vector3 vec2)
    {
        vec1.x += vec2.x;
        vec1.y += vec2.y;
        vec1.z += vec2.z;
        return vec1;
    }

    /**
     * @param vec1
     * @param vec2
     * @return temporary vector 3 addition
     */
    public static Vector3 add_temp(Vector3 vec1, Vector3 vec2)
    {
        Vector3 temp = new Vector3();
        temp.x = vec1.x + vec2.x;
        temp.y = vec1.y + vec2.y;
        temp.z = vec1.z + vec2.z;
        return temp;
    }

    /**
     * @param vec1
     * @param vec2
     * @return vector 3 subtraction
     */
    public static Vector3 subtract(Vector3 vec1, Vector3 vec2)
    {
        vec1.x -= vec2.x;
        vec1.y -= vec2.y;
        vec1.z -= vec2.z;
        return vec1;
    }

    /**
     * @param vec1
     * @param vec2
     * @return temporary vector 3 subtraction
     */
    public static Vector3 subtract_temp(Vector3 vec1, Vector3 vec2)
    {
        Vector3 temp = new Vector3();
        temp.x = vec1.x - vec2.x;
        temp.y = vec1.y - vec2.y;
        temp.z = vec1.z - vec2.z;
        return temp;
    }

    /**
     * @param vec1
     * @param vec2
     * @return vector 3 division
     */
    public static Vector3 divide(Vector3 vec1, Vector3 vec2)
    {
        vec1.x /= vec2.x;
        vec1.y /= vec2.y;
        vec1.z /= vec2.z;
        return vec1;
    }

    /**
     * @param vec1
     * @param vec2
     * @return vector 3 multiplication with two vector 3s
     */
    public static Vector3 multiply(Vector3 vec1, Vector3 vec2)
    {
        vec1.x *= vec2.x;
        vec1.y *= vec2.y;
        vec1.z *= vec2.z;
        return vec1;
    }

    /**
     * @param vec
     * @param q
     * @return vector 3 multiplication with vecotr 3 and quaternion
     */
    public static Vector3 multiply(Vector3 vec, Quaternion q)
    {
        Vector3 temp = vec.cpy();
        return temp.mul(q);
    }

    /**
     * @param vec
     * @return inverse vector 3
     */
    public static Vector3 negate(Vector3 vec)
    {
        vec.x = -vec.x;
        vec.y = -vec.y;
        vec.z = -vec.z;
        return vec;
    }

    /**
     * @param vec1
     * @param vec2
     * @return 1 if vector 3s are equal
     */
    public static boolean is_equal(Vector3 vec1, Vector3 vec2)
    {
        return vec1.x == vec2.x && vec1.y == vec2.y && vec1.z == vec2.z;
    }

    /**
     * @param vec
     * @param scale
     * @return vector3 scaled to magnitude of scale
     */
    public static Vector3 scale(Vector3 vec, float scale)
    {
        Vector3 vec_temp = new Vector3(scale * vec.x, scale * vec.y, scale * vec.z);
        return vec_temp;
    }

    /**
     * @param r
     * @return creates vector 3 with random values
     */
    public static Vector3 random(float r)
    {
        float x = r / (float)(Math.random() % 10 );
        float y = r / (float)(Math.random() % 10 );
        float z = r / (float)(Math.random() % 10 );
        return new Vector3(x,y,z).nor();
    }
}
