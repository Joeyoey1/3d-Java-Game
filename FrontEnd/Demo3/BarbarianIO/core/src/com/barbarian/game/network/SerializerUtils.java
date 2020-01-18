package com.barbarian.game.network;


import com.badlogic.gdx.math.Quaternion;
import com.barbarian.game.models.Location;
import com.barbarian.game.models.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializerUtils {

    /**
     * This converts a location to a stream
     * @param location
     * @param stream
     * @throws IOException
     */
    public static void writeLocation(Location location, ObjectOutputStream stream) throws IOException {
        stream.writeFloat(location.getX());
        stream.writeFloat(location.getY());
        stream.writeFloat(location.getZ());
        stream.writeFloat(location.getW());
        stream.writeFloat(location.getVelX());
        stream.writeFloat(location.getVelZ());
    }

    /**
     * This converts the stream to a location.
     * @param stream
     * @return
     * @throws IOException
     */
    public static Location readLocation(ObjectInputStream stream) throws IOException {
        float x = stream.readFloat();
        float y = stream.readFloat();
        float z = stream.readFloat();
        float w = stream.readFloat();
        float velX = stream.readFloat();
        float velZ = stream.readFloat();
        return new Location(x, y, z, w, velZ, velX);
    }

    public static void writePlayer(Player player, ObjectOutputStream stream) throws IOException {
        if (player == null) return;
        stream.writeInt(player.getId());
        stream.writeDouble(player.getHealth());
        stream.writeInt(player.getColor());

        writeLocation(player.getLocation(), stream);

        stream.writeInt(player.getCharacterState());
        stream.writeDouble(player.getAttackDamage());
        stream.writeDouble(player.getAttackRange());
        stream.writeDouble(player.getManaPool());

        writeQuaternion(player.getRotation(), stream);
    }

    public static Player readPlayer(ObjectInputStream stream) throws IOException {
        int id = stream.readInt();
        double health = stream.readDouble();
        int color = stream.readInt();

        Location location = readLocation(stream);

        int charState = stream.readInt();
        double attackDmg = stream.readDouble();
        double attackRng = stream.readDouble();
        double manaPool = stream.readDouble();

        Quaternion rotation = readQuaternion(stream);

        return new Player(id, health, color, location, charState, attackDmg, attackRng, manaPool, rotation);
    }


    public static void writeQuaternion(Quaternion quaternion, ObjectOutputStream stream) throws IOException {
        stream.writeFloat(quaternion.x);
        stream.writeFloat(quaternion.y);
        stream.writeFloat(quaternion.z);
        stream.writeFloat(quaternion.w);
    }

    public static Quaternion readQuaternion(ObjectInputStream stream) throws IOException {
        float x = stream.readFloat();
        float y = stream.readFloat();
        float z = stream.readFloat();
        float w = stream.readFloat();
        return new Quaternion(x, y, z, w);
    }

}
