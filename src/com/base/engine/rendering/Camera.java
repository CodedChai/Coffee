package com.base.engine.rendering;

import com.base.engine.core.Input;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Time;
import com.base.engine.core.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    public static final Vector3f yAxis = new Vector3f(0, 1, 0); // Up in the world

    private Vector3f pos;
    private Vector3f forward;
    private Vector3f up;
    private Matrix4f projection;

    public Camera(float fov, float aspectRatio, float zNear, float zFar){
        this.pos = new Vector3f(0,0,0);
        this.forward = new Vector3f(0,0,1);
        this.up = new Vector3f(0,1,0);
        this.projection = new Matrix4f().initPerspective(fov, aspectRatio, zNear, zFar);
    }

    public Matrix4f getViewProjection(){
        Matrix4f camRotation = new Matrix4f().InitRotation(forward, up);
        Matrix4f cameraTranslation = new Matrix4f().Translation(-pos.getX(), -pos.getY(), -pos.getZ());

        return projection.mul(camRotation.mul(cameraTranslation));
    }

    public void input(){
        float movAmount = (float)(10 * Time.getDelta());
        float rotAmount = (float)(2 * Time.getDelta());

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

    // Look left and right
    public void rotateY(float angle){
        Vector3f haxis = yAxis.cross(forward).normalize();
        Vector3f y = new Vector3f(yAxis.getX(), yAxis.getY(), yAxis.getZ());
        Vector3f t = forward.rotate(angle, y);

        forward = forward.add(t);
        forward = forward.normalize();

        up = forward.cross(haxis).normalize();
        up = up.normalize();
    }

    // Tilting up and down
    public void rotateX(float angle){

        Vector3f haxis = yAxis.cross(forward).normalize();
        //System.out.println(haxis.toString());
        Vector3f t = forward.rotate(angle, haxis);
        forward = forward.add(t);

        forward = forward.normalize();

        up = forward.cross(haxis).normalize();
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
