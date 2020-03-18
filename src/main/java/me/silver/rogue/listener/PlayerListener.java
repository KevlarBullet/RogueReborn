package me.silver.rogue.listener;

import me.silver.rogue.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerListener implements Listener {

    @EventHandler
    public static void onRightClick(PlayerInteractEvent event) {
        Bukkit.getLogger().info("This worked");
        Player player = event.getPlayer();

        Utils.drawParticleCircle(Particle.FLAME, player.getLocation(), 1, 24);
//        if (event.getHand().) {
//            switch (player.getActiveItem().getType()) {
//                case STICK:
//                    break;
//                default:
//                    // idk
//            }
//        }
    }

}
