package com.gmail.camo5hark10.flightclubsim.map;

import com.gmail.camo5hark10.flightclubsim.MathHelper;
import com.gmail.camo5hark10.flightclubsim.Resource;
import com.gmail.camo5hark10.flightclubsim.mesh.Mesh;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class MapLoader {
    public static Map load(String path) {
        InputStream assetIn = null;
        GZIPInputStream gzIn = null;
        byte[] bin = null;

        try {
            assetIn = Resource.inAsset("/maps/" + path + ".bin.gz");

            if (assetIn == null) {
                throw new FileNotFoundException(path);
            }

            gzIn = new GZIPInputStream(assetIn);
            bin = gzIn.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Resource.closeIn(gzIn);
            Resource.closeIn(assetIn);
        }

        if (bin == null) {
            throw new NullPointerException("bin == null");
        }

        int width = MathHelper.bytesToInt(bin, 0);
        int depth = MathHelper.bytesToInt(bin, 4);
        int halfWidth = width / 2;
        int halfDepth = depth / 2;
        float[] colorData = new float[width * depth];
        float[] vertexData = new float[colorData.length * 3];

        for (int z = 0; z < depth; z++) {
            for (int x = 0; x < width; x++) {
                int i = MathHelper.xzToIndex(width, x, z);
                int vi = i * 3;
                float y = (float) (bin[i + 8] & 0xFF);

                vertexData[vi] = x - halfWidth;
                vertexData[vi + 1] = y - Map.HALF_HEIGHT;
                vertexData[vi + 2] = z - halfDepth;
                colorData[i] = (float) Math.abs(Math.random());//y / 255.0F;
            }
        }

        int widthFaces = width - 1;
        int depthFaces = depth - 1;
        int[] indexData = new int[(widthFaces * depthFaces) * 6];

        for (int z = 0; z < depthFaces; z++) {
            for (int x = 0; x < widthFaces; x++) {
                int i = MathHelper.xzToIndex(widthFaces, x, z);
                int fi = i * 6;
                int leftUp = i + z;
                int leftDown = leftUp + widthFaces + 1;
                int rightDown = leftDown + 1;

                indexData[fi] = leftUp; // left up
                indexData[fi + 1] = leftUp + 1; // right up
                indexData[fi + 2] = rightDown; // right down
                indexData[fi + 3] = rightDown; // right down
                indexData[fi + 4] = leftDown; // left down
                indexData[fi + 5] = leftUp; // left up
            }
        }

        return new Map(width, depth, new Mesh(vertexData, colorData, indexData));
    }
}
