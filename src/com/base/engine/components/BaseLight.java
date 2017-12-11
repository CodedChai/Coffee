package com.base.engine.components;

import com.base.engine.core.Transform;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Shader;

public class BaseLight extends Component {
    private Vector3f color;
    private float intensity;
    private Shader shader;

    public BaseLight(Vector3f color, float intensity){
        this.color = color;
        this.intensity = intensity;
    }

    public void setShader(Shader shader){
        this.shader = shader;
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine){
        renderingEngine.addLight(this);
    }
    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public float getIntensity() {
        return intensity;
    }

    public Shader getShader(){
        return shader;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}
