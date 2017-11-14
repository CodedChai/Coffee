package com.base.engine;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Guard on 11/10/2017.
 */
public class Game {

    private Mesh mesh;
    private Shader shader;
    private Transform transform;

    public Game(){
        mesh = new Mesh();
        shader = new Shader();

        Vertex[] data = new Vertex[]{
                new Vertex(new Vector3f(-1, -1, 0)),
                new Vertex(new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f(1, -1, 0))
        };
        mesh.addVertices(data);

        transform = new Transform();

        shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
        shader.compileShader();


        shader.addUniform("transform");
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
        transform.setTranslation((float)Math.sin(temp),(float)Math.abs(Math.cos(temp)),0);
        transform.setRotation(0, 0, (float)Math.sin(temp) * 180);
        transform.setScale((float)Math.abs(Math.sin(temp)), (float)Math.abs(Math.sin(temp)), (float)Math.abs(Math.sin(temp)));
    }

    public void render(){
        shader.bind();
        shader.setUniform("transform", transform.getTransformation());

        mesh.draw();
        shader.unbind();
    }
}
