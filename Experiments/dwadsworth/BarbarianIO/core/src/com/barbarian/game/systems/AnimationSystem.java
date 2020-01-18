package com.barbarian.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.barbarian.game.components.AnimationComponent;
import com.barbarian.game.components.MovementComponent;

public class AnimationSystem extends IteratingSystem {
    private ComponentMapper<AnimationComponent> animation_m_ = ComponentMapper.getFor(AnimationComponent.class);
    private ComponentMapper<MovementComponent> movement_m_ = ComponentMapper.getFor(MovementComponent.class);

    /**
     * creates new animation system
     */
    public AnimationSystem () {super(Family.all(AnimationComponent.class).get());}

    /**
     * @param entity
     * @param deltaTime
     * runs with each update, animates entity
     */
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final AnimationComponent animation_c = animation_m_.get(entity);
        MovementComponent movement_c = movement_m_.get(entity);

        animation_c.controller.setAnimation("Armature|Walk|Armature|Walk", 1, new AnimationController.AnimationListener() {
            @Override
            public void onEnd(AnimationController.AnimationDesc animation) {
                animation_c.controller.queue("Armature|Walk|Armature|Walk",-1,1f,null,0f);
            }

            @Override
            public void onLoop(AnimationController.AnimationDesc animation) {

            }
        });
    }
}

