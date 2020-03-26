package me.silver.rogue.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.silver.rogue.game.GameInstance;
import me.silver.rogue.game.GameManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("rg")
public class RogueGeneratorCommand extends BaseCommand {

    @Default
    public static void generatePoints(CommandSender sender, int count) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            Location location = player.getLocation();

            GameInstance testInstance = GameManager.startGame(location.getWorld());

            GameManager.addPlayerToGame(testInstance, GameManager.getRoguePlayer(player));
            testInstance.generateRooms(location.getBlockX(), location.getBlockY(), location.getBlockZ(), count);

            sender.sendMessage("Attempting to generate " + count + " rooms.");
        } else {
            // Because I'm too lazy to add other cases and it just doesn't matter
            sender.sendMessage("Error: Command must be executed by a player.");
        }
    }
}
