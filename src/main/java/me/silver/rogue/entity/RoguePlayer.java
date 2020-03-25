package me.silver.rogue.entity;

import me.silver.rogue.GameInstance;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RoguePlayer {

    private final Player player;

    private GameInstance gameInstance;

    public RoguePlayer(Player player) {
        this.player = player;
    }

    public void setGameInstance(GameInstance instance) {
        this.gameInstance = instance;
    }

    public GameInstance getGameInstance() {
        return this.gameInstance;
    }
}
