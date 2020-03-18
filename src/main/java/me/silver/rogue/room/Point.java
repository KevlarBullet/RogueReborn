package me.silver.rogue.room;

public class Point {

    public final int x;
    public final int z;
    public final int size;
    public final Chunk chunk;

    public Point(int x, int z, int size, Chunk chunk) {
        this.x = x;
        this.z = z;
        this.size = size;
        this.chunk = chunk;
    }

}
