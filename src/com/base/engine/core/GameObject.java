package com.base.engine.core;

import java.util.ArrayList;

public class GameObject {
    private ArrayList<GameObject> children;
    private ArrayList<Component> components;
    private Transform transform;

    public GameObject(){
        children = new ArrayList<GameObject>();
        components = new ArrayList<Component>();
        transform = new Transform();
    }

    public void addChild(GameObject child){
        children.add(child);
    }

    public void removeChild(GameObject child){
        children.remove(child);
    }

    public void removeChild(int index){
        children.remove(index);
    }

    public void addComponent(Component component){
        components.add(component);
    }

    public void removeComponent(Component component){
        components.remove(component);
    }

    public void removeComponent(int index){
        components.remove(index);
    }

    public void input(){
        for(Component component : components){
            component.input(transform);
        }

        for(GameObject child : children){
            child.input();
        }
    }

    public void update(){
        for(Component component : components){
            component.update(transform);
        }

        for(GameObject child : children){
            child.update();
        }
    }

    public void render(){
        for(Component component : components){
            component.render(transform);
        }

        for(GameObject child : children){
            child.render();
        }
    }

    public Transform getTransform(){
        return transform;
    }
}
