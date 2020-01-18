package com.barbarian.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.barbarian.game.Game;
import com.barbarian.game.components.CameraComponent;
import com.barbarian.game.components.TransformComponent;
import com.barbarian.game.utils.QuaternionMath;

public class CameraFollowSystem extends IteratingSystem {
    private ComponentMapper<CameraComponent> camera_m_ = ComponentMapper.getFor(CameraComponent.class);
    private ComponentMapper<TransformComponent> transform_m_ = ComponentMapper.getFor(TransformComponent.class);

    /**
     * creates new camera follow system
     */
    public CameraFollowSystem () {
        super(Family.all(CameraComponent.class, TransformComponent.class).get());
    }

    /**
     * @param entity
     * @param deltaTime
     * sets camera to stay behind players viewpoint
     */
    @Override
    public void processEntity (Entity entity, float deltaTime) {
        CameraComponent camera_c = camera_m_.get(entity);
        TransformComponent transform_c = transform_m_.get(entity);

        Game.cam.position.set(transform_c.get_position());
        Game.cam.position.add(camera_c.offset.cpy().mul(QuaternionMath.inverse(transform_c.rotation)));
        Game.cam.update();
    }
}
