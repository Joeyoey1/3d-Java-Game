package com.barbarian.game.systems;


import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.barbarian.game.components.*;
import com.barbarian.game.models.Player;
import com.barbarian.game.utils.CollisionMath;
import com.barbarian.game.utils.VectorMath;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

public class DirectionalMovementSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> transform_m_ = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<DirectionComponent> direction_m_ = ComponentMapper.getFor(DirectionComponent.class);
    private ComponentMapper<MovementComponent> movement_m_ = ComponentMapper.getFor(MovementComponent.class);

    /**
     * creates new directional movement system
     */
    public DirectionalMovementSystem () {
        super(Family.all(TransformComponent.class, DirectionComponent.class, MovementComponent.class).get());
    }

    /**
     * @param entity
     * @param deltaTime
     * sets position and rotation for entity
     */
    @Override
    public void processEntity (Entity entity, float deltaTime) {
        TransformComponent transform = transform_m_.get(entity);
        DirectionComponent direction = direction_m_.get(entity);
        MovementComponent movement = movement_m_.get(entity);

        transform.rotation = direction.get_direction();

        transform.add_position(VectorMath.scale(movement.get_velocity().mul(direction.get_direction()), deltaTime));
    }
}