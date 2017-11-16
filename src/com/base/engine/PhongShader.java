package com.base.engine;

import org.lwjglx.Sys;

public class PhongShader extends Shader {
    private static final PhongShader instance = new PhongShader();

    private static final int MAX_POINT_LIGHTS = 5;

    public static PhongShader getInstance(){
        return instance;
    }

    private static Vector3f ambientLight = new Vector3f(0.15f, 0.15f, 0.15f);
    private static DirectionalLight directionalLight = new DirectionalLight(new BaseLight(new Vector3f(1,1,1), 0), new Vector3f(0,0,0));

    private static PointLight[] pointLights = new PointLight[]{};

    public PhongShader(){
        super();

        addVertexShader(ResourceLoader.loadShader("phongVertex.vs"));
        addFragmentShader(ResourceLoader.loadShader("phongFragment.fs"));
        compileShader();

        addUniform("transform");
        addUniform("transformProjected");
        addUniform("baseColor");
        addUniform("ambientLight");

        addUniform("specularIntensity");
        addUniform("specularPower");
        addUniform("eyePos");

        addUniform("directionalLight.base.color");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");

        for(int i = 0; i < MAX_POINT_LIGHTS; i++){
            addUniform("pointLights[" + i + "].base.color");
            addUniform("pointLights[" + i + "].base.intensity");
            addUniform("pointLights[" + i + "].atten.constant");
            addUniform("pointLights[" + i + "].atten.linear");
            addUniform("pointLights[" + i + "].atten.exponent");
            addUniform("pointLights[" + i + "].position");
            addUniform("pointLights[" + i + "].range");
        }
    }

    @Override
    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material){
        if(material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();

        setUniform("transformProjected", projectedMatrix);
        setUniform("transform", worldMatrix);
        setUniform("baseColor", material.getColor());

        setUniformf("specularIntensity", material.getSpecularIntensity());
        setUniformf("specularPower", material.getSpecularPower());
        setUniform("eyePos", Transform.getCamera().getPos());

        setUniform("ambientLight", ambientLight);
        setUniform("directionalLight", directionalLight);
        for(int i = 0; i < pointLights.length; i++){
            setUniform("pointLights[" + i + "]", pointLights[i]);
        }

    }

    public void setUniform(String uniformName, PointLight pointLight){
        setUniform(uniformName + ".base", pointLight.getBaseLight());
        setUniformf(uniformName + ".atten.constant", pointLight.getAtten().getConstant());
        setUniformf(uniformName + ".atten.linear", pointLight.getAtten().getLinear());
        setUniformf(uniformName + ".atten.exponent", pointLight.getAtten().getExponent());
        setUniform(uniformName + ".position", pointLight.getPosition());
        setUniformf(uniformName + ".range", pointLight.getRange());
    }

    public static Vector3f getAmbientLight() {
        return ambientLight;
    }

    public static void setAmbientLight(Vector3f ambientLight) {
        PhongShader.ambientLight = ambientLight;
    }

    public static void setDirectionalLight(DirectionalLight directionalLight) {
        PhongShader.directionalLight = directionalLight;
    }

    public static void setPointLight(PointLight[] pointLights){
        if(pointLights.length > MAX_POINT_LIGHTS){
            System.err.println("Error: Too many point lights passed in. Max allowed it: " + MAX_POINT_LIGHTS
            + " You passed in: " + pointLights.length);
            new Exception().printStackTrace();
            System.exit(1);
        }

        PhongShader.pointLights = pointLights;
    }

    public void setUniform(String uniformName, BaseLight baseLight){
        setUniform(uniformName + ".color", baseLight.getColor());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    public void setUniform(String uniformName, DirectionalLight directionalLight){
        setUniform(uniformName + ".base", directionalLight.getBase());
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }
}
