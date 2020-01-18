package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class ModelComponent implements Component {

    private Model model_;
    private ModelInstance model_instance_;

    public ModelComponent(){}

    public ModelInstance get_instance()
    {
        return model_instance_;
    }
}
