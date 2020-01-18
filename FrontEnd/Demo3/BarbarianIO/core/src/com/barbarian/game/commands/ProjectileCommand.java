package com.barbarian.game.commands;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.Game;
import com.barbarian.game.colliders.CircleCollider;
import com.barbarian.game.colliders.RectangleCollider;
import com.barbarian.game.components.*;
import com.barbarian.game.utils.QuaternionMath;

public class ProjectileCommand implements Command {
    private Vector3 velocity_;
    private ComponentMapper<TransformComponent> transform_m_ = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<DirectionComponent> direction_m_ = ComponentMapper.getFor(DirectionComponent.class);

    public ProjectileCommand()
    {
    }

    @Override
    public void execute(Entity entity) {
        TransformComponent transform_c = transform_m_.get(entity);
        DirectionComponent direction_c = direction_m_.get(entity);
        Entity projectile = Game.engine.createEntity();
        TransformComponent temp_transform = new TransformComponent(transform_c.get_position().cpy());
        temp_transform.add_position(new Vector3(0,0,3.2f).cpy().mul(QuaternionMath.inverse(transform_c.rotation)));
        temp_transform.set_position(new Vector3(temp_transform.get_position().x, 9f, temp_transform.get_position().z));
        temp_transform.scale = 1;
        projectile.add(temp_transform);
        projectile.add(new InstanceComponent(Game.proj_mo));
        projectile.add(new AttackComponent());
        projectile.add(new MovementComponent(75f, Vector3.Z.cpy().mul(direction_c.get_direction())));
        projectile.add(new CollisionComponent(new RectangleCollider(new Vector3(1,1,1), temp_transform.get_position(), temp_transform.rotation)));
        Game.engine.addEntity(projectile);
    }
        @Override
    public void undo(Entity entity) { }
}
