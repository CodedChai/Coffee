package com.base.engine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;

/**
 * Created by Guard on 11/10/2017.
 */
public class Game {

    public Game(){

    }

    public void input(){
        if(Input.getKey((GLFW_KEY_E))){
            System.out.println("Just pressed E");
        }
       /* if(Input.getKeyUp(((int)GLFW_KEY_E))){
            System.out.println("Just released E");
        }*/
    }

    public void update(){

    }

    public void render(){

    }
}
