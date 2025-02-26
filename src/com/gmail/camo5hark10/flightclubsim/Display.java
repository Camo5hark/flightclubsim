package com.gmail.camo5hark10.flightclubsim;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

public class Display {
    public boolean fullscreen;
    public int width;
    public int height;
    public String title;
    public boolean vSync;
    private long windowHandle;

    public Display(boolean fullscreen, int width, int height, String title, boolean vSync) {
        this.fullscreen = fullscreen;
        this.width = width;
        this.height = height;
        this.title = title;
        this.vSync = vSync;
    }

    public void destroy() {
        if (windowHandle == 0L) {
            return;
        }

        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);

        windowHandle = 0L;
    }

    public void update() {
        destroy();

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        long monitorHandle = glfwGetPrimaryMonitor();

        if (monitorHandle == 0L) {
            throw new NullPointerException("monitorHandle == NULL");
        }

        GLFWVidMode vidMode = glfwGetVideoMode(monitorHandle);

        if (vidMode == null) {
            throw new NullPointerException("vidMode == null");
        }

        int screenWidth = vidMode.width();
        int screenHeight = vidMode.height();

        if (fullscreen) {
            width = screenWidth;
            height = screenHeight;
        }

        windowHandle = glfwCreateWindow(width, height, title, fullscreen ? monitorHandle : 0L, 0L);

        if (windowHandle == 0L) {
            throw new NullPointerException("windowHandle == NULL");
        }

        glfwSetFramebufferSizeCallback(windowHandle, (long window, int width, int height) -> {
            this.width = width;
            this.height = height;
        });

        glfwSetWindowPos(windowHandle, (screenWidth - width) / 2, (screenHeight - height) / 2);
        glfwMakeContextCurrent(windowHandle);
        glfwSwapInterval(vSync ? 1 : 0);
        glfwShowWindow(windowHandle);
    }

    public boolean isOpen() {
        return !glfwWindowShouldClose(windowHandle);
    }

    public void swapBuffers() {
        glfwSwapBuffers(windowHandle);
    }

    public boolean isKeyPressed(int key) {
        return glfwGetKey(windowHandle, key) == GLFW_PRESS;
    }
}
