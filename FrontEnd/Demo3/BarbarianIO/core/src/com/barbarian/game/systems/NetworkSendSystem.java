package com.barbarian.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.components.DirectionComponent;
import com.barbarian.game.components.MovementComponent;
import com.barbarian.game.components.PlayerComponent;
import com.barbarian.game.components.TransformComponent;
import com.barbarian.game.models.Location;
import com.barbarian.game.network.Network;
import com.barbarian.game.utils.Ticker;

public class NetworkSendSystem extends IteratingSystem {

    private ComponentMapper<PlayerComponent> player_m_ = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<MovementComponent> movement_m_ = ComponentMapper.getFor(MovementComponent.class);
    private ComponentMapper<TransformComponent> transform_m_ = ComponentMapper.getFor(TransformComponent.class);

    private Network network_;
    private Ticker ticker = new Ticker();

    public NetworkSendSystem(Network network) {
        super(Family.all(PlayerComponent.class, MovementComponent.class, TransformComponent.class).get());
        network_ = network;
        ticker.start();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (ticker.get_ticks(deltaTime) > 1) {
            PlayerComponent player_c = player_m_.get(entity);
            TransformComponent transform_c = transform_m_.get(entity);
            MovementComponent movement_c = movement_m_.get(entity);



            Location player_l = player_c.getLocation();
            Vector3 transform_p = transform_c.get_position();

            player_l.setX(transform_p.x);
            player_l.setY(transform_p.y);
            player_l.setZ(transform_p.z);
            //System.out.println(yaw + " Yaw Sent");



            //System.out.println("Sent:\n" + direction_c.get_direction().x + " x\n" + direction_c.get_direction().y + " y\n" + direction_c.get_direction().z + " z\n" + direction_c.get_direction().w + " w\n");
            //System.out.println(transform_c.rotation.getYaw() + " Yaw, " + transform_c.rotation.getPitch() + " Pitch, " + transform_c.rotation.getRoll() + " Roll");

            //direction_c.get_direction().z;

            Vector3 player_v = movement_c.get_velocity();
            player_l.setVelX(player_v.x);
            player_l.setVelZ(player_v.z);

            player_c.getEntityPlayer().setRotation(transform_c.rotation.cpy());
            System.out.println(player_c.getEntityPlayer().getId() + ": " + player_c.getEntityPlayer().getRotation().toString());
            player_c.getEntityPlayer().setLocation(player_l);

            network_.sendPlayerUpdate(player_c.getEntityPlayer());
            ticker.start();
        }

    }
}
