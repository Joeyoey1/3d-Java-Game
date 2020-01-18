package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.commands.*;

import java.lang.reflect.Array;
import java.util.*;

public class ControllerComponent implements Component {

    private CommandInputController i_controller_;
    private HashMap<Integer, List<Command>> inputs = new HashMap<Integer, List<Command>>();

    public ControllerComponent(Entity player)
    {
        Command[] walkCommands = new Command[]{new AnimationCommand("Armature|Armature|Armature|Run|Armature|Run", -1),
                                                new MoveCommand(new Vector3(0f, 0f, 1.0f))};
        inputs.put(Input.Keys.W, new ArrayList<>(Arrays.asList(walkCommands)));
        walkCommands[1] = new MoveCommand(new Vector3(0f, 0f, -1.0f));
        inputs.put(Input.Keys.S, new ArrayList<>(Arrays.asList(walkCommands)));
        walkCommands[1] = new MoveCommand(new Vector3(1.0f, 0f, 0f));
        inputs.put(Input.Keys.A, new ArrayList<>(Arrays.asList(walkCommands)));
        walkCommands[1] = new MoveCommand(new Vector3(-1.0f, 0f, 0f));
        inputs.put(Input.Keys.D, new ArrayList<>(Arrays.asList(walkCommands)));
//        inputs.put(Input.Keys.P, new AnimationCommand("Armature|Armature|Armature|stab|Armature|stab", 1));
//        inputs.put(Input.Keys.M, new AnimationCommand("Armature|Armature|Armature|tempAttackStance|Armature|tempAttackStance", -1));
//        inputs.put(Input.Keys.O, new AnimationCommand("Armature|Armature|Armature|death|Armature|death", 1));
//        inputs.put(Input.Keys.H, new AnimationCommand("Armature|Armature|Armature|hurt|Armature|hurt", -1));
        inputs.put(Input.Keys.SHIFT_LEFT, new ArrayList<>(Arrays.asList(new Command[]{new ProjectileCommand()})));
        inputs.put(Input.Keys.ESCAPE, new ArrayList<Command>(Arrays.asList(new Command[]{new ExitGameCommand()})));

        i_controller_ = new CommandInputController(player, inputs);
        Gdx.input.setInputProcessor(i_controller_);
    }
}
