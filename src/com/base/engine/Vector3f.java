package com.base.engine;

public class Vector3f {
    private float x;
    private float y;
    private float z;

    public Vector3f(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float length(){
        return (float)Math.sqrt(x * x + y * y * + z * z);
    }

    public float dot(Vector3f right){
        return x * right.getX() + y * right.getY() + z * right.getZ();
    }

    public Vector3f normalize(){
        float length = length();

        x /= length;
        y /= length;
        z /= length;

        return this;
    }

    public Vector3f cross(Vector3f right){
        float x_ = y * right.getZ() - z * right.getY();
        float y_ = z * right.getX() - x * right.getZ();
        float z_ = x * right.getY() - y * right.getX();

        return new Vector3f(x_, y_, z_);
    }

    public Vector3f rotate(){
        return null;
    }

    public Vector3f add(Vector3f right){
        return new Vector3f(x + right.getX(), y + right.getY(), z + right.getZ());
    }

    public Vector3f add(float right){
        return new Vector3f(x + right, y + right, z + right);
    }

    public Vector3f sub(Vector3f right){
        return new Vector3f(x - right.getX(), y - right.getY(), z - right.getZ());
    }

    public Vector3f sub(float right){
        return new Vector3f(x - right, y - right, z - right);
    }

    public Vector3f mul(Vector3f right){
        return new Vector3f(x * right.getX(), y * right.getY(), z * right.getZ());
    }

    public Vector3f mul(float right){
        return new Vector3f(x * right, y * right, z * right);
    }

    public Vector3f div(Vector3f right){
        return new Vector3f(x / right.getX(), y / right.getY(), z / right.getZ());
    }

    public Vector3f div(float right){
        return new Vector3f(x / right, y / right, z / right);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

}
