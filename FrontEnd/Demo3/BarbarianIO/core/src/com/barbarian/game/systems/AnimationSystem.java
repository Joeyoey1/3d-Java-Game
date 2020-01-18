package com.barbarian.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.Game;
import com.barbarian.game.components.AnimationComponent;
import com.barbarian.game.components.InstanceComponent;
import com.barbarian.game.components.MovementComponent;

public class AnimationSystem extends IteratingSystem {
    private ComponentMapper<AnimationComponent> animation_m_ = ComponentMapper.getFor(AnimationComponent.class);
    //private ComponentMapper<InstanceComponent> instance_m_ = ComponentMapper.getFor(InstanceComponent.class);
    private ComponentMapper<MovementComponent> movement_m_ = ComponentMapper.getFor(MovementComponent.class);

    public AnimationSystem () {super(Family.all(AnimationComponent.class, MovementComponent.class).get());}

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //InstanceComponent instance_c = instance_m_.get(entity);
        AnimationComponent animation_c = animation_m_.get(entity);
        MovementComponent movement_c = movement_m_.get(entity);

        animation_c.controller.update(Gdx.graphics.getDeltaTime());
        //animation_c.controller.setAnimation("Armature|Armature|Armature|Run|Armature|Run", -1);
        if(entity != Game.thisP) {
            if(movement_c.get_velocity().epsilonEquals(new Vector3(0,0,0))){
                animation_c.controller.setAnimation("Armature|Armature|Armature|Idle|Armature|Idle", -1);
                //System.out.println("idle");
            }
            else{
                animation_c.controller.setAnimation("Armature|Armature|Armature|Run|Armature|Run", -1);
                //System.out.println("running");
            }
        }
    }
}

