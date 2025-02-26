package com.gmail.camo5hark10.flightclubsim.shader;

import com.gmail.camo5hark10.flightclubsim.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL46.*;

public class Shader extends Resource {
    private static record Type(int value, String extension) {}
    public static final Type TYPE_VERTEX = new Type(GL_VERTEX_SHADER, ".vsh");
    public static final Type TYPE_FRAGMENT = new Type(GL_FRAGMENT_SHADER, ".fsh");

    public final String[] uniforms;

    public Shader(String path, Type type, String... uniforms) {
        super(glCreateShader(type.value));

        this.uniforms = uniforms;

        InputStream sourceIn = null;

        try {
            sourceIn = inAsset("/shaders/" + path + type.extension);

            if (sourceIn == null) {
                throw new FileNotFoundException(path);
            }

            glShaderSource(value(), readIn(sourceIn));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIn(sourceIn);
        }

        glCompileShader(value());

        if (glGetShaderi(value(), GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException(glGetShaderInfoLog(value()));
        }
    }

    @Override
    public void bind() {}
}
