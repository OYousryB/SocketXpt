package io.util;

public class Profiler {

    public static long startProfile(String message){
        System.out.println(message);
        return System.currentTimeMillis();
    }

    public static void endProfile(String message, long start){
        long end = System.currentTimeMillis();
        System.out.println(message + " [" + (double)(end - start)/1000 + " seconds]");
    }
}
