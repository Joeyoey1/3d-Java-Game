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

package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.utils.VectorMath;

public class MovementComponent implements Component {

	private Vector3 velocity_;
	private float speed_;

	public Vector3 get_velocity() { return VectorMath.scale(velocity_.cpy().nor(), speed_); }

	public void set_velocity(Vector3 n_vel)
	{
		velocity_ = n_vel;
	}
	public void add_velocity(Vector3 a_vel) { VectorMath.add(velocity_, a_vel); }
	public void subtract_velocity(Vector3 s_vel) {VectorMath.subtract(velocity_, s_vel);}

	public MovementComponent(float speed)
	{
		this.speed_ = speed;
		velocity_ = Vector3.Zero.cpy();
	}

	public MovementComponent(float speed, Vector3 velocity)
	{
		this.speed_ = speed;
		this.velocity_ = velocity;
	}
}
