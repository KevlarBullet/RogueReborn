package me.silver.rogue.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.silver.rogue.room.RoomGenerator;
import org.bukkit.Bukkit;
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

            RoomGenerator generator = new RoomGenerator(Bukkit.getWorld("world"), location.getBlockX(), location.getBlockY(), location.getBlockZ());

            sender.sendMessage("Attempting to generate " + count + " rooms.");
            generator.generatePoints(count);
//            generator.buildAndConnectRooms();
        } else {
            // Because I'm too lazy to add other cases and it just doesn't matter
            sender.sendMessage("Error: Command must be executed by a player.");
        }
    }
}
