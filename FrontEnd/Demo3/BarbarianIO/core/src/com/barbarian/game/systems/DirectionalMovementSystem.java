package com.barbarian.game.systems;


import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.components.*;
import com.barbarian.game.models.Player;
import com.barbarian.game.utils.VectorMath;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

public class DirectionalMovementSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> transform_m_ = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<DirectionComponent> direction_m_ = ComponentMapper.getFor(DirectionComponent.class);
    private ComponentMapper<MovementComponent> movement_m_ = ComponentMapper.getFor(MovementComponent.class);

    public DirectionalMovementSystem () {
        super(Family.all(TransformComponent.class, DirectionComponent.class, MovementComponent.class).get());
    }

    @Override
    public void processEntity (Entity entity, float deltaTime) {
        TransformComponent transform = transform_m_.get(entity);
        DirectionComponent direction = direction_m_.get(entity);
        MovementComponent movement = movement_m_.get(entity);

        //transform.rotation = direction.get_direction();

        transform.rotation.slerp(direction.get_direction(), deltaTime * 7);

        Vector3 target = movement.get_velocity().mul(direction.get_direction());
        Vector3 start = transform.get_position();

        Vector3 add = start.cpy().add(target.cpy());


        start.interpolate(add, deltaTime, Interpolation.linear);

//        transform.add_position(VectorMath.scale(start, deltaTime));

//        transform.set_position(start);

        //transform.add_position(VectorMath.scale(movement.get_velocity().mul(direction.get_direction()), deltaTime));
    }
}