package me.silver.rogue.room;

import me.silver.rogue.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RoomGenerator {

    private final int xOrigin;
    private final int zOrigin;

    private final Random random = new Random(System.currentTimeMillis());

    private HashMap<Long, Chunk> chunkMap = new HashMap<>();
    private ArrayList<Chunk> availableChunks = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();;

    private int chunkOriginX;
    private int chunkOriginZ;
    private Pair<Integer, Integer> topLeft;
    private Pair<Integer, Integer> bottomRight;

    private int chunkID = 0;

    public RoomGenerator(int xOrigin, int zOrigin) {
        this.xOrigin = xOrigin;
        this.zOrigin = zOrigin;

        this.chunkOriginX = xOrigin >> 4;
        this.chunkOriginZ = zOrigin >> 4;
        this.topLeft = new Pair<>(chunkOriginX, chunkOriginZ);
        this.bottomRight = new Pair<>(chunkOriginX, chunkOriginZ);
    }


    public void generatePoints(int count) {
        Chunk origin = this.getOrGenerateChunk(chunkOriginX, chunkOriginZ);
        int generatedPoints = 1;

        generator:while (generatedPoints < count) {
            // Try 3 times to generate a point
            for (int i = 0; i < 3; i++) {
                Chunk chunk = availableChunks.get(random.nextInt(availableChunks.size()));
                int pointX = random.nextInt(16);
                int pointZ = random.nextInt(16);
                int size = random.nextInt(12) + 5;

                // Check all neighboring chunks for nearby points
                for (int x = chunk.chunkX - 1; x < chunk.chunkX + 1; x++) {
                    for (int z = chunk.chunkZ - 1; z < chunk.chunkZ + 1; z++) {
                        Chunk otherChunk = this.getChunk(x, z);

                        if (otherChunk != null) {
                            for (Point point : otherChunk.getPoints()) {
                                
                            }
                        }
                    }
                }
            }
        }
    }

    private void addChunkRing() {
        topLeft.setLeft(topLeft.getLeft() - 1);
        topLeft.setRight(topLeft.getRight() - 1);
        bottomRight.setLeft(bottomRight.getLeft() + 1);
        bottomRight.setRight(bottomRight.getRight() + 1);

        for (int x = topLeft.getLeft(); x <= bottomRight.getLeft(); x++) {
            for (int z = topLeft.getRight(); z <= bottomRight.getRight(); z++) {
                // Only generate chunk on edges of square
                // Could be more efficient if done with individual loops for each edge
                if (x == topLeft.getLeft() || x == bottomRight.getLeft()
                        || z == topLeft.getLeft() || z == bottomRight.getRight()) {
                    getOrGenerateChunk(x, z);
                }
            }
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
