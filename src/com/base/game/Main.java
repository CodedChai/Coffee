package com.base.game;

import com.base.engine.core.CoreEngine;

public class Main {
    public static void main(String[] args){
        CoreEngine engine = new CoreEngine(1280, 720, 60, new TestGame());
        engine.createWindow("Coffee");
        engine.start();
    }
}
