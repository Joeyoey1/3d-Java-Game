package com.barbarian.game.systems;

import com.badlogic.ashley.systems.IteratingSystem;
import com.barbarian.game.components.ControllerComponent;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;

public class ControllerSystem extends IteratingSystem {

    private ComponentMapper<ControllerComponent> cm = ComponentMapper.getFor(ControllerComponent.class);

    /**
     * creates new controller system
     */
    public ControllerSystem () {
        super(Family.all(ControllerComponent.class).get());
    }

    /**
     * @param entity
     * @param deltaTime
     * gets controller input for entity
     */
    @Override
    public void processEntity (Entity entity, float deltaTime) {
        ControllerComponent controller = cm.get(entity);

    }


}
