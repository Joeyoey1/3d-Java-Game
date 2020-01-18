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

    public TransformComponent()
    {
        this.position_ = Vector3.Zero.cpy();
        this.rotation = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
        this.scale = 1.0f;
        this.prev_position_ = Vector3.Zero.cpy();
    }
    public TransformComponent(TransformComponent tc)
    {
        this.position_ = tc.position_;
        this.rotation = tc.rotation;
        this.scale = tc.scale;
        this.prev_position_ = tc.prev_position_;
    }

    public TransformComponent(Vector3 position)
    {
        this.position_ = position;
        this.rotation = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
        this.scale = 1.0f;
        this.prev_position_ = Vector3.Zero.cpy();
    }

    public TransformComponent(Vector3 position, Vector3 rotation, float scale)
    {
        this.position_ = position;
        this.rotation = (new Quaternion()).setEulerAngles(rotation.z, rotation.y, rotation.x);
        this.scale = scale;
        this.prev_position_ = position;
    }

    public Matrix4 get_transform()
    {
        return new Matrix4(position_, rotation, new Vector3(scale, scale, scale));
    }

    public void add_position(Vector3 position_a)
    {
        prev_position_ = position_.cpy();
        VectorMath.add(position_, position_a);
    }

    public void set_position(Vector3 position_s)
    {
        prev_position_ = position_.cpy();
        position_ = position_s;
    }

    public void set_position_out_of_range(Vector3 position_s, Vector3 range)
    {
        if (!VectorMath.in_range(position_, position_s, range))
            set_position(position_s);
    }

    public void setRotation(Quaternion quaternion) {
        this.rotation = quaternion;
    }

    public void setCustTest(float floa) {
        this.rotation.w = floa;
        this.rotation.x = floa;
        this.rotation.y = floa;
        this.rotation.z = floa;
    }

    public Vector3 get_position() {return position_;}
    public void backtrack_position() {
        position_ = prev_position_;
    }
}
