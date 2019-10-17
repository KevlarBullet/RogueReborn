package me.silver.rogue.room;

import java.util.ArrayList;
import java.util.Random;

public class Chunk {

    private int chunkX;
    private int chunkZ;

    private ArrayList<Point> points;
    private ArrayList<Room> intersectedRooms;

    private static Random random = new Random(System.currentTimeMillis());

    public Chunk(int chunkX, int chunkZ, int pointDensity) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;

        this.points = new ArrayList<>();
        this.intersectedRooms = new ArrayList<>();
    }

    //TODO: Verify that 2 points don't have the same position
    public void generatePoints(int minPoints, int maxPoints) {
        if (minPoints > maxPoints) {
            int temp = minPoints;

            maxPoints = minPoints;
            minPoints = temp;
        }

        int count = random.nextInt(maxPoints - minPoints) + minPoints;

        for (int i = 0; i < count; i++) {
            int posX = random.nextInt(16);
            int posZ = random.nextInt(16);
            int pointX = this.chunkX << 4 + posX;
            int pointZ = this.chunkZ << 4 + posZ;

            this.points.add(new Point(pointX, pointZ));
        }
    }

    long toLong(int x) {
        return (long) x & 4294967295L;
    }
}
