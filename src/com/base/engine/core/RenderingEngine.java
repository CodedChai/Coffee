package com.base.engine.core;

import com.base.engine.components.DirectionalLight;
import com.base.engine.components.PointLight;
import com.base.engine.rendering.*;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderingEngine {

    private Camera mainCamera;
    private Vector3f ambientLight;
    private DirectionalLight activeDirectionalLight;
   // private DirectionalLight directionalLight2;
    private PointLight activePointLight;
    //private PointLight[] pointLights;
    private SpotLight spotLight;

    // Permanent structures
    private ArrayList<DirectionalLight> directionalLights;
    private ArrayList<PointLight> pointLights;

    public RenderingEngine(){
        directionalLights = new ArrayList<DirectionalLight>();
        pointLights = new ArrayList<PointLight>();

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
//        activeDirectionalLight = new DirectionalLight(new BaseLight(new Vector3f(1,0,0), 0.4f),
//                new Vector3f(1,-1,1));
//        directionalLight2 = new DirectionalLight(new BaseLight(new Vector3f(0,0,1), 0.4f),
//                new Vector3f(-1,-1,1));

//        int lightFieldWidth = 10;
//        int lightFieldDepth = 10;
//        float lightFieldStartX = -5f;
//        float lightFieldStepX = 3.5f;
//        float lightFieldStartY = -3f;
//        float lightFieldStepY = 3.5f;
//
//        pointLights = new PointLight[lightFieldWidth * lightFieldDepth];
//
//        for(int i = 0; i < lightFieldWidth; i++){
//            for(int j = 0; j < lightFieldDepth; j++){
//                pointLights[i * lightFieldWidth + j] = new PointLight(new BaseLight(new Vector3f(0, 1, 0), 0.2f),
//                        new Attenuation(0,0,1),
//                        new Vector3f(lightFieldStartX + lightFieldStepX * i, 0,
//                                lightFieldStartY + lightFieldStepY * j), 100f);
//            }
//        }
//        activePointLight = pointLights[0];
//
//        spotLight = new SpotLight(new PointLight(new BaseLight(new Vector3f(0, 1, 1), 0.9f),
//                new Attenuation(0,0,1),
//                new Vector3f(lightFieldStartX, 0,
//                        lightFieldStartY), 100f), new Vector3f(1,-1,1), 0.7f);
    }

    public void input(float delta){
        mainCamera.input(delta);
    }

    public void render(GameObject object){
        clearScreen();

        clearLightList();
        object.addToRenderingEngine(this);

        Shader forwardAmbient = ForwardAmbient.getInstance();
        Shader forwardDirectional = ForwardDirectional.getInstance();
        Shader forwardPoint = ForwardPoint.getInstance();
        Shader forwardSpot = ForwardSpot.getInstance();

        forwardAmbient.setRenderingEngine(this);
        forwardDirectional.setRenderingEngine(this);
        forwardPoint.setRenderingEngine(this);
        forwardSpot.setRenderingEngine(this);

        object.render(forwardAmbient);

        // To blend in other lighting effects
        glEnable(GL_BLEND);
        // Add them together
        glBlendFunc(GL_ONE, GL_ONE);
        // Disable writing to depth buffer
        glDepthMask(false);
        // Only add lighting if they have the same exact depth value
        glDepthFunc(GL_EQUAL);

        for(DirectionalLight light : directionalLights){
            activeDirectionalLight = light;
            object.render(forwardDirectional);
        }

        for(PointLight light : pointLights){
            activePointLight = light;
            object.render(forwardPoint);
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

    private void clearLightList(){
        directionalLights.clear();
        pointLights.clear();
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

    public DirectionalLight getActiveDirectionalLight() {
        return activeDirectionalLight;
    }

    public void setActiveDirectionalLight(DirectionalLight directionalLight) {
        this.activeDirectionalLight = directionalLight;
    }

    public PointLight getActivePointLight() {
        return activePointLight;
    }

    public void setActivePointLight(PointLight activePointLight) {
        this.activePointLight = activePointLight;
    }

    public SpotLight getSpotLight() {
        return spotLight;
    }

    public void setSpotLight(SpotLight spotLight) {
        this.spotLight = spotLight;
    }

    public void addDirectionalLight(DirectionalLight directionalLight){
        directionalLights.add(directionalLight);
    }

    public void addPointLight(PointLight pointLight){
        pointLights.add(pointLight);
    }
}
