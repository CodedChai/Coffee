package com.base.engine;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Guard on 11/10/2017.
 */
public class Game {

    public Game(){
    }

    public void start(){

    }

    public void input(){
        if(GLFW_PRESS == Input.keys[GLFW_KEY_E]){
            System.out.println("Pressed E");
        }

    }


    public void update(){

    }

    public void render(){

    }
}
