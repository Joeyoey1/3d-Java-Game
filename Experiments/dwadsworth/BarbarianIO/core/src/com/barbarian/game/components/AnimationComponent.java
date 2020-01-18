package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class AnimationComponent implements Component {
    public AnimationController controller;

    /**
     * @param controller
     * creates new animation component
     */
    public AnimationComponent(AnimationController controller){
        this.controller = controller;
    }
}
