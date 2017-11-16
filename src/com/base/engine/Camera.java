package com.base.engine;

import static org.lwjgl.glfw.GLFW.*;

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

    public void input(){
        float movAmount = (float)(10 * Time.getDelta());
        float rotAmount = (float)(100 * Time.getDelta());
        //System.out.println(forward.toString());

        if(Input.getKey(GLFW_KEY_W)){
            move(forward, movAmount);
        }
        if(Input.getKey(GLFW_KEY_S)){
            move(forward, -movAmount);
        }
        if(Input.getKey(GLFW_KEY_A)){
            move(getLeft(), movAmount);
        }
        if(Input.getKey(GLFW_KEY_D)){
            move(getRight(), movAmount);
        }

        if(Input.getKey(GLFW_KEY_UP)){

            rotateX(-rotAmount);
        }
        if(Input.getKey(GLFW_KEY_DOWN)){
            rotateX(rotAmount);
        }
        if(Input.getKey(GLFW_KEY_LEFT)){
            rotateY(-rotAmount);
        }
        if(Input.getKey(GLFW_KEY_RIGHT)){
            rotateY(rotAmount);
        }


    }

    public void move(Vector3f dir, float amount){

        pos = pos.add(dir.mul(amount));
    }

    // Currently breaks
    public void rotateY(float angle){
        Vector3f haxis = yAxis.cross(forward).normalize();
        //System.out.println(forward.toString());
        Vector3f f = forward.rotate(angle, yAxis).normalize();
        //System.out.println(forward.toString());

        up = f.cross(haxis).normalize();
    }

    // Tilting up and down
    public void rotateX(float angle){

        Vector3f haxis = yAxis.cross(forward).normalize();

        Vector3f f = forward.rotate(angle, haxis).normalize();

        up = f.cross(haxis).normalize();
    }

    public Vector3f getLeft(){
        Vector3f left = forward.cross(up);
        left.normalize();
        return left;
    }

    public Vector3f getRight(){
        Vector3f right = up.cross(forward);
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
