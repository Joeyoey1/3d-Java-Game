package com.barbarian.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.Game;
import com.barbarian.game.components.CameraComponent;
import com.barbarian.game.components.TransformComponent;
import com.barbarian.game.utils.QuaternionMath;
import com.barbarian.game.utils.VectorMath;

public class CameraFollowSystem extends IteratingSystem {
    private ComponentMapper<CameraComponent> camera_m_ = ComponentMapper.getFor(CameraComponent.class);
    private ComponentMapper<TransformComponent> transform_m_ = ComponentMapper.getFor(TransformComponent.class);
    private Vector3 target;
    private Quaternion rotTarget;
    public final Vector3 up = new Vector3(0, 1, 0);
    public final Vector3 direction = new Vector3(0, 0, -1);
    private final Vector3 tmpVec = new Vector3();


    public CameraFollowSystem() {
        super(Family.all(CameraComponent.class, TransformComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        CameraComponent camera_c = camera_m_.get(entity);
        TransformComponent transform_c = transform_m_.get(entity);

        target = transform_c.get_position().cpy();


        Game.cam.position.interpolate(transform_c.get_position().cpy().add(camera_c.offset.cpy().mul(transform_c.rotation.cpy())), .175f, Interpolation.linear);

        Game.cam.lookAt(target.add(0, 10, 0));

        Game.cam.up.set(Vector3.Y);

        Game.cam.update();
    }
}
