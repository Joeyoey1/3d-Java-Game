package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public class PositionComponent implements Component {
    public Vector3 position;

    /**
     * @param start_pos
     * creates new position of entity
     */
    public PositionComponent(Vector3 start_pos) { this.position = start_pos; }
}
