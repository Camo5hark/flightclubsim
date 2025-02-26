package com.gmail.camo5hark10.flightclubsim.mesh.buffer;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.*;

public class IndexMeshBuffer extends MeshBuffer {
    public IndexMeshBuffer(int[] data) {
        super(GL_ELEMENT_ARRAY_BUFFER);

        bind();

        IntBuffer dataBuffer = null;

        try {
            dataBuffer = memAllocInt(data.length).put(data).flip();

            glBufferData(target, dataBuffer, GL_STATIC_DRAW);
        } finally {
            freeBuffer(dataBuffer);
        }
    }

    @Override
    public void bind() {
        glBindBuffer(target, value());
    }
}
