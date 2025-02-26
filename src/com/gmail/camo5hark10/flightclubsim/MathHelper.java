package com.gmail.camo5hark10.flightclubsim;

import org.joml.Vector3d;

public class MathHelper {
    public static Vector3d left() {
        return new Vector3d(-1.0, 0.0, 0.0);
    }

    public static Vector3d right() {
        return new Vector3d(1.0, 0.0, 0.0);
    }

    public static Vector3d down() {
        return new Vector3d(0.0, -1.0, 0.0);
    }

    public static Vector3d up() {
        return new Vector3d(0.0, 1.0, 0.0);
    }

    public static Vector3d backward() {
        return new Vector3d(0.0, 0.0, -1.0);
    }

    public static Vector3d forward() {
        return new Vector3d(0.0, 0.0, 1.0);
    }

    public static double normalizeRadians(double radians) {
        double mod = radians % Math.PI;

        return radians < -Math.PI ? Math.PI + mod : radians > Math.PI ? -Math.PI + mod : radians;
    }

    public static int bytesToInt(byte[] data, int offset) {
        if (offset < 0 || offset + Integer.BYTES >= data.length) {
            throw new IndexOutOfBoundsException(offset);
        }

        int result = 0;

        for (int i = 0; i < Integer.BYTES; i++) {
            result |= ((int) data[offset + i] & 0xFF) << (Integer.SIZE - ((i + 1) * Byte.SIZE));
        }

        return result;
    }

    public static int xzToIndex(int width, int x, int z) {
        return (width * z) + x;
    }

    public static float map(float value, float fromMin, float fromMax, float toMin, float toMax) {
        return value * ((fromMax - fromMin) / (toMax - toMin));
    }
}
