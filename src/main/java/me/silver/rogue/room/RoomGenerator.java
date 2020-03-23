package me.silver.rogue.room;

import me.silver.rogue.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.material.Wool;

import java.util.*;

public class RoomGenerator {

//    private final int xOrigin;
//    private final int zOrigin;

    private final Random random = new Random(System.currentTimeMillis());

    private static List<DyeColor> colors = Arrays.asList(DyeColor.values());

    private HashMap<Long, Chunk> chunkMap = new HashMap<>();
    private ArrayList<Chunk> availableChunks = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();;

    private int chunkOriginX;
    private int chunkOriginZ;
    private Pair<Integer, Integer> topLeft;
    private Pair<Integer, Integer> bottomRight;
    private final int y;

    public RoomGenerator(int xOrigin, int y, int zOrigin) {
//        this.xOrigin = xOrigin;
//        this.zOrigin = zOrigin;

        this.chunkOriginX = xOrigin >> 4;
        this.chunkOriginZ = zOrigin >> 4;
        this.y = y;
        this.topLeft = new Pair<>(chunkOriginX, chunkOriginZ);
        this.bottomRight = new Pair<>(chunkOriginX, chunkOriginZ);
    }

    // Holy crap, do some unit tests before writing this much code!
    public void generatePoints(int count) {

        // For debugging only
        World world = Bukkit.getWorld("world");
        byte color = (byte)random.nextInt(16);
//        DyeColor color = colors.get(random.nextInt(16));

        Chunk origin = this.getOrGenerateChunk(chunkOriginX, chunkOriginZ);
        int generatedPoints = 0;

        generator:while (generatedPoints < count) {
            // Try 3 times to generate a point
            counter:for (int i = 0; i < 3; i++) {
                Chunk chunk = availableChunks.get(random.nextInt(availableChunks.size()));
                int pointX = random.nextInt(16);
                int pointZ = random.nextInt(16);
                int size = random.nextInt(12) + 5;

                // Check all neighboring chunks for nearby points
                for (int x = chunk.chunkX - 1; x <= chunk.chunkX + 1; x++) {
                    for (int z = chunk.chunkZ - 1; z <= chunk.chunkZ + 1; z++) {
                        Chunk otherChunk = this.getChunk(x, z);

                        if (otherChunk != null) {
                            for (Point point : otherChunk.getPoints()) {
                                // If generated rooms would overlap then continue to next try
                                if (!point.checkDistance(pointX, pointZ, x, z, size)) continue counter;
                            }
                        }
                    }
                }

                // If we've made it this far then all points for a given try were far enough from each other to allow
                // for a room to be generated without overlap
                Point point = new Point(pointX, pointZ, size, chunk);

                chunk.addPoint(point);
                generatedPoints++;

                Block block = world.getBlockAt(point.getTrueX(), y, point.getTrueZ());
                block.setType(Material.WOOL);
                block.setData(color);

                // Continue to the next cycle to generate another room without first adding a ring of chunks
                continue generator;
            }

            // Add a ring of chunks after 3 failed attempts to generate a room without overlapping another room
            this.addChunkRing();
        }

        for (Chunk chunk : availableChunks) {
            Bukkit.getLogger().info(String.format("(%d, %d)", chunk.chunkX, chunk.chunkZ));
        }
    }

    private void addChunkRing() {
        topLeft.setLeft(topLeft.getLeft() - 1);
        topLeft.setRight(topLeft.getRight() - 1);
        bottomRight.setLeft(bottomRight.getLeft() + 1);
        bottomRight.setRight(bottomRight.getRight() + 1);

        for (int x = topLeft.getLeft(); x <= bottomRight.getLeft(); x++) {
            getOrGenerateChunk(x, topLeft.getRight());
            getOrGenerateChunk(x, bottomRight.getRight());
        }

        for (int z = topLeft.getRight() + 1; z <= bottomRight.getRight() - 1; z++) {
            getOrGenerateChunk(topLeft.getLeft(), z);
            getOrGenerateChunk(bottomRight.getLeft(), z);
        }
    }

    // Stolen from Minecraft source code
    public static long asLong(int x, int z) {
        return (long)x & 4294967295L | ((long)z & 4294967295L) << 32;
    }

    // Might make this void later, depending on use
    private Chunk getOrGenerateChunk(int chunkX, int chunkZ) {
        Long chunkPos = asLong(chunkX, chunkZ);
        
        if (chunkMap.containsKey(chunkPos)) {
            return chunkMap.get(chunkPos);
        } else {
            Chunk chunk = new Chunk(chunkX, chunkZ, chunkPos);
            chunkMap.put(chunkPos, chunk);
            availableChunks.add(chunk);

            return chunk;
        }
    }

    private Chunk getChunk(int chunkX, int chunkZ) {
        Long chunkPos = asLong(chunkX, chunkZ);

        return chunkMap.get(chunkPos);
    }

}
