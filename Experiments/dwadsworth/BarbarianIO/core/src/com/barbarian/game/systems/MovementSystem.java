/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.barbarian.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.barbarian.game.components.MovementComponent;
import com.barbarian.game.components.PositionComponent;
import com.barbarian.game.components.TransformComponent;
import com.barbarian.game.utils.VectorMath;

public class MovementSystem extends IteratingSystem {
	private ComponentMapper<PositionComponent> position_m_ = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<MovementComponent> movement_m_ = ComponentMapper.getFor(MovementComponent.class);

	/**
	 * creates new movement system
	 */
	public MovementSystem () {
		super(Family.all(PositionComponent.class, MovementComponent.class).get());
	}

	/**
	 * @param entity
	 * @param deltaTime
	 * sets position for entity
	 */
	@Override
	public void processEntity (Entity entity, float deltaTime) {
		PositionComponent position_c = position_m_.get(entity);
		MovementComponent movement_c = movement_m_.get(entity);

		VectorMath.add(position_c.position, VectorMath.scale(movement_c.get_velocity(), deltaTime));
	}
}
