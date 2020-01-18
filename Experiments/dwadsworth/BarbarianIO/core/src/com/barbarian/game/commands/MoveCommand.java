package com.barbarian.game.commands;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.components.AnimationComponent;
import com.barbarian.game.components.MovementComponent;

public class MoveCommand implements Command {

    private Vector3 velocity_;
    private ComponentMapper<MovementComponent> movement_m_ = ComponentMapper.getFor(MovementComponent.class);

    /**
     * @param velocity
     * creates new move command velocity
     */
    public MoveCommand(Vector3 velocity)
    {
        velocity_ = velocity;
    }

    /**
     * @param entity
     * oves the player in direction
     */
    @Override
    public void execute(Entity entity) {
        MovementComponent movement_c = movement_m_.get(entity);
        movement_c.add_velocity(velocity_);
    }

    /**
     * @param entity
     * stops entity from moving
     */
    @Override
    public void undo(Entity entity) {
        MovementComponent movement_c = movement_m_.get(entity);
        movement_c.subtract_velocity(velocity_);
    }
}
