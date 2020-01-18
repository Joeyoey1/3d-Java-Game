package com.barbarian.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.barbarian.game.Game;
import com.barbarian.game.components.*;
import com.barbarian.game.models.Location;
import com.barbarian.game.models.Player;
import com.barbarian.game.network.Network;
import com.barbarian.game.utils.VectorMath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class NetworkGetSystem extends EntitySystem {

    private ComponentMapper<TransformComponent> transform_m_ = ComponentMapper.getFor(TransformComponent.class);
    private ComponentMapper<MovementComponent> movement_m_ = ComponentMapper.getFor(MovementComponent.class);
    private ComponentMapper<DirectionComponent> direction_m_ = ComponentMapper.getFor(DirectionComponent.class);


    private Network network_;

    public NetworkGetSystem(Network network) {
        network_ = network;

    }

    @Override
    public void update(float deltaTime) {
        if (network_.is_fresh())
        {
            for (Player player : network_.get_updated_players()) {
                if(player == null){}
                else if(Game.players.get(player.getId()) == null){
                    Random rand = new Random(player.getId());
                    Entity player_n = new Entity();
                    float x = rand.nextFloat() % 20;
                    float z = rand.nextFloat() % 20;
                    TransformComponent player_trans = new TransformComponent(new Vector3(x, 0f, z), new Vector3(0,0,0), 0.07f);
                    player_n.add(player_trans);
                    InstanceComponent playerInstanceComponent = new InstanceComponent(Game.player_mo);
                    playerInstanceComponent.instance.materials.get(0).clear();
                    playerInstanceComponent.instance.materials.get(0).set(ColorAttribute.createDiffuse(Color.BLUE));

                    player_n.add(playerInstanceComponent);
                    player_n.add(new MovementComponent(30f));
                    player_n.add(new DirectionComponent());
                    player_n.add(new AnimationComponent(new AnimationController(playerInstanceComponent.instance)));
                    Game.players.put(player.getId(), player_n);
                    getEngine().addEntity(player_n);
                }
                else if(player.getId() != Game.thisID){
                    TransformComponent transform = transform_m_.get(Game.players.get(player.getId()));
                    MovementComponent movement_c = movement_m_.get(Game.players.get(player.getId()));
                    DirectionComponent direction_c = direction_m_.get(Game.players.get(player.getId()));

                    Location location = player.getLocation();
                    Vector3 target = new Vector3(location.getX(), location.getY(), location.getZ());

                    transform.set_position_out_of_range(VectorMath.voodoo_interpolate(transform.get_position(), target), new Vector3(10,10,10));

                    direction_c.setDirection_(player.getRotation().cpy());

                    movement_c.set_velocity(new Vector3(location.getVelX(), 0f, location.getVelZ()));
                }
            }
            for (Iterator<Integer> i = Game.players.keySet().iterator(); i.hasNext();) {
                int id = i.next();
                try {
                    if (!network_.get_updated_players().stream().map(Player::getId).collect(Collectors.toList()).contains(id)) {
                        getEngine().removeEntity(Game.players.get(id));
                        i.remove();
                    }
                } catch (NullPointerException ignored) {
                }
            }
            network_.set_stale();
        }
    }




}
