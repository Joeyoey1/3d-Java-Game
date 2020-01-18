package com.barbarian.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.barbarian.game.colliders.Collision;
import com.barbarian.game.components.CollisionComponent;
import com.barbarian.game.components.PlayerComponent;
import com.barbarian.game.components.TransformComponent;
import com.barbarian.game.network.Network;

public class CollisionSystem extends EntitySystem {

    private ComponentMapper<TransformComponent> transform_m_ = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<CollisionComponent> collision_m_ = ComponentMapper.getFor(CollisionComponent.class);
    private ImmutableArray<Entity> player_entities, collision_entities;


    @Override
    public void addedToEngine(Engine engine) {
        collision_entities = engine.getEntitiesFor(Family.all(TransformComponent.class, CollisionComponent.class).get());
        player_entities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }


    @Override
    public void update(float deltaTime)
    {
        CollisionComponent collision1_c, collision2_c;
        TransformComponent transform1_c;

        for (int i = 0; i < player_entities.size(); i++)
        {
            collision1_c = collision_m_.get(player_entities.get(i));
            transform1_c = transform_m_.get(player_entities.get(i));

            for (int j = 0; j < collision_entities.size(); j++) {
                if (player_entities.get(i) != collision_entities.get(j)) {
                    collision2_c = collision_m_.get(collision_entities.get(j));
                    if (Collision.checkCollision(collision1_c.getRectangleCollider(), collision2_c.getRectangleCollider()))
                        transform1_c.backtrack_position();
                }
            }
        }

    }

}
