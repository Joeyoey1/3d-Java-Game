package com.barbarian.game.commands;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;

public class ExitGameCommand implements Command {
    /**
     * @param entity exites the game
     */
    @Override
    public void execute(Entity entity) {
        Gdx.app.exit();
    }

    @Override
    public void undo(Entity entity) {

    }
}
