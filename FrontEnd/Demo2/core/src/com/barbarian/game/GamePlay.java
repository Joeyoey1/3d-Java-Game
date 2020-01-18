package com.barbarian.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.JsonObject;
import serviceJS.HttpRequestHandler;

import java.util.Random;

public class GamePlay implements Screen {
    private Environment environment;
    private PerspectiveCamera cam;
    private ModelBatch modelBatch;
    private CameraInputController camController;
    private Model model;
    private ModelInstance instance, instance2;
    private boolean is_pressed = false;
    private int amtRenders = 0;

    @Override
    public void show() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(5f, 5f, 5f);
        cam.lookAt(-50,0,-50);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        instance = new ModelInstance(model);
        instance2 = new ModelInstance(model);
        instance.transform.translate(new Vector3(-50f, 0f, -50f));
        instance2.transform.translate(new Vector3(0f, 0f, 0f));
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
    }

    @Override
    public void render(float delta) {
        camController.update();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            if (!is_pressed)
            {
                int rand_color = random_color();
                is_pressed = true;
                instance2.materials.get(0).set(get_color(rand_color));
                JsonObject obj = new JsonObject();
                obj.addProperty("id", 1);
                obj.addProperty("health", rand_color);

                try {
                    HttpRequestHandler.sendPut("players", obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else
            is_pressed = false;

        if (Gdx.input.isKeyPressed(Input.Keys.TAB))
        {
            try {
                int stored_color = HttpRequestHandler.sendGet("players/");
                instance.materials.get(0).set(get_color(stored_color));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instance, environment);
        modelBatch.render(instance2, environment);
        modelBatch.end();
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
        modelBatch.dispose();
        model.dispose();
    }

    public int random_color()
    {
        Random rand = new Random();
        int x = rand.nextInt(5);

        return x;
    }

    public ColorAttribute get_color(int color_id)
    {
        switch(color_id)
        {
            case 0:
                return ColorAttribute.createDiffuse(Color.RED);
            case 1:
                return ColorAttribute.createDiffuse(Color.GREEN);
            case 2:
                return ColorAttribute.createDiffuse(Color.BLUE);
            case 3:
                return ColorAttribute.createDiffuse(Color.PINK);
            default:
                return ColorAttribute.createDiffuse(Color.YELLOW);
        }
    }
}