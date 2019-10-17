package me.silver.rogue.room;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Room {

    private World world;

    private Vector cornerOne;
    private Vector cornerTwo;

    public Room(Location cornerOne, Location cornerTwo) {
        this(cornerOne.getWorld(), cornerOne.toVector(), cornerTwo.toVector());
    }

    public Room(World world, Vector cornerOne, Vector cornerTwo) {
        this.world = world;

        double maxX;
        double minX;
        double maxY;
        double minY;
        double maxZ;
        double minZ;

        if (cornerOne.getX() >= cornerTwo.getX()) {
            maxX = cornerOne.getX();
            minX = cornerTwo.getX();
        } else {
            maxX = cornerTwo.getX();
            minX = cornerOne.getX();
        }

        if (cornerOne.getY() >= cornerTwo.getY()) {
            maxY = cornerOne.getY();
            minY = cornerTwo.getY();
        } else {
            maxY = cornerTwo.getY();
            minY = cornerOne.getY();
        }

        if (cornerOne.getZ() >= cornerTwo.getZ()) {
            maxZ = cornerOne.getZ();
            minZ = cornerTwo.getZ();
        } else {
            maxZ = cornerTwo.getZ();
            minZ = cornerOne.getZ();
        }

        this.cornerOne = new Vector(maxX, maxY, maxZ);
        this.cornerTwo = new Vector(minX, minY, minZ);
    }
}
