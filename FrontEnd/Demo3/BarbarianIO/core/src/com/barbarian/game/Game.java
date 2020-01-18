package com.barbarian.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.components.*;
import com.barbarian.game.models.Player;
import com.barbarian.game.network.ChatNetwork;
import com.barbarian.game.network.Network;
import com.barbarian.game.systems.*;

import java.util.HashMap;
import java.util.Random;

public class Game implements Screen {
	private Environment environment;
	public static PerspectiveCamera cam;
	public static Model player_mo, arena_mo_, walls_mo, proj_mo;
	public static PooledEngine engine;
	private AssetManager assets;
    public static HashMap<Integer, Entity> players = new HashMap<>();
    public static int thisID;
	public static Entity thisP;
    private Network network_;
    private ChatNetwork because_david_needed_this_for_mokito;
    private Player player;

    public Game(Player player) {
		this.player = player;
		thisID = player.getId();
	}

	@Override
	public void show() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(0f, 10f, 5f);
		cam.lookAt(0,0,-50);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		network_ = new Network();
		because_david_needed_this_for_mokito = new ChatNetwork();

		ModelBuilder modelBuilder = new ModelBuilder();

		proj_mo = modelBuilder.createSphere(1f, 1f, 1f, 10, 10,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

//
//		walls_mo = modelBuilder.createBox(60f, 20f, 60f,
//				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
//				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		Material playerMat = new Material(ColorAttribute.createDiffuse(Color.BLUE));

		engine = new PooledEngine();

		ModelLoader ml = new ObjLoader();

		arena_mo_ = ml.loadModel(Gdx.files.internal("../BarbarianIO/core/assets/colosseum.obj"));
		//player_mo = ml.loadModel(Gdx.files.internal("../BarbarianIO/core/assets/Fighter.obj"));
		assets = new AssetManager();
		assets.load("../BarbarianIO/core/Assets/Fighter.g3db", Model.class);
		assets.finishLoading();
		player_mo = assets.get("../BarbarianIO/core/Assets/Fighter.g3db");
		//player_mo.nodes.get(0).parts.get(0).material.set(ColorAttribute.createDiffuse(Color.BLUE));

		Gdx.input.setCursorCatched(true);
		Entity map = engine.createEntity();
		map.add(new TransformComponent(new Vector3(-70,-57,-50), new Vector3(0,0,0), 45));
		map.add(new InstanceComponent(arena_mo_));
		engine.addEntity(map);

		/* this users player character*/
		InstanceComponent playerInstanceComponent = new InstanceComponent(player_mo);
		playerInstanceComponent.instance.materials.get(0).clear();
		playerInstanceComponent.instance.materials.get(0).set(ColorAttribute.createDiffuse(Color.BLUE));
		thisP = engine.createEntity();
		TransformComponent player_trans = new TransformComponent(new Vector3(0f,0f,-100f), new Vector3(0, 0,0), 0.07f);

		thisP.add(player_trans);
		thisP.add(playerInstanceComponent);
		thisP.add(new MovementComponent(30f));
		thisP.add(new DirectionComponent(new Quaternion().setEulerAngles(180,0,0)));
		thisP.add(new ControllerComponent(thisP));
		thisP.add(new CameraComponent(new Vector3(0f, 13f, -10f)));
		thisP.add(new CollisionComponent(new Vector3(5f, 10f, 5f), player_trans.get_position(), player_trans.rotation));
		thisP.add(new PlayerComponent(this.player));
		thisP.add(new AnimationComponent(new AnimationController(playerInstanceComponent.instance)));
		thisP.add(new ChatComponent());
		engine.addEntity(thisP);
		players.put(thisID, thisP);


		TransformComponent dummy_trans = new TransformComponent(new Vector3(0f,0f,0f), new Vector3(0, 0,0), 0.07f);
		Entity dummyP = engine.createEntity();
		dummyP.add(dummy_trans);
		dummyP.add(playerInstanceComponent);
		dummyP.add(new CollisionComponent(new Vector3(5f, 10f, 5f), dummy_trans.get_position(), dummy_trans.rotation));
		dummyP.add(new PlayerComponent(this.player));
		engine.addEntity(dummyP);

		engine.addSystem(new DirectionalMovementSystem());
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new RenderSystem3(cam, environment));
		engine.addSystem(new RenderSystem2());
		engine.addSystem(new CameraFollowSystem());
		engine.addSystem(new NetworkSendSystem(network_));
		engine.addSystem(new NetworkGetSystem(network_));
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new ChatSystem(because_david_needed_this_for_mokito));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new AttackSystem(network_));

		Entity chatBox = new Entity();
		chatBox.add(new PositionComponent(new Vector3(0,0,0)));
		chatBox.add(new VisualComponent());
		engine.addEntity(chatBox);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		engine.update(Gdx.graphics.getDeltaTime());
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
	public void dispose () {
		player_mo.dispose();
		arena_mo_.dispose();
        walls_mo.dispose();
        proj_mo.dispose();
		engine.clearPools();

	}
}
