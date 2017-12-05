package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.core.GameObject;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.*;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderingEngine {

    private Camera mainCamera;
    private Vector3f ambientLight;


    // "Permanent" Structure
    private ArrayList<BaseLight> lights;
    private BaseLight activeLight;

    public RenderingEngine(){
        lights = new ArrayList<>();

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);

        mainCamera = new Camera((float)Math.toRadians(70.0f),
                (float)Window.getWidth()/(float)Window.getHeight(),0.01f, 1000.0f);
        ambientLight = new Vector3f(0.2f, 0.2f, 0.2f);
    }

    public void input(float delta){
        mainCamera.input(delta);
    }

    public void render(GameObject object){
        clearScreen();

        lights.clear();
        object.addToRenderingEngine(this);

        Shader forwardAmbient = ForwardAmbient.getInstance();
        forwardAmbient.setRenderingEngine(this);


        object.render(forwardAmbient);

        // To blend in other lighting effects
        glEnable(GL_BLEND);
        // Add them together
        glBlendFunc(GL_ONE, GL_ONE);
        // Disable writing to depth buffer
        glDepthMask(false);
        // Only add lighting if they have the same exact depth value
        glDepthFunc(GL_EQUAL);

        for(BaseLight light : lights){
            light.getShader().setRenderingEngine(this);
            activeLight = light;
            object.render(light.getShader());
        }

        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    public Vector3f getAmbientLight(){
        return ambientLight;
    }

    private static void clearScreen(){
        // TODO: Stencil Buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void setTextures(boolean enabled){
        if(enabled)
            glEnable(GL_TEXTURE_2D);
        else
            glDisable(GL_TEXTURE_2D);
    }

    public void addLight(BaseLight light){
        lights.add(light);
    }

    private static void setClearColor(Vector3f color){
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

    private static void setClearColor(float x, float y, float z){
        glClearColor(x, y, z, 1.0f);
    }

    private static void setClearColor(float x){
        glClearColor(x, x, x, 1.0f);
    }

    private static void unbindTextures(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static String getOpenGLVersion(){
        return glGetString(GL_VERSION);
    }

    public Camera getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }

    public BaseLight getActiveLight(){
        return activeLight;
    }
}
