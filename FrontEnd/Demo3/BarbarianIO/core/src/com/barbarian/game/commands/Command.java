package com.barbarian.game.commands;

import com.badlogic.ashley.core.Entity;

public interface Command {
    void execute(Entity entity);
    void undo(Entity entity);
}
