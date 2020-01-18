package com.barbarian.game.utils;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;

public class VectorMath {

    public static Vector3 add(Vector3 vec1, Vector3 vec2)
    {
        vec1.x += vec2.x;
        vec1.y += vec2.y;
        vec1.z += vec2.z;
        return vec1;
    }

    public static Vector3 add_temp(Vector3 vec1, Vector3 vec2)
    {
        Vector3 temp = new Vector3();
        temp.x = vec1.x + vec2.x;
        temp.y = vec1.y + vec2.y;
        temp.z = vec1.z + vec2.z;
        return temp;
    }

    public static Vector3 subtract(Vector3 vec1, Vector3 vec2)
    {
        vec1.x -= vec2.x;
        vec1.y -= vec2.y;
        vec1.z -= vec2.z;
        return vec1;
    }

    public static Vector3 subtract_temp(Vector3 vec1, Vector3 vec2)
    {
        Vector3 temp = new Vector3();
        temp.x = vec1.x - vec2.x;
        temp.y = vec1.y - vec2.y;
        temp.z = vec1.z - vec2.z;
        return temp;
    }
    public static Vector3 divide(Vector3 vec1, Vector3 vec2)
    {
        vec1.x /= vec2.x;
        vec1.y /= vec2.y;
        vec1.z /= vec2.z;
        return vec1;
    }

    public static Vector3 multiply(Vector3 vec1, Vector3 vec2)
    {
        vec1.x *= vec2.x;
        vec1.y *= vec2.y;
        vec1.z *= vec2.z;
        return vec1;
    }

    public static Vector3 multiply(Vector3 vec, Quaternion q)
    {
        Vector3 temp = vec.cpy();
        return temp.mul(q);
    }

    public static Vector3 negate(Vector3 vec)
    {
        vec.x = -vec.x;
        vec.y = -vec.y;
        vec.z = -vec.z;
        return vec;
    }

    public static boolean is_equal(Vector3 vec1, Vector3 vec2)
    {
        return vec1.x == vec2.x && vec1.y == vec2.y && vec1.z == vec2.z;
    }

    public static Vector3 scale(Vector3 vec, float scale)
    {
        Vector3 vec_temp = new Vector3(scale * vec.x, scale * vec.y, scale * vec.z);
        return vec_temp;
    }

    public static Vector3 random(float r)
    {
        float x = r / (float)(Math.random() % 10 );
        float y = r / (float)(Math.random() % 10 );
        float z = r / (float)(Math.random() % 10 );
        return new Vector3(x,y,z).nor();
    }
    public static boolean in_range(Vector3 vec1, Vector3 vec2, Vector3 range)
    {
        return vec1.x + range.x < vec2.x && vec1.x - range.x > vec2.x
                && vec1.y + range.y < vec2.y && vec1.y - range.y > vec2.y
                && vec1.z + range.z < vec2.z && vec1.z - range.z > vec2.z;
    }

    public static Vector3 voodoo_interpolate(Vector3 previous, Vector3 next) {
        double distance = Math.sqrt(Math.pow(previous.cpy().x - next.cpy().x, 2) + Math.pow(previous.cpy().y - next.cpy().y, 2) + Math.pow(previous.cpy().z - next.cpy().z, 2));
        float sharpness = (float) (distance / 30);
        if (sharpness > .4) sharpness /= 3;
        if (sharpness > 1) sharpness = 1;
        //System.out.println(sharpness + " : sharpness value");
        return previous.cpy().interpolate(next.cpy(), sharpness, Interpolation.linear);
    }

}
