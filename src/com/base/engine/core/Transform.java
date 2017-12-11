package com.base.engine.core;

public class Transform {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    public Transform(){
        position = new Vector3f(0,0,0);
        rotation = new Vector3f(0,0,0);
        scale = new Vector3f(1, 1, 1);
    }

    public Matrix4f getTransformation(){
        Matrix4f translationMat = new Matrix4f().Translation(position.getX(), position.getY(), position.getZ());
        Matrix4f rotationMat = new Matrix4f().Rotation(rotation.getX(), rotation.getY(), rotation.getZ());
        Matrix4f scaleMat = new Matrix4f().Scale(scale.getX(), scale.getY(), scale.getZ());

        return translationMat.mul(rotationMat.mul(scaleMat));
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPosition(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
    }


    public void setTranslation(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
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
