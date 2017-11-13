package com.base.engine;

/**
 * Created by Guard on 11/10/2017.
 */
public class MainComponent {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "Coffee Engine";
    public static final boolean vsyncEnabled = true;
    public static final double FRAME_CAP = 200.0;

    private boolean isRunning;
    private Game game;

    public MainComponent(){
        isRunning = false;
        game = new Game();
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

        final double frameLength = 1.0 / FRAME_CAP;

        long lastTime = Time.getTime(); // Time of previous frame
        double unprocessedTime = 0;     // How many times to still update game
        boolean render = false;

        while(isRunning){


            long startTime = Time.getTime();    // Time of this frame
            long passedTime = startTime - lastTime; // Time it took last frame to this frame
            lastTime = startTime;

            unprocessedTime += passedTime / (double)Time.SECOND;
            //System.out.println(unprocessedTime);
            framesCounter += passedTime;

            while (unprocessedTime > frameLength){
                render = true;

                unprocessedTime -= frameLength;

                if(Window.isCloseRequested())
                    stop();

                Time.setDelta(frameLength);

                Input.update();

                game.input();
                game.update();
                if(framesCounter >= Time.SECOND){
                    System.out.println(frames);
                    frames=0;
                    framesCounter = 0;
                }
            }
            if(render){
                System.out.println("render");
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
        game.render();
        Window.render();
    }

    private void cleanUp(){
        Window.destroy();
    }

    public static void main(String[] args){
        Window.createWindow(WIDTH, HEIGHT, TITLE, vsyncEnabled);

        MainComponent mainGame = new MainComponent();

        mainGame.start();
    }

}
