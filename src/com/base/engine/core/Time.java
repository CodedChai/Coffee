package com.base.engine.core;

/**
 * Created by Guard on 11/10/2017.
 */
public class Time {

    private static final long SECOND = 1000000000L;

    private static double delta;

    public static double getTime(){
        return(double) System.nanoTime()/(double)SECOND;
    }

    public static double deltaTime(){
        return delta;
    }

    public static void setDelta(double delta){
        Time.delta = delta;
    }
}
