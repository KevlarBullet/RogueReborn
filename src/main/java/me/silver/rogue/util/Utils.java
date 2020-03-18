package me.silver.rogue.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class Utils {

    public static void drawParticleCircle(Particle effect, Location origin, float radius, int count) {
        double angleIncrement = 360f / count;
        double angle = 0;

        for (int i = 0; i < count; i++) {
            double x = radius * (Math.sin(angle));
            double z = radius * (Math.cos(angle));

            // TODO: Update so particle count isn't arbitrary
            origin.getWorld().spawnParticle(effect, origin.add(x, 0, z), 1, 0, 0, 0, 0);

            angle += angleIncrement;
        }
    }
}
