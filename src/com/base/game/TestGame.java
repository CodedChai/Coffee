package com.base.game;

import com.base.engine.core.*;
import com.base.engine.rendering.*;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;

public class TestGame implements Game {

    private Camera camera;
    private GameObject root;

    public TestGame(){

    }

    public void init(){
        root = new GameObject();
        camera = new Camera(new Vector3f(0,0,0), new Vector3f(0,0,1), new Vector3f(0,1,0));

        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;
        Vertex[] vertices = new Vertex[] {  new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))
        };

        int indices[] = {   0, 1, 2,
                2, 1, 3};
        Mesh mesh = new Mesh(vertices, indices, true);
        Material material = new Material(new Texture("Marble.jpg"), new Vector3f(1.0f, 1.0f, 1.0f), 1, 16);

        MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
        root.addComponent(meshRenderer);

        Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
        Transform.setCamera(camera);
    }

    public void input(){
        camera.input();
        root.input();
        if(Input.getKey(GLFW_KEY_E)){
            System.out.println("Pressed E");
        }
    }


    public void update(){
        root.getTransform().setTranslation(0, -1, 5);
        root.update();
 }

    public void render(){
        root.render();
    }
}
