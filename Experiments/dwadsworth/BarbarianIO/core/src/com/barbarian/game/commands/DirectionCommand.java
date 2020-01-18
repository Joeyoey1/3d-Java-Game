package com.barbarian.game.commands;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.Game;
import com.barbarian.game.components.CameraComponent;
import com.barbarian.game.components.DirectionComponent;

public class DirectionCommand implements Command
{
    private Quaternion direction_;
    private ComponentMapper<DirectionComponent> direction_m_ = ComponentMapper.getFor(DirectionComponent.class);
    private ComponentMapper<CameraComponent> camera_m_ = ComponentMapper.getFor(CameraComponent.class);
    public DirectionCommand(Vector3 direction) { direction_ = new Quaternion().setEulerAnglesRad(direction.x, direction.y, direction.z);}

    /**
     * @param entity
     * what will be executed with every engine update, changes the rotation of entity
     */
    @Override
    public void execute(Entity entity) {
        DirectionComponent direction_c = direction_m_.get(entity);
        CameraComponent camera_c_ = camera_m_.get(entity);

        direction_c.add_direction(direction_);
        Game.cam.rotate(direction_);
    }

    @Override
    public void undo(Entity entity) {
    }
}
