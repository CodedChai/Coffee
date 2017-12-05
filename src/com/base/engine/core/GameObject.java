package com.base.engine.core;

import com.base.engine.components.Component;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

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

    public void input(float delta){
        for(Component component : components){
            component.input(transform, delta);
        }

        for(GameObject child : children){
            child.input(delta);
        }
    }

    public void update(float delta){
        for(Component component : components){
            component.update(transform, delta);
        }

        for(GameObject child : children){
            child.update(delta);
        }
    }

    public void render(Shader shader){
        for(Component component : components){
            component.render(transform, shader);
        }

        for(GameObject child : children){
            child.render(shader);
        }
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine){
        for(Component component : components){
            component.addToRenderingEngine(renderingEngine);
        }

        for(GameObject child : children){
            child.addToRenderingEngine(renderingEngine);
        }
    }

    public Transform getTransform(){
        return transform;
    }
}
