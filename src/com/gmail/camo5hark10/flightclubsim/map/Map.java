package com.gmail.camo5hark10.flightclubsim.map;

import com.gmail.camo5hark10.flightclubsim.mesh.Mesh;

public class Map {
    public static final int HEIGHT = 256;
    public static final int HALF_HEIGHT = HEIGHT / 2;
    public static final int MIN_POS_Y = -HALF_HEIGHT;
    public static final int MAX_POS_Y = HALF_HEIGHT - 1;

    public final int width;
    public final int depth;
    public final int halfWidth;
    public final int halfDepth;
    public final int minPosX;
    public final int maxPosX;
    public final int minPosZ;
    public final int maxPosZ;
    private final Mesh terrainMesh;

    public Map(int width, int depth, Mesh terrainMesh) {
        this.width = width;
        this.depth = depth;

        halfWidth = width / 2;
        halfDepth = depth / 2;
        minPosX = -halfWidth;
        maxPosX = halfWidth - 1;
        minPosZ = -halfDepth;
        maxPosZ = halfDepth - 1;

        this.terrainMesh = terrainMesh;
    }

    public void render() {
        terrainMesh.draw();
    }
}
