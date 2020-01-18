package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class AnimationComponent implements Component {
    public AnimationController controller;

    public AnimationComponent(AnimationController controller){
        this.controller = controller;
    }
}
