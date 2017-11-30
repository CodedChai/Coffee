package com.base.engine.core;

public class Vector2f {
    private float x;
    private float y;

    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float length(){
        return (float)Math.sqrt(x * x + y * y);
    }

    public float dot(Vector2f right){
        return x * right.getX() + y * right.getY();
    }

    public Vector2f normalize(){
        float length = length();

        return new Vector2f(x/length, x/length);
    }

    public Vector2f rotate(float angle){
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2f((float)(x * cos - y * sin), (float)(x * sin + y * cos));
    }

    public Vector2f lerp(Vector2f destination, float lerpFactor){
        return destination.sub(this).mul(lerpFactor).add(this);
    }

    public float cross(Vector2f right){
        return x * right.getY() - y * right.getX();
    }

    public Vector2f add(Vector2f right){
        return new Vector2f(x+right.getX(), y + right.getY());
    }

    public Vector2f add(float right){
        return new Vector2f(x+right, y + right);
    }

    public Vector2f sub(Vector2f right){
        return new Vector2f(x - right.getX(), y - right.getY());
    }

    public Vector2f sub(float right){
        return new Vector2f(x - right, y - right);
    }

    public Vector2f mul(Vector2f right){
        return new Vector2f(x * right.getX(), y * right.getY());
    }

    public Vector2f mul(float right){
        return new Vector2f(x * right, y * right);
    }

    public Vector2f div(Vector2f right){
        return new Vector2f(x / right.getX(), y / right.getY());
    }

    public Vector2f div(float right){
        return new Vector2f(x / right, y / right);
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public boolean equals(Vector2f right){
        return x == right.getX() && y == right.getY();
    }
}
