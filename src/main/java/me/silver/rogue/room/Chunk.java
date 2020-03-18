package me.silver.rogue.room;

import java.util.ArrayList;
import java.util.Random;

public class Chunk {

    final int chunkX;
    final int chunkZ;
    final long chunkPos;

    private ArrayList<Point> points = new ArrayList<>();

    private static Random random = new Random(System.currentTimeMillis());

    public Chunk(int chunkX, int chunkZ, long chunkPos) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.chunkPos = chunkPos;
    }

    ArrayList<Point> getPoints() {
        return this.points;
    }

    void addPoint(Point point) {
        this.points.add(point);
    }

    static long toLong(int chunkX, int chunkZ) {
        return ((long) chunkX | ((long) chunkZ) << 32);
    }

}
