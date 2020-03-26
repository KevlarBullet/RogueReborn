package me.silver.rogue.game;

import me.silver.rogue.entity.RoguePlayer;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;

public class GameInstance {

    public final int gameID;
    private final World world;

    private RoomGenerator generator;

    private Set<RoguePlayer> players = new HashSet<>();

    // I don't know yet
    public GameInstance(int gameID, World world) {
        this.gameID = gameID;
        this.world = world;
    }

    // My use of the keyword 'this' can be described as nothing less than chaotic
    public void generateRooms(int x, int y, int z, int count) {
        this.generator = new RoomGenerator(world, x, y, z);

        generator.generatePoints(count);
        generator.buildAndConnectRooms();
    }

    protected void addPlayer(RoguePlayer player) {
        this.players.add(player);
    }

    protected void removePlayer(RoguePlayer player) {
        this.players.remove(player);
    }

    public Set<RoguePlayer> getPlayers() {
        return players;
    }

    public RoomGenerator getGenerator() {
        return this.generator;
    }
}
