package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.utils.QuaternionMath;
import com.barbarian.game.utils.VectorMath;

public class TransformComponent implements Component {
    private Vector3 position_, prev_position_;
    public Quaternion rotation;
    public float scale;

    /**
     * creates basic transsform component
     */
    public TransformComponent()
    {
        this.position_ = Vector3.Zero;
        this.rotation = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
        this.scale = 1.0f;
        this.prev_position_ = Vector3.Zero;
    }

    /**
     * @param position
     * sets specific transform component with position
     */
    public TransformComponent(Vector3 position)
    {
        this.position_ = position;
        this.rotation = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
        this.scale = 1.0f;
        this.prev_position_ = Vector3.Zero;
    }

    /**
     * @param position
     * @param rotation
     * @param scale
     * sets transform component wit position, rotation, and scale
     */
    public TransformComponent(Vector3 position, Vector3 rotation, float scale)
    {
        this.position_ = position;
        this.rotation = (new Quaternion()).setEulerAngles(rotation.z, rotation.y, rotation.x);
        this.scale = scale;
        this.prev_position_ = position;
    }

    /**
     * @return transfrom
     */
    public Matrix4 get_transform()
    {
        return new Matrix4(position_, rotation, new Vector3(scale, scale, scale));
    }

    /**
     * @param position_a
     * add to current position
     */
    public void add_position(Vector3 position_a)
    {
        prev_position_ = position_.cpy();
        VectorMath.add(position_, position_a);
    }

    /**
     * @param position_s
     * sets current position
     */
    public void set_position(Vector3 position_s)
    {
        prev_position_ = position_.cpy();
        position_ = position_s;
    }

    /**
     * @return position
     */
    public Vector3 get_position() {return position_;}

    /**
     * sets enetity to previous position
     */
    public void backtrack_position() {
        position_ = prev_position_;
    }
}
