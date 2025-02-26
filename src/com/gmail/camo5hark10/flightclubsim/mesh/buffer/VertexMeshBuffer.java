package com.gmail.camo5hark10.flightclubsim.mesh.buffer;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

public class VertexMeshBuffer extends MeshBuffer {
    private final int index;

    public VertexMeshBuffer(float[] data, int index, int size) {
        super(GL_ARRAY_BUFFER);

        this.index = index;

        FloatBuffer dataBuffer = null;

        try {
            dataBuffer = memAllocFloat(data.length).put(data).flip();

            glBufferData(target, dataBuffer, GL_STATIC_DRAW);
        } finally {
            freeBuffer(dataBuffer);
        }

        glVertexAttribPointer(index, size, GL_FLOAT, false, size * Float.BYTES, 0L);
    }

    public void enable() {
        glEnableVertexAttribArray(index);
    }
}
