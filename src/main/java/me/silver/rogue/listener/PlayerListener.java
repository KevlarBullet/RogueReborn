package me.silver.rogue.listener;

import me.silver.rogue.entity.RoguePlayer;
import me.silver.rogue.game.GameInstance;
import me.silver.rogue.game.GameManager;
import me.silver.rogue.game.RoomGenerator;
import me.silver.rogue.room.Chunk;
import me.silver.rogue.room.Point;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        // Temporary solution while testing whatever
        Player player = event.getPlayer();
        GameManager.addRoguePlayer(new RoguePlayer(player), player.getName());
    }

    @EventHandler
    public static void onPlayerMove(PlayerMoveEvent event) {
        Location to = event.getTo();
        Location from = event.getFrom();

        if (to.getBlockX() == from.getBlockX()
                && to.getBlockY() == from.getBlockY()
                && to.getBlockZ() == from.getBlockZ()) {
            // Do nothing if the player hasn't moved 1 block
            return;
        }

        Player player = event.getPlayer();
        RoguePlayer roguePlayer;

        if ((roguePlayer = GameManager.getRoguePlayer(player)) != null) {
            GameInstance instance = roguePlayer.getGameInstance();

            if (instance != null) {
                int chunkX = to.getBlockX() >> 4;
                int chunkZ = to.getBlockZ() >> 4;

                RoomGenerator generator = instance.getGenerator();
                Chunk chunk = generator.getChunk(chunkX, chunkZ);

                if (chunk != null) {
                    for (Point point : chunk.getPoints()) {
                        if (point.getTrueX() - point.size + 1 < to.getX()
                                && point.getTrueX() + point.size > to.getX()
                                && point.getTrueZ() - point.size + 1 < to.getZ()
                                && point.getTrueZ() + point.size > to.getZ()) {
                            // Player is moving inside of a room
                            // Spawn initial wave of mobs here
                            break;
                        }
                    }

                }
            }

        }
    }

}
