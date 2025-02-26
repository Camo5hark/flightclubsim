package com.gmail.camo5hark10.flightclubsim;

import static org.lwjgl.glfw.GLFW.*;

public class Timer {
    private static double deltaTime;
    private static double time;
    private static double fps;

    public static void update() {
        double now = glfwGetTime();

        deltaTime = now - time;
        time = now;
        fps = 1.0 / deltaTime;
    }

    public static double getDeltaTime() {
        return deltaTime;
    }

    public static double getTime() {
        return time;
    }

    public static double getFPS() {
        return fps;
    }
}
