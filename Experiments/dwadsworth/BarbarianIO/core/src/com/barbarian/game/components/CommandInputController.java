package com.barbarian.game.components;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.commands.Command;
import com.barbarian.game.commands.DirectionCommand;


import java.util.HashMap;

public class CommandInputController implements InputProcessor {

    private static final int CENTER_SCREEN = 320;
    private DirectionCommand camera_r_ = new DirectionCommand(new Vector3(-0.05f, 0f, 0f)), camera_l_ = new DirectionCommand(new Vector3(0.05f, 0f, 0f));
    private Entity player_;
    private HashMap<Integer, Command> controller_;

    /**
     * @param player
     * @param controller
     * creates new command controller
     */
    public CommandInputController(Entity player, HashMap<Integer, Command> controller)
    {
        player_ = player;
        controller_ = controller;
    }

    /**
     * @param keycode
     * @return true
     * what happens when key is pressed, depends on key
     */
    @Override
    public boolean keyDown(int keycode) {
        if (controller_.containsKey(keycode))
            controller_.get(keycode).execute(player_);
        return true;
    }

    /**
     * @param keycode
     * @return true
     * undoes what key down does
     */
    @Override
    public boolean keyUp(int keycode) {
        if (controller_.containsKey(keycode))
            controller_.get(keycode).undo(player_);
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

    /**
     * @param screenX
     * @param screenY
     * @return false
     * allows rotation of the player
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
        if(screenX > CENTER_SCREEN){
            camera_r_.execute(player_);
            Gdx.input.setCursorPosition(CENTER_SCREEN, screenY);
        }
        if(screenX < CENTER_SCREEN){
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
