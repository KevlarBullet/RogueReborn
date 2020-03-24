package me.silver.rogue.room;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

    final int chunkX;
    final int chunkZ;
    final long chunkPos;

    // Contains both points whose generated rooms would overlap this chunk and points directly within this chunk
    private ArrayList<Point> points = new ArrayList<>();

    public Chunk(int chunkX, int chunkZ, long chunkPos) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.chunkPos = chunkPos;
    }

    List<Point> getPoints() {
        return this.points;
    }

    void addPoint(Point point) {
        this.points.add(point);
    }

    static long toLong(int chunkX, int chunkZ) {
        return ((long) chunkX | ((long) chunkZ) << 32);
    }

}
