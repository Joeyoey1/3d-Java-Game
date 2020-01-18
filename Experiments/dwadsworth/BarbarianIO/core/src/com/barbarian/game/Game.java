package com.barbarian.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.UBJsonReader;
import com.barbarian.game.components.*;
import com.barbarian.game.systems.*;
import java.util.HashMap;

public class Game implements Screen {
	private Environment environment;
	public static PerspectiveCamera cam;
	public static Model player_mo, arena_mo_, walls_mo;
	private PooledEngine engine;
    public static HashMap<Integer, Entity> players = new HashMap<>();
    public static int thisID = 12346;


	/**
	 * Generates the environment, camera, engine, and entities needed to run the game
	 */
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

		engine = new PooledEngine();

		ModelLoader ml = new ObjLoader();

		arena_mo_ = ml.loadModel(Gdx.files.internal("../BarbarianIO/core/assets/colosseum.obj"));
		player_mo = ml.loadModel(Gdx.files.internal("../BarbarianIO/core/assets/Fighter.obj"));

		Gdx.input.setCursorCatched(true);
		Entity map = engine.createEntity();
		map.add(new TransformComponent(new Vector3(-70,-57,-50), new Vector3(0,0,0), 45));
		map.add(new InstanceComponent(arena_mo_));
		engine.addEntity(map);
//
//		Entity walls = engine.createEntity();
//		walls.add(new TransformComponent(new Vector3(70,0,-175f), new Vector3(0f, 0f, 10f), 1));
//		walls.add(new CollisionComponent(new Vector3(60f, 20f, 60f)));
//		walls.add(new InstanceComponent(walls_mo));
//		engine.addEntity(walls);

		/* enemy users player characters
		 * change so that maps to player id*/

//		for(int i = 1; i < 4/*number of players from server*/; i++){
//            players.put(i, engine.createEntity());
//            players.get(i).add(new TransformComponent(new Vector3((-20f + (20 * (i-1))), 0, -50), new Vector3(0, 0, 0), 5));
//            players.get(i).add(new InstanceComponent(player_mo_));
//            players.get(i).add(new CollisionComponent(new Vector3(2f, 10f, 1f), new Vector3((-20f + (20 * (i-1))), 0, -50)));
//            engine.addEntity(players.get(i));
//        }

//		/* this users player character*/
//		Entity thisP = engine.createEntity();
//		thisP.add(new TransformComponent(new Vector3(0f,0f,-100f), new Vector3(0,0,0), 5));
//		thisP.add(new InstanceComponent(player_mo));


		UBJsonReader jsonReader = new UBJsonReader();

		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		// Now load the model by name
		// Note, the model (g3db file ) and textures need to be added to the assets folder of the Android proj
//		player_mo = modelLoader.loadModel(Gdx.files.getFileHandle("data/Barbarian.g3db", Files.FileType.Internal));

		Entity thisP = engine.createEntity();
		thisP.add(new TransformComponent(new Vector3(0f,0f,-20f), new Vector3(0,0,0), 5));
		thisP.add(new InstanceComponent(player_mo));
		thisP.add(new MovementComponent(30f));
		thisP.add(new DirectionComponent());
		thisP.add(new ControllerComponent(thisP));
		thisP.add(new CameraComponent(new Vector3(0f, 10f, 5f)));
		thisP.add(new CollisionComponent(new Vector3(5f, 10f, 5f)));
//		thisP.add(new AnimationComponent(player_inst));
		thisP.add(new PlayerComponent());
		engine.addEntity(thisP);
		players.put(thisID, thisP);

		Entity wall = engine.createEntity();
		wall.add(new InstanceComponent(player_mo));
		wall.add(new TransformComponent(new Vector3(0,0,-40), new Vector3(0, 180, 90), 5));
		wall.add(new CollisionComponent(new Vector3(30, 10, 2)));
		engine.addEntity(wall);

		engine.addSystem(new DirectionalMovementSystem());
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new RenderSystem3(cam, environment));
		engine.addSystem(new CameraFollowSystem());
		engine.addSystem(new NetworkSystem());
	}

	/**
	 * @param delta
	 * renders the game screen
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		engine.update(Gdx.graphics.getDeltaTime());
	}
	@Override
	public void resize(int width, int height) {

	}

	/**
	 * pauses the game
	 */
	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	/**
	 * dispose the models created at game end to prevent memrory leak
	 */
	@Override
	public void dispose () {
		player_mo.dispose();
		arena_mo_.dispose();
		engine.clearPools();
	}
}
