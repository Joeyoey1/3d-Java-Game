package com.barbarian.game.commands;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.components.MovementComponent;

public class MoveCommand implements Command {

    private Vector3 velocity_;
    private ComponentMapper<MovementComponent> movement_m_ = ComponentMapper.getFor(MovementComponent.class);

    public MoveCommand(Vector3 velocity)
    {
        velocity_ = velocity;
    }

    @Override
    public void execute(Entity entity) {
        MovementComponent movement_c = movement_m_.get(entity);
        movement_c.add_velocity(velocity_);
    }

    @Override
    public void undo(Entity entity) {
        MovementComponent movement_c = movement_m_.get(entity);
        movement_c.subtract_velocity(velocity_);
    }
}
