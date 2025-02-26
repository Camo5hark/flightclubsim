package com.gmail.camo5hark10.flightclubsim.mesh.buffer;

import com.gmail.camo5hark10.flightclubsim.Resource;

import static org.lwjgl.opengl.GL33.*;

public abstract class MeshBuffer extends Resource {
    protected final int target;

    protected MeshBuffer(int target) {
        super(glGenBuffers());

        this.target = target;

        bind();
    }

    @Override
    public void bind() {
        glBindBuffer(target, value());
    }
}
