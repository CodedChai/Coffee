package com.base.engine;

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

public class Input extends GLFWKeyCallback {

    public static int[] keys = new int[65535];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action;
    }
}
