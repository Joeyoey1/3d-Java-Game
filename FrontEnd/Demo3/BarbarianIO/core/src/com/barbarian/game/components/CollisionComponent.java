package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.colliders.Collider;
import com.barbarian.game.colliders.RectangleCollider;

public class CollisionComponent implements Component {
    private Collider collider_;

    public CollisionComponent(Collider collider){
        this.collider_ = collider;
    }

    public CollisionComponent(Vector3 box, Vector3 pos, Quaternion rot)
    {
        collider_ = new RectangleCollider(box, pos, rot);
    }

    public RectangleCollider getRectangleCollider(){
        return (RectangleCollider)collider_;
    }

}
