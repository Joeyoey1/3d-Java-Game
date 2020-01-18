package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class InstanceComponent implements Component {

    public ModelInstance instance;

    /**
     * @param model
     * creates new model instance
     */
    public InstanceComponent(Model model){ instance = new ModelInstance(model); }
    public InstanceComponent(ModelInstance instance){this.instance = instance;}
}
