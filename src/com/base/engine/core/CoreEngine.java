package com.base.engine.core;

import com.base.engine.rendering.RenderUtil;
import com.base.engine.rendering.Window;

/**
 * Created by Guard on 11/10/2017.
 */
public class CoreEngine {

    private boolean isRunning;
    private Game game;
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

    private void initializeRenderingSystem(){
        System.out.println(RenderUtil.getOpenGLVersion());
        RenderUtil.initGraphics();
    }

    public void createWindow(String title){
        Window.createWindow(width, height, title, true);
        //initializeRenderingSystem();
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

        long lastTime = Time.getTime(); // Time of previous frame
        double unprocessedTime = 0;     // How many times to still update game
        boolean render = false;
        while(isRunning){

            long startTime = Time.getTime();    // Time of this frame
            long passedTime = startTime - lastTime; // Time it took last frame to this frame
            lastTime = startTime;

            unprocessedTime += passedTime / (double)Time.SECOND;
            framesCounter += passedTime;

            while (unprocessedTime > frameTime){
                render = true;

                unprocessedTime -= frameTime;

                if(Window.isCloseRequested())
                    stop();

                Time.setDelta(frameTime);

                game.input();
                game.update();
                if(framesCounter >= Time.SECOND){
                    System.out.println(frames);
                    frames=0;
                    framesCounter = 0;
                }
            }
            if(render){
                frames++;
                render();
                render = false;
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

    private void render(){
        Window.render();
        game.render();
        Window.lateRender();
    }

    private void cleanUp(){
        Window.destroy();
    }

}
