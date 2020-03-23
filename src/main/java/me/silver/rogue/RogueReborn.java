package me.silver.rogue;

import co.aikar.commands.PaperCommandManager;
import me.silver.rogue.command.RogueGeneratorCommand;
import me.silver.rogue.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RogueReborn extends JavaPlugin {

    @Override
    public void onEnable() {
//        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        PaperCommandManager manager = new PaperCommandManager(this);

        manager.registerCommand(new RogueGeneratorCommand());
    }
}
