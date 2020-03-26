package me.silver.rogue.game;

import me.silver.rogue.entity.RoguePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

// I actually have no idea where to go with this
public class GameManager {

    private static int gameID = 0;

    private static Map<String, RoguePlayer> players = new HashMap<>();
    private static Map<Integer, GameInstance> games = new HashMap<>();

    public static GameInstance startGame(World world) {
        GameInstance instance = new GameInstance(gameID, world);
        games.put(gameID++, instance);

        return instance;
    }

    public static void stopGame(GameInstance instance) {
        stopGame(instance.gameID);
    }

    public static void stopGame(int gameID) {
        GameInstance instance;

        if ((instance = games.remove(gameID)) != null) {
            for (RoguePlayer player : instance.getPlayers()) {
                if (player.getGameInstance() == instance) {
                    player.setGameInstance(null);
                }
            }

            // Run whatever other code is needed to cancel the game instance
        }
    }

    // Might be better to put this in GameInstance instead
    public static void addPlayerToGame(GameInstance instance, RoguePlayer player) {
        GameInstance otherInstance = player.getGameInstance();

        if (otherInstance != null) {
            otherInstance.removePlayer(player);
        }

        instance.addPlayer(player);
        player.setGameInstance(instance);
    }

    public static RoguePlayer getRoguePlayer(String playerName) {
        return players.get(playerName);
    }

    public static RoguePlayer getRoguePlayer(@NotNull Player player) {
        return players.get(player.getName());
    }

    public static void addRoguePlayer(RoguePlayer player, String name) {
        players.put(name, player);
    }

}
