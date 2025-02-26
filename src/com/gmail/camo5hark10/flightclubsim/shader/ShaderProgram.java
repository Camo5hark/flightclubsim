package com.gmail.camo5hark10.flightclubsim.shader;

import com.gmail.camo5hark10.flightclubsim.Resource;
import org.joml.Matrix4d;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.*;

public class ShaderProgram extends Resource {
    private final Map<String, Integer> uniforms = new HashMap<>();

    public ShaderProgram(Shader... shaders) {
        super(glCreateProgram());

        for (Shader shader : shaders) {
            glAttachShader(value(), shader.value());
        }

        glLinkProgram(value());

        if (glGetProgrami(value(), GL_LINK_STATUS) == 0) {
            throw new RuntimeException(glGetProgramInfoLog(value()));
        }

        glValidateProgram(value());

        if (glGetProgrami(value(), GL_VALIDATE_STATUS) == 0) {
            throw new RuntimeException(glGetProgramInfoLog(value()));
        }

        for (Shader shader : shaders) {
            for (String uniform : shader.uniforms) {
                int location = glGetUniformLocation(value(), uniform);

                if (location == -1) {
                    throw new NullPointerException("uniform not found: " + uniform);
                }

                uniforms.put(uniform, location);
            }
        }
    }

    @Override
    public void bind() {
        glUseProgram(value());
    }

    public void setMat4(String uniform, Matrix4d value) {
        try (MemoryStack stack = stackPush()) {
            glUniformMatrix4fv(uniforms.get(uniform), false, value.get(stack.mallocFloat(16)));
        }
    }
}
