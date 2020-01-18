package com.barbarian.game.commands;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.components.AnimationComponent;
import com.barbarian.game.components.MovementComponent;

public class AnimationCommand implements Command {

    public String animId;
    public int loop;
    private ComponentMapper<AnimationComponent> animation_m_ = ComponentMapper.getFor(AnimationComponent.class);

    public AnimationCommand(String animationId, int loopNum)
    {
        animId = animationId;
        loop = loopNum;
    }

    @Override
    public void execute(Entity entity) {
        AnimationComponent animation_c = animation_m_.get(entity);
        animation_c.controller.setAnimation(animId, loop);
    }

    @Override
    public void undo(Entity entity) {
        AnimationComponent animation_c = animation_m_.get(entity);
        animation_c.controller.setAnimation("Armature|Armature|Armature|Idle|Armature|Idle", -1);
    }
}
