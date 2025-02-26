package com.gmail.camo5hark10.flightclubsim;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.system.MemoryUtil.memFree;

public abstract class Resource {
    private int value;

    protected Resource(int value) {
        this.value = value;
    }

    public abstract void bind();

    public int value() {
        return value;
    }

    public static InputStream in(String path) {
        return Resource.class.getResourceAsStream(path);
    }

    public static InputStream inAsset(String path) {
        return in("/assets" + path);
    }

    public static String readIn(InputStream in) throws IOException {
        return new String(in.readAllBytes(), StandardCharsets.UTF_8);
    }

    public static void closeIn(InputStream in) {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void freeBuffer(Buffer buffer) {
        if (buffer != null) {
            memFree(buffer);
        }
    }
}
