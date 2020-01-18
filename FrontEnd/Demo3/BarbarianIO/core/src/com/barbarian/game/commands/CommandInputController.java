package com.barbarian.game.commands;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.commands.Command;
import com.barbarian.game.commands.DirectionCommand;


import java.util.HashMap;
import java.util.List;

public class CommandInputController implements InputProcessor {

    private static final int CENTER_SCREEN = 320;
    private DirectionCommand camera_r_ = new DirectionCommand(new Vector3(-0.05f, 0f, 0f)), camera_l_ = new DirectionCommand(new Vector3(0.05f, 0f, 0f));
    private Entity player_;
    private HashMap<Integer, List<Command>> controller_;

    public CommandInputController(Entity player, HashMap<Integer, List<Command>> controller)
    {
        player_ = player;
        controller_ = controller;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (controller_.containsKey(keycode))
            for(int i = 0; i < controller_.get(keycode).size(); i++){
                controller_.get(keycode).get(i).execute(player_);
            }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (controller_.containsKey(keycode))
            for(int i = 0; i < controller_.get(keycode).size(); i++){
                controller_.get(keycode).get(i).undo(player_);
            }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
        if(screenX > CENTER_SCREEN + 10){
            camera_r_.execute(player_);
            Gdx.input.setCursorPosition(CENTER_SCREEN, screenY);
        }
        if(screenX < CENTER_SCREEN - 10){
            camera_l_.execute(player_);
            Gdx.input.setCursorPosition(CENTER_SCREEN, screenY);
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
