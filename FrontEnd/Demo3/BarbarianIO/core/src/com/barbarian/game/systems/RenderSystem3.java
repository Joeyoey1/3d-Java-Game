package com.barbarian.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.barbarian.game.components.InstanceComponent;
import com.barbarian.game.components.TransformComponent;

public class RenderSystem3 extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private ModelBatch batch;
	private Environment environment;
	private PerspectiveCamera camera;
	private AnimationController controller;

	private ComponentMapper<TransformComponent> transform_m_ = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<InstanceComponent> instance_m_ = ComponentMapper.getFor(InstanceComponent.class);

	public RenderSystem3 (PerspectiveCamera camera, Environment environment) {
		batch = new ModelBatch();
		this.environment = environment;
		this.camera = camera;
	}

	@Override
	public void addedToEngine (Engine engine) {
		entities = engine.getEntitiesFor(Family.all(TransformComponent.class, InstanceComponent.class).get());
	}

	@Override
	public void removedFromEngine (Engine engine) {
		entities = engine.getEntitiesFor(Family.all(TransformComponent.class, InstanceComponent.class).get());
	}

	@Override
	public void update (float deltaTime) {
		TransformComponent transform_c;
		InstanceComponent instance_c;
		ModelInstance instance;
		AnimationController controller;

		camera.update();

		batch.begin(camera);

		for (int i = 0; i < entities.size(); ++i) {
			Entity e = entities.get(i);

			transform_c = transform_m_.get(e);
			instance_c = instance_m_.get(e);
			instance = instance_c.instance;

			instance.transform = transform_c.get_transform();
//			controller = new AnimationController(instance);
//			controller.setAnimation(instance.animations.first().id, -1);
//
//			controller.update(Gdx.graphics.getDeltaTime());
			batch.render(instance, environment);
		}

		batch.end();
	}

}
