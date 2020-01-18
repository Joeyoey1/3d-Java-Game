package com.barbarian.game.colliders;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class RectangleCollider implements Collider{
    public Vector3 box;
    public Vector3 position;
    public Quaternion rotation;

    public RectangleCollider(Vector3 box, Vector3 pos, Quaternion rot)
    {
        this.box = box;
        this.position = pos;
        this.rotation = rot;
    }

    @Override
    public boolean check_collision(Collider collider) {
        return Collision.checkCollision(this, (RectangleCollider)collider);
    }
}
