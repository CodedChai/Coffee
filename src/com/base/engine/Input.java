package com.base.engine;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    public static void update(){
    }

    public static boolean getKey(int keyCode){
        int state = glfwGetKey(Window.windowid, keyCode);
        return (state == GLFW_PRESS || state == GLFW_REPEAT);
    }

    public static boolean getKeyDown(int keyCode){
        int state = glfwGetKey(Window.windowid, keyCode);
        return (state == GLFW_PRESS);
    }

    public static boolean getKeyUp(int keyCode){
        // TODO: Check only when key is released
        int state = glfwGetKey(Window.windowid, keyCode);
        return (state == GLFW_RELEASE);
    }

}
