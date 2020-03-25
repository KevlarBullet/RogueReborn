package me.silver.rogue;

import co.aikar.commands.PaperCommandManager;
import me.silver.rogue.command.RogueGeneratorCommand;
import me.silver.rogue.entity.RoguePlayer;
import me.silver.rogue.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RogueReborn extends JavaPlugin {

    private static HashMap<String, RoguePlayer> players;

    @Override
    public void onEnable() {
//        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        players = new HashMap<>();

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new RogueGeneratorCommand());
    }

    public static RoguePlayer getRoguePlayer(@NotNull Player player) {
        return  players.get(player.getName());
    }
}
