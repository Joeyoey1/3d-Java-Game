package com.barbarian.game;

import com.badlogic.gdx.Game;

//import javafx.scene.input.KeyCode;

public class MainGame extends Game {
	/**
	 * creates the game
	 */
	@Override
	public void create () {
		setScreen(new LoginScreen(this));
	}
}
