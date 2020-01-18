package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public class CollisionComponent implements Component {
    private Vector3 collisionBox;

    /**
     * @param box
     * creates new collsion component
     */
    public CollisionComponent(Vector3 box){
        this.collisionBox = box;
    }

    /**
     * @return
     * returns the collision box size of entity as vector 3
     */
    public Vector3 getBox(){
        return collisionBox;
    }

}
