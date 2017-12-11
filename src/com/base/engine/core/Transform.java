package com.base.engine.core;

public class Transform {

    private Vector3f position;
    private Quaternion rotation;
    private Vector3f scale;

    public Transform(){
        position = new Vector3f(0,0,0);
        rotation = new Quaternion(0,0,0, 1);
        scale = new Vector3f(1, 1, 1);
    }

    public Matrix4f getTransformation(){
        Matrix4f translationMat = new Matrix4f().Translation(position.getX(), position.getY(), position.getZ());
        Matrix4f rotationMat = rotation.toRotationMatrix();//new Matrix4f().Rotation(rotation.getX(), rotation.getY(), rotation.getZ());
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

    public Quaternion getRotation() {
        return rotation;
    }

    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
