package com.barbarian.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.gson.JsonObject;
import serviceJS.HttpRequestHandler;

public class LoginScreen implements Screen {
    private Stage stage;
    private MainGame game;
    private TextField txfUsername;
    private TextField txfPassword;

    LoginScreen(MainGame game){
        stage = new Stage();
        this.game = game;
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton btnLogin = new TextButton("Log In", skin);
        btnLogin.setPosition(150, 240);
        btnLogin.setSize(145, 50);
        btnLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y)
            {
                    btnLoginClicked();
            }
        });

        TextButton btnNewAccount = new TextButton("New Account", skin);
        btnNewAccount.setPosition(305, 240);
        btnNewAccount.setSize(145, 50);
        btnNewAccount.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent e, float x, float y){
                createAccountClicked();
            }
        });

        txfUsername = new TextField("", skin);
        txfUsername.setPosition(150, 350);
        txfUsername.setSize(300, 40);

        txfPassword = new TextField("", skin);
        txfPassword.setPosition(150, 300);
        txfPassword.setSize(300, 40);

        stage.addActor(btnLogin);
        stage.addActor(txfUsername);
        stage.addActor(txfPassword);
        stage.addActor(btnNewAccount);
        Gdx.input.setInputProcessor(stage);
    }

    private void btnLoginClicked() {
        //TODO: resolve login button issues
        game.setScreen(new GamePlay());
    }

    private void createAccountClicked() {
        game.setScreen(new NewAccountScreen(game));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
