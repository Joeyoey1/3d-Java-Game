package com.barbarian.game.colliders;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class CircleCollider implements Collider{
    public float radius;
    public Vector3 position;

    public CircleCollider(float radius, Vector3 pos)
    {
        this.radius = radius;
        this.position = pos;
    }

    @Override
    public boolean check_collision(Collider collider) {
        return Collision.checkCollision(this, (CircleCollider)collider);
    }
}
