package com.base.engine;

import org.lwjglx.Sys;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Guard on 11/10/2017.
 */
public class Game {

    private Mesh mesh;
    private Shader shader;
    private Transform transform;
    private Texture texture;
    private Camera camera;

    public Game(){
        //mesh = ResourceLoader.loadMesh("cube.obj");
        mesh = new Mesh();
        texture = ResourceLoader.loadTexture("Marble.jpg");
        System.out.println(texture.getID());
        shader = new Shader();
        camera = new Camera(new Vector3f(0,0,0), new Vector3f(0,0,1), new Vector3f(0,1,0));


         //created pyramid
        Vertex[] vertices = new Vertex[]{
                new Vertex(new Vector3f(-1, -1, 0), new Vector2f(0,0)),
                new Vertex(new Vector3f(0, 1, 0), new Vector2f(0.5f,0)),
                new Vertex(new Vector3f(1, -1, 0), new Vector2f(1,0)),
                new Vertex(new Vector3f(0, -1, 1), new Vector2f(0,0.5f))
        };

        int[] indices = new int[] {3, 1, 0,
                                    2,1,3,
                                    0,1,2,
                                    0,2,3};
        mesh.addVertices(vertices, indices);

        Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
        Transform.setCamera(camera);
        transform = new Transform();

        shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
        shader.compileShader();

        shader.addUniform("transform");
    }

    public void start(){

    }

    public void input(){
        camera.input();

        if(Input.getKey(GLFW_KEY_E)){
            System.out.println("Pressed E");
        }

    }

    float temp = 0.0f;
    public void update(){
        temp += Time.getDelta();
        transform.setTranslation((float)Math.sin(temp),(float)Math.abs(Math.cos(temp)),5);
        transform.setRotation(0, (float)Math.sin(temp) * 180, (float)Math.sin(temp) * 180);
        transform.setScale((float)Math.abs(Math.sin(temp)), (float)Math.abs(Math.sin(temp)), (float)Math.abs(Math.sin(temp)));
    }

    public void render(){
        shader.bind();
        shader.setUniform("transform", transform.getProjectedTransformation());
        texture.bind();
        mesh.draw();
        shader.unbind();
    }
}
