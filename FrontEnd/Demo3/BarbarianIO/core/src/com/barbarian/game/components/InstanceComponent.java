package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public class InstanceComponent implements Component {

    public ModelInstance instance;

    public InstanceComponent(Model model){ instance = new ModelInstance(model); }
}
