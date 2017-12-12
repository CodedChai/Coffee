package com.base.game;

import com.base.engine.core.CoreEngine;

public class Main {
    public static void main(String[] args){
        CoreEngine engine = new CoreEngine(2560, 1440, 60, new TestGame());
        engine.createWindow("Coffee");
        engine.start();
    }
}
