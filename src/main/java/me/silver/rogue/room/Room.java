package me.silver.rogue.room;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
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
        this.cornerOne = cornerOne;
        this.cornerTwo = cornerTwo;
    }

    // TODO: Async room building?
    public void buildRoom(byte color) {
        Block block;

        for (int x = (int)cornerOne.getX(); x <= (int)cornerTwo.getX(); x++) {
            block = world.getBlockAt(x, (int)cornerOne.getY(), (int)cornerOne.getZ());
            block.setType(Material.WOOL);
            block.setData(color);

            block = world.getBlockAt(x, (int)cornerOne.getY(), (int)cornerTwo.getZ());
            block.setType(Material.WOOL);
            block.setData(color);
        }

        for (int z = (int)cornerOne.getZ() + 1; z <= (int)cornerTwo.getZ() - 1; z++) {
            block = world.getBlockAt((int)cornerOne.getX(), (int)cornerOne.getY(), z);
            block.setType(Material.WOOL);
            block.setData(color);

            block = world.getBlockAt((int)cornerTwo.getX(), (int)cornerOne.getY(), z);
            block.setType(Material.WOOL);
            block.setData(color);
        }
    }

    public void spawnMob() {
        
    }

}
