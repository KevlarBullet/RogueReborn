package me.silver.rogue.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.silver.rogue.room.RoomGenerator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("rg")
public class RogueGeneratorCommand extends BaseCommand {

    @Default
    public static void generatePoints(CommandSender sender, int count) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            RoomGenerator generator = new RoomGenerator(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());

            sender.sendMessage("Attempting to generate " + count + " rooms.");
            generator.generatePoints(count);
        } else {
            // Because I'm too lazy to add other cases and it just doesn't matter
            sender.sendMessage("Error: Command must be executed by a player.");
        }
    }
}
