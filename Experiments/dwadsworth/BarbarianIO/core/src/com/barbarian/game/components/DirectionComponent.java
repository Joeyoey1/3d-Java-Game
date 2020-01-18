package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class DirectionComponent implements Component {
    private Quaternion direction_;

    /**
     * creates new direction quarternion
     */
    public DirectionComponent(){ this.direction_ = new Quaternion(0,0,0,1); }

    /**
     * @return this quaternion
     */
    public Quaternion get_direction() { return direction_; }

    /**
     * @param rotation_z
     */
    public void set_rotation_z(float rotation_z) {direction_.z = rotation_z;}

    /**
     * @param n_direction
     * change direction
     */
    public void add_direction(Vector3 n_direction) {
         direction_.mul((new Quaternion()).setEulerAnglesRad(n_direction.x, n_direction.y, n_direction.z));
    }

    /**
     * @param n_direction
     * change direction
     */
    public void add_direction(Quaternion n_direction)
    {
        direction_.mul(n_direction);
    }

}
