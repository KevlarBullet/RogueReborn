package me.silver.rogue.game;

import me.silver.rogue.room.Chunk;
import me.silver.rogue.room.Point;
import me.silver.rogue.room.Room;
import me.silver.rogue.util.Pair;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.*;

public class RoomGenerator {

    private final Random random = new Random(System.currentTimeMillis());

    private final World world;

    private HashMap<Long, Chunk> chunkMap = new HashMap<>();
    private ArrayList<Chunk> availableChunks = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();

    private int chunkOriginX;
    private int chunkOriginZ;
    private Pair<Integer, Integer> topLeft;
    private Pair<Integer, Integer> bottomRight;
    private final int y;

    public RoomGenerator(World world, int xOrigin, int y, int zOrigin) {
        this.world = world;
        this.chunkOriginX = xOrigin >> 4;
        this.chunkOriginZ = zOrigin >> 4;
        this.y = y;
        this.topLeft = new Pair<>(chunkOriginX, chunkOriginZ);
        this.bottomRight = new Pair<>(chunkOriginX, chunkOriginZ);
    }

    // TODO: Refactor so that unintended classes can't access this method
    public void generatePoints(int count) {
        // Generate center chunk
        availableChunks.add(getOrGenerateChunk(chunkOriginX, chunkOriginZ));
        int generatedPoints = 0;

        generator:while (generatedPoints < count) {
            // Try 3 times to generate a point
            counter:for (int i = 0; i < 3; i++) {
                Chunk chunk = availableChunks.get(random.nextInt(availableChunks.size()));
                int pointX = random.nextInt(16);
                int pointZ = random.nextInt(16);

                int size;
//                = random.nextInt(12) + 5;
                if (generatedPoints < count * 0.6) {
                    size = random.nextInt(6) + 11;
                } else {
                    size = random.nextInt(4) + 5;
                }

                Point point = new Point(pointX, pointZ, size, chunk);

                List<Chunk> overlappedChunks = new ArrayList<>();
                Map<Point, Point> checkedPoints = new HashMap<>();

                for (int x = (pointX - size >> 4) + chunk.chunkX; x <= (pointX + size >> 4) + chunk.chunkX; x++) {
                    for (int z = (pointZ - size >> 4) + chunk.chunkZ; z <= (pointZ + size >> 4) + chunk.chunkZ; z++) {
                        Chunk otherChunk = this.getOrGenerateChunk(x, z);

                        for (Point otherPoint : otherChunk.getPoints()) {
                            if (!checkedPoints.containsKey(otherPoint) && otherPoint.checkDistance(pointX, pointZ, chunk.chunkX, chunk.chunkZ, size)) {
                                checkedPoints.put(otherPoint, otherPoint);
                            } else {
                                // If generated rooms would overlap then continue to next try
                                continue counter;
                            }
                        }

                        overlappedChunks.add(otherChunk);
                    }
                }

                // If we've made it this far then all points for a given try were far enough from each other to allow
                // for a room to be generated without overlap
                for (Chunk overlappedChunk : overlappedChunks) {
                    overlappedChunk.addPoint(point);
                }

                generatedPoints++;

                this.rooms.add(new Room(this.world, new Vector(point.getTrueX() - size, y, point.getTrueZ() - size),
                        new Vector(point.getTrueX() + size, y, point.getTrueZ() + size)));

                // Continue to the next cycle to generate another room without first adding a ring of chunks
                continue generator;
            }

            // Add a ring of chunks after 3 failed attempts to generate a room without overlapping another room
            this.addChunkRing();
        }
    }

    public void buildAndConnectRooms() {
        byte color = (byte)random.nextInt(16);

        for (Room room : this.rooms) {
            room.buildRoom(color);
        }
    }

    private void addChunkRing() {
        topLeft.setLeft(topLeft.getLeft() - 1);
        topLeft.setRight(topLeft.getRight() - 1);
        bottomRight.setLeft(bottomRight.getLeft() + 1);
        bottomRight.setRight(bottomRight.getRight() + 1);

        for (int x = topLeft.getLeft(); x <= bottomRight.getLeft(); x++) {
            this.availableChunks.add(getOrGenerateChunk(x, topLeft.getRight()));
            this.availableChunks.add(getOrGenerateChunk(x, bottomRight.getRight()));
        }

        for (int z = topLeft.getRight() + 1; z <= bottomRight.getRight() - 1; z++) {
            this.availableChunks.add(getOrGenerateChunk(topLeft.getLeft(), z));
            this.availableChunks.add(getOrGenerateChunk(bottomRight.getLeft(), z));
        }
    }

    // Stolen from Minecraft source code
    public static long asLong(int x, int z) {
        return (long)x & 4294967295L | ((long)z & 4294967295L) << 32;
    }

    // Re un-voided lmao
    public Chunk getOrGenerateChunk(int chunkX, int chunkZ) {
        long chunkPos = asLong(chunkX, chunkZ);
        Chunk chunk;

        if ((chunk = chunkMap.get(chunkPos)) == null) {
            chunk = new Chunk(chunkX, chunkZ, chunkPos);
            this.chunkMap.put(chunkPos, chunk);

        }
        return chunk;
    }

    public Chunk getChunk(int chunkX, int chunkZ) {
        Long chunkPos = asLong(chunkX, chunkZ);

        return chunkMap.get(chunkPos);
    }

    public List<Room> getRooms() {
        return this.rooms;
    }

}
