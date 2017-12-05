package com.base.engine.core;

import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Window;

/**
 * Created by Guard on 11/10/2017.
 */
public class CoreEngine {

    private boolean isRunning;
    private Game game;
    private RenderingEngine renderingEngine;
    private int width;
    private int height;
    private double frameTime;

    public CoreEngine(int width, int height, double framerate, Game game){
        this.isRunning = false;
        this.width = width;
        this.height = height;
        this.frameTime = 1.0/framerate;
        this.game = game;
    }

    public void createWindow(String title){
        Window.createWindow(width, height, title, true);
        this.renderingEngine = new RenderingEngine();
    }

    public void start(){
        if(isRunning)
            return;

        isRunning = true;

        run();
    }

    public void stop(){
        if(!isRunning)
            return;

        isRunning = false;
    }

    private void run(){
        int frames = 0; // Framerate
        long framesCounter = 0;

        game.init();

        double lastTime = Time.getTime(); // Time of previous frame
        double unprocessedTime = 0;     // How many times to still update game
        boolean render = false;
        while(isRunning){

            double startTime = Time.getTime();    // Time of this frame
            double passedTime = startTime - lastTime; // Time it took last frame to this frame
            lastTime = startTime;

            unprocessedTime += passedTime;
            framesCounter += passedTime;

            while (unprocessedTime > frameTime){
                render = true;
                Time.setDelta(frameTime);
                unprocessedTime -= frameTime;

                if(Window.isCloseRequested())
                    stop();

                game.input((float)frameTime);
                renderingEngine.input((float)frameTime);
                game.update((float)frameTime);
                if(framesCounter >= 1.0){
                    System.out.println(frames);
                    frames=0;
                    framesCounter = 0;
                }
            }
            if(render){
                renderingEngine.render(game.getRootObject());
                Window.render();
                frames++;
            }
            else {
                try {
                    Thread.sleep(1);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        cleanUp();
    }


    private void cleanUp(){
        Window.destroy();
    }

}
