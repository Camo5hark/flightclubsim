package com.gmail.camo5hark10.flightclubsim.mesh;

import com.gmail.camo5hark10.flightclubsim.Resource;
import com.gmail.camo5hark10.flightclubsim.mesh.buffer.IndexMeshBuffer;
import com.gmail.camo5hark10.flightclubsim.mesh.buffer.VertexMeshBuffer;

import static org.lwjgl.opengl.GL33.*;

public class Mesh extends Resource {
    private final VertexMeshBuffer vertexBuffer;
    private final VertexMeshBuffer colorBuffer;
    private final int drawCount;

    public Mesh(float[] vertexData, float[] colorData, int[] indexData) {
        super(glGenVertexArrays());

        bind();

        vertexBuffer = new VertexMeshBuffer(vertexData, 0, 3);
        colorBuffer = new VertexMeshBuffer(colorData, 1, 1);

        new IndexMeshBuffer(indexData);

        drawCount = indexData.length;
    }

    @Override
    public void bind() {
        glBindVertexArray(value());
    }

    public void draw() {
        vertexBuffer.enable();
        colorBuffer.enable();

        bind();

        glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0L);
    }
}
