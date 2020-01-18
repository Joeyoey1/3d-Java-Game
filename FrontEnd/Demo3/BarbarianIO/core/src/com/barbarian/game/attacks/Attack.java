package com.barbarian.game.attacks;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.colliders.Collider;
import com.barbarian.game.colliders.RectangleCollider;
import com.barbarian.game.components.TransformComponent;

public class Attack {
    public static Attack get_attack(int attack_id, Entity player)
    {
        Attack attack = new Attack();
        TransformComponent transform_c = player.getComponent(TransformComponent.class);
        switch (attack_id)
        {
            case 0:
                attack.animation_id = 2;
                attack.collider = new RectangleCollider(new Vector3(5, 5, 10), transform_c.get_position(), transform_c.rotation);
                attack.hit_stun = 30;
                attack.damage = 9f;
                attack.attack_stun = 20;
                attack.attacking_player = player;
                break;
            default:
                attack.animation_id = 2;
                attack.collider = new RectangleCollider(new Vector3(5, 5, 10), transform_c.get_position(), transform_c.rotation);
                attack.hit_stun = 30;
                attack.damage = 9f;
                attack.attack_stun = 21;
                attack.attacking_player = player;
                break;
        }
        return attack;
    }

    public int animation_id;
    public Collider collider;
    public Entity attacking_player;
    public int hit_stun;
    public float damage;
    public int attack_stun;
}
