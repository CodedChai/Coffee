package com.base.engine;

public class Transform {

    private Vector3f translation;
    private Vector3f rotation;
    private Vector3f scale;

    public Transform(){
        translation = new Vector3f(0,0,0);
        rotation = new Vector3f(0,0,0);
        scale = new Vector3f(1, 1, 1);
    }

    public Matrix4f getTransformation(){
        Matrix4f translationMat = new Matrix4f().Translation(translation.getX(), translation.getY(), translation.getZ());
        Matrix4f rotationMat = new Matrix4f().Rotation(rotation.getX(), rotation.getY(), rotation.getZ());
        Matrix4f scaleMat = new Matrix4f().Scale(scale.getX(), scale.getY(), scale.getZ());

        return translationMat.mul(rotationMat.mul(scaleMat));
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public void setTranslation(float x, float y, float z) {
        this.translation = new Vector3f(x, y, z);
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation = new Vector3f(x, y, z);
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setScale(float x, float y, float z) {
        this.scale = new Vector3f(x, y, z);
    }
}
