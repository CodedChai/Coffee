package com.base.engine;

public class Camera {
    public static final Vector3f yAxis = new Vector3f(0, 1, 0); // Up in the world

    private Vector3f pos;
    private Vector3f forward;
    private Vector3f up;

    public Camera(){
        this(new Vector3f(0,0,0), new Vector3f(0,0,1), new Vector3f(0,1,0));
    }

    public Camera(Vector3f pos, Vector3f forward, Vector3f up){
        this.pos = pos;
        this.forward = forward;
        this.up = up;

        up.normalize();
        forward.normalize();
    }

    public void move(Vector3f dir, float amount){
        pos = pos.add(dir.mul(amount));
    }

    public void rotateY(float angle){
        Vector3f haxis = yAxis.cross(forward);
        haxis.normalize();

        forward.rotate(angle, yAxis);
        forward.normalize();

        up = forward.cross(haxis);
        up.normalize();
    }

    // Tilting up and down
    public void rotateX(float angle){
        Vector3f haxis = yAxis.cross(forward);
        haxis.normalize();

        forward.rotate(angle, haxis);
        forward.normalize();

        up = forward.cross(haxis);
        up.normalize();
    }

    public Vector3f getLeft(){
        Vector3f left = up.cross(forward);
        left.normalize();
        return left;
    }

    public Vector3f getRight(){
        Vector3f right = forward.cross(up);
        right.normalize();
        return right;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getForward() {
        return forward;
    }

    public void setForward(Vector3f forward) {
        this.forward = forward;
    }

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up = up;
    }
}
