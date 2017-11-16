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
    private Material material;
    private Camera camera;

    public Game(){
        //mesh = ResourceLoader.loadMesh("cube.obj");
        mesh = new Mesh();
        material = new Material(ResourceLoader.loadTexture("Marble.jpg"), new Vector3f(1.0f, 1.0f, 1.0f));
        shader = PhongShader.getInstance();
        camera = new Camera(new Vector3f(0,0,0), new Vector3f(0,0,1), new Vector3f(0,1,0));
        transform = new Transform();

         //created pyramid
        Vertex[] vertices = new Vertex[]{
                new Vertex(new Vector3f(-1.0f, -1.0f, 0.5773f), new Vector2f(0.0f,0.0f)),
                new Vertex(new Vector3f(0.0f, -1.0f, -1.15475f), new Vector2f(0.5f,0.0f)),
                new Vertex(new Vector3f(1.0f, -1.0f, 0.5773f), new Vector2f(1.0f,0.0f)),
                new Vertex(new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.5f,1.0f))
        };

        int[] indices = new int[] {0, 3, 1,
                                    1,3,2,
                                    2,3,0,
                                    1,2,0};
        mesh.addVertices(vertices, indices, true);

        Transform.setProjection(70f, Window.getWidth(), Window.getHeight(), 0.1f, 1000);
        Transform.setCamera(camera);

        PhongShader.setAmbientLight(new Vector3f(0.1f, 0.1f, 0.1f));
        PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(new Vector3f(1,1,1), 0.8f), new Vector3f(1,1,1)));
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
        //transform.setScale((float)Math.abs(Math.sin(temp)), (float)Math.abs(Math.sin(temp)), (float)Math.abs(Math.sin(temp)));
    }

    public void render(){
        RenderUtil.setClearColor(Transform.getCamera().getPos().div(2048f).Abs());
        shader.bind();
        shader.updateUniforms(transform.getTransformation(), transform.getProjectedTransformation(), material);
        mesh.draw();
        shader.unbind();
    }
}
