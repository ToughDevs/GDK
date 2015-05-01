package com.landscape.game.keys;

public class GameKeys {
    private static boolean keys[];
    private static boolean pkeys[];

    public static int mouseX;
    public static int mouseY;

    private static boolean mouseDown = false;
    public static int mousePressedX;
    public static int mousePressedY;

    private static int NUM_KEYS = 8;

    public static int UP = 0;
    public static int RIGHT = 1;
    public static int DOWN = 2;
    public static int LEFT = 3;
    public static int SPACE = 4;
    public static int SHIFT = 5;
    public static int ENTER = 6;
    public static int ESCAPE = 7;

    static{
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
    }

    public static void update(){
        for(int i = 0; i < NUM_KEYS; i++){
            pkeys[i] = keys[i];
        }
    }

    public static void setKey(int k, boolean b){
        keys[k] = b;
    }
    public static void pressMouse(boolean b){
        mouseDown = b;
    }

    public static boolean mousePressed(){return mouseDown;}

    public static boolean isDown(int k){
        return keys[k];
    }
    public static boolean isPressed(int k){
        return keys[k] && !pkeys[k];
    }
}