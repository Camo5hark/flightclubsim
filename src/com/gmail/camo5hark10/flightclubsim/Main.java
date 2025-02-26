package com.gmail.camo5hark10.flightclubsim;

import com.gmail.camo5hark10.flightclubsim.map.Map;
import com.gmail.camo5hark10.flightclubsim.map.MapLoader;
import com.gmail.camo5hark10.flightclubsim.map.Camera;
import com.gmail.camo5hark10.flightclubsim.shader.Shader;
import com.gmail.camo5hark10.flightclubsim.shader.ShaderProgram;
import org.joml.Vector3d;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWErrorCallback.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL33.*;

public class Main {
    public static final Main INSTANCE = new Main();
    private static final int CLEAR_MASK = GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT;

    private volatile boolean running;
    private Display display;

    public void run() {
        running = true;

        new Thread(() -> {
            try {
                // Init phase 1: GLFW
                glfwSetErrorCallback(createThrow());

                if (!glfwInit()) {
                    throw new RuntimeException("glfwInit returned false");
                }

                // Init phase 2: Display
                display = new Display(false, 640, 480, "flightclubsim", false);
                display.update();

                // Init phase 3: GL
                createCapabilities();
                glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glEnable(GL_DEPTH_TEST);
                glDepthFunc(GL_LEQUAL);
                glEnable(GL_CULL_FACE);
                glCullFace(GL_FRONT);

                ShaderProgram terrainShader = new ShaderProgram(
                        new Shader("terrain", Shader.TYPE_VERTEX, "projection", "sceneView"),
                        new Shader("terrain", Shader.TYPE_FRAGMENT)
                );

                Map map = MapLoader.load("map");
                Camera camera = new Camera();
                camera.position.y = 180.0;

                // Render loop
                while (running && display.isOpen()) {
                    Timer.update();

                    double d = Timer.getDeltaTime() * 2.0;
                    double m = d * 8.0;
                    Vector3d x = camera.getOrientation().transformUnit(MathHelper.right()).mul(m);
                    Vector3d z = camera.getOrientation().transformUnit(MathHelper.backward()).mul(m);

                    if (display.isKeyPressed(GLFW_KEY_A)) {
                        camera.position.sub(x);
                    }

                    if (display.isKeyPressed(GLFW_KEY_D)) {
                        camera.position.add(x);
                    }

                    if (display.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
                        camera.position.y -= m;
                    }

                    if (display.isKeyPressed(GLFW_KEY_SPACE)) {
                        camera.position.y += m;
                    }

                    if (display.isKeyPressed(GLFW_KEY_S)) {
                        camera.position.sub(z);
                    }

                    if (display.isKeyPressed(GLFW_KEY_W)) {
                        camera.position.add(z);
                    }

                    // rotate
                    if (display.isKeyPressed(GLFW_KEY_LEFT)) {
                        camera.rotation.y -= d;
                    }

                    if (display.isKeyPressed(GLFW_KEY_RIGHT)) {
                        camera.rotation.y += d;
                    }

                    if (display.isKeyPressed(GLFW_KEY_DOWN)) {
                        camera.rotation.x -= d;
                    }

                    if (display.isKeyPressed(GLFW_KEY_UP)) {
                        camera.rotation.x += d;
                    }

                    // Start frame
                    glClear(CLEAR_MASK);
                    glViewport(0, 0, display.width, display.height);

                    // Render frame
                    camera.updateSceneView();

                    terrainShader.bind();
                    terrainShader.setMat4("projection", camera.getProjection());
                    terrainShader.setMat4("sceneView", camera.getSceneView());

                    map.render();

                    // End frame
                    display.swapBuffers();

                    glfwPollEvents();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                // Cleanup
                // Stop all threads if this one stops
                stop();

                // Cleanup Display
                display.destroy();

                // Cleanup GLFW
                GLFWErrorCallback callback = glfwSetErrorCallback(null);

                if (callback != null) {
                    callback.free();
                }
            }
        }, "Render").start();
    }

    public synchronized void stop() {
        running = false;
    }

    public Display getDisplay() {
        return display;
    }

    public static void main(String[] args) {
        INSTANCE.run();
    }
}
