package com.base.engine;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Guard on 11/10/2017.
 */
public class Game {

    private Mesh mesh;
    private Shader shader;

    public Game(){
        mesh = new Mesh();
        shader = new Shader();

        Vertex[] data = new Vertex[]{
                new Vertex(new Vector3f(-1, -1, 0)),
                new Vertex(new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f(1, -1, 0))
        };
        mesh.addVertices(data);

        shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
        shader.compileShader();


        shader.addUniform("fade");
    }

    public void start(){

    }



    public void input(){
        if(GLFW_PRESS == Input.keys[GLFW_KEY_E]){
            System.out.println("Pressed E");
        }

    }
    float temp = 0.0f;


    public void update(){

        temp += Time.getDelta();
        shader.bind();
        shader.setUniformf("fade", (float)Math.abs(Math.sin(temp)));
        shader.unbind();
    }

    public void render(){
        shader.bind();
        mesh.draw();
        shader.unbind();
    }
}
