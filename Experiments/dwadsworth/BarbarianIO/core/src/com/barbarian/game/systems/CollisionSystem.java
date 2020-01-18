package com.barbarian.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.barbarian.game.components.CollisionComponent;
import com.barbarian.game.components.PlayerComponent;
import com.barbarian.game.components.TransformComponent;
import com.barbarian.game.utils.CollisionMath;

public class CollisionSystem extends EntitySystem {

    private ComponentMapper<TransformComponent> transform_m_ = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<CollisionComponent> collision_m_ = ComponentMapper.getFor(CollisionComponent.class);
    private ImmutableArray<Entity> player_entities, collision_entities;


    /**
     * @param engine
     * sets what components to add to engine
     */
    @Override
    public void addedToEngine(Engine engine) {
        collision_entities = engine.getEntitiesFor(Family.all(TransformComponent.class, CollisionComponent.class).get());
        player_entities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }


    /**
     * @param deltaTime
     * checks for collision between entites with each update
     */
    @Override
    public void update(float deltaTime)
    {
        CollisionComponent collision1_c, collision2_c;
        TransformComponent transform1_c, transform2_c;

        for (int i = 0; i < player_entities.size(); i++)
        {
            collision1_c = collision_m_.get(player_entities.get(i));
            transform1_c = transform_m_.get(player_entities.get(i));

            for (int j = 0; j < collision_entities.size(); j++) {
                if (player_entities.get(i) != collision_entities.get(j)) {
                    collision2_c = collision_m_.get(collision_entities.get(j));
                    transform2_c = transform_m_.get(collision_entities.get(j));
                    if (CollisionMath.checkCollision(collision1_c.getBox(), collision2_c.getBox(), transform1_c.get_position(), transform2_c.get_position(), transform1_c.rotation, transform2_c.rotation ))
                        transform1_c.backtrack_position();
                }
            }
        }

    }

}
