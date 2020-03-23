package me.silver.rogue.room;

// The whole premise behind points is unnecessary and will probably be removed in favor of just rooms
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

    /**
     * Checks the distance of another point to see if there's overlap
     * @param pointX The X coordinate of the point to be checked
     * @param pointZ The Z coordinate of the point to be checked
     * @param chunkX The X coordinate of the chunk the other point would live in
     * @param chunkZ The Z coordinate of the chunk the other point would live in
     * @param size The maximum size of the room for the given point
     * @return False if the rooms would overlap or true otherwise
     */
    public boolean checkDistance(int pointX, int pointZ, int chunkX, int chunkZ, int size) {
        int minimumDistance = this.size + size + 1;

//        System.out.printf("## pX: %d, pZ: %d, cX: %d, cZ: %d\n", pointX, pointZ, chunkX, chunkZ);

        int actualXOther = (chunkX  << 4) + pointX;
        int actualZOther = (chunkZ  << 4) + pointZ;

//        System.out.printf("## aXC: %d, aZC: %d, aXO: %d, aZO: %d\n", actualXCurrent, actualZCurrent, actualXOther, actualZOther);

        return Math.abs(this.getTrueX() - actualXOther) >= minimumDistance
                || Math.abs(this.getTrueZ() - actualZOther) >= minimumDistance;

//        System.out.println("-Point distance check failed");
//        System.out.printf("--Minimum distance: %d\n", minimumDistance);
//        System.out.printf("--Actual distances: %d, %d\n", Math.abs(actualXCurrent - actualXOther), Math.abs(actualZCurrent - actualZOther));
    }

    public int getTrueX() {
        return this.x + (this.chunk.chunkX << 4);
    }

    public int getTrueZ() {
        return this.z + (this.chunk.chunkZ << 4);
    }

}
