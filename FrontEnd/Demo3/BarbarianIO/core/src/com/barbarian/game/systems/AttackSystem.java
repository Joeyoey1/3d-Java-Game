package com.barbarian.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.barbarian.game.Game;
import com.barbarian.game.attacks.Attack;
import com.barbarian.game.colliders.Collision;
import com.barbarian.game.components.*;
import com.barbarian.game.network.Network;

public class AttackSystem extends EntitySystem {
    private ComponentMapper<AttackComponent> attack_m_ = ComponentMapper.getFor(AttackComponent.class);
    private ComponentMapper<CollisionComponent> collision_m_ = ComponentMapper.getFor(CollisionComponent.class);
    private ComponentMapper<TransformComponent> transform_m_ = ComponentMapper.getFor(TransformComponent.class);
    private ImmutableArray<Entity> player_entities, attack_entities;

    private Network network_;

    public AttackSystem (Network network)
    {
        network_ = network;
    }




    @Override
    public void addedToEngine(Engine engine) {
        attack_entities = engine.getEntitiesFor(Family.all(AttackComponent.class, CollisionComponent.class).get());
        player_entities = engine.getEntitiesFor(Family.all(PlayerComponent.class, CollisionComponent.class).get());
    }


    @Override
    public void update(float deltaTime)
    {
        CollisionComponent collision1_c, collision2_c;
        TransformComponent transform1_c;
        AttackComponent attack1_c;

        for (int i = 0; i < attack_entities.size(); i++)
        {
            collision1_c = collision_m_.get(attack_entities.get(i));
            transform1_c = transform_m_.get(attack_entities.get(i));
            attack1_c = attack_m_.get(attack_entities.get(i));

            for (int j = 0; j < player_entities.size(); j++) {
                if (attack_entities.get(i) != player_entities.get(j)) {
                    collision2_c = collision_m_.get(player_entities.get(j));
                    if (Collision.checkCollision(collision1_c.getRectangleCollider(), collision2_c.getRectangleCollider())){
                        System.out.println("hit detected");
                        //network_.sendAttackUpdate(attack1_c.getAttack());
                        getEngine().removeEntity(attack_entities.get(i));
                    }
                }
            }
        }

    }
}
