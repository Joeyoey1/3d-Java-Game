package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.commands.Command;
import com.barbarian.game.commands.DirectionCommand;
import com.barbarian.game.commands.ExitGameCommand;
import com.barbarian.game.commands.MoveCommand;

import java.util.HashMap;

public class ControllerComponent implements Component {

    private CommandInputController i_controller_;
    private HashMap<Integer, Command> inputs = new HashMap<Integer, Command>();

    /**
     * @param player
     * sets commands for what each key will do
     */
    public ControllerComponent(Entity player)
    {
        inputs.put(Input.Keys.W, new MoveCommand(new Vector3(0f, 0f, -1.0f)));
        inputs.put(Input.Keys.S, new MoveCommand(new Vector3(0f, 0f, 1.0f)));
        inputs.put(Input.Keys.A, new MoveCommand(new Vector3(-1f, 0f, 0f)));
        inputs.put(Input.Keys.D, new MoveCommand(new Vector3(1f, 0f, 0f)));
        inputs.put(Input.Keys.ESCAPE, new ExitGameCommand());

        i_controller_ = new CommandInputController(player, inputs);
        Gdx.input.setInputProcessor(i_controller_);
    }
}
