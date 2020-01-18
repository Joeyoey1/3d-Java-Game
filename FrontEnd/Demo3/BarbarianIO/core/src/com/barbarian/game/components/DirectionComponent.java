package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class DirectionComponent implements Component {
    private Quaternion direction_;

    public DirectionComponent() {
        this.direction_ = new Quaternion(0, 0, 0, 1);
    }

    public DirectionComponent(Quaternion starting_direction) {
        this.direction_ = starting_direction;
    }

    public DirectionComponent(Vector3 direction) {
        direction_ = new Quaternion().setEulerAnglesRad(direction.x, direction.y, direction.z);
    }

    public Quaternion get_direction() {
        return direction_;
    }

    public void set_rotation_z(float rotation_z) {
        direction_.z = rotation_z;
        direction_.x = rotation_z;
        direction_.y = rotation_z;
        direction_.w = rotation_z;
    }

    public void add_direction(Vector3 n_direction) {
        direction_.mul((new Quaternion()).setEulerAnglesRad(n_direction.x, n_direction.y, n_direction.z));
    }

    public void add_direction(Quaternion n_direction) {
        direction_.mul(n_direction);
    }

    public void setDirection_(Quaternion direction_) {
        this.direction_ = direction_;
    }
}
