package me.silver.rogue;

import me.silver.rogue.entity.RoguePlayer;
import me.silver.rogue.room.Room;
import me.silver.rogue.room.RoomGenerator;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class GameInstance {

    private final World world;

    private RoomGenerator generator;

    private List<RoguePlayer> players = new ArrayList<>();

    // I don't know yet
    public GameInstance(World world) {
        this.world = world;
    }

    public void generateRooms() {
        this.generator = new RoomGenerator(world, 0, 64, 0);
    }

    // Because I don't want other classes screwing with the room generator
    public List<Room> getRooms() {
        return this.generator.getRooms();
    }
}
