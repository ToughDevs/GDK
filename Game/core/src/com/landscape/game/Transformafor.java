package com.landscape.game;

import com.badlogic.gdx.math.MathUtils;

public class Transformafor {

    private float inX, inY, inZ;

    private static float angle;

    private static float sideAngle;

    private static float offsetX;
    private static float offsetY;

    private static float scroll = 1;

    private float x;
    private float y;

    public Transformafor(float inX, float inY, float inZ){
        this.inX = inX;
        this.inY = inY;
        this.inZ = inZ;
        angle = MathUtils.PI / 4;
        sideAngle = 0;
        offsetX = 0;
        offsetY = 0;
        update();
    }

    public static void up(){
        if(angle < MathUtils.PI / 2)
            angle += MathUtils.PI / 180;
    }

    public static void down(){
        if(angle > 0)
            angle -= MathUtils.PI / 180;
    }

    public static void left(){
        sideAngle -= MathUtils.PI / 180;
    }

    public static void right(){
        sideAngle += MathUtils.PI / 180;
    }

    public static void moveRight(){offsetX++;}
    public static void moveLeft(){offsetX--;}
    public static void moveTop(){offsetY--;}
    public static void moveBot(){offsetY++;}

    public void update(){
        float newY = inY * scroll * MathUtils.cos(sideAngle) - (inX * scroll + offsetX) * MathUtils.sin(sideAngle);
        float newX = inY * scroll * MathUtils.sin(sideAngle) + (inX * scroll + offsetX) * MathUtils.cos(sideAngle);
        x = newX + newY * MathUtils.cos(angle) + offsetY * MathUtils.cos(angle);
        y = inZ * scroll + offsetY * MathUtils.sin(angle) + newY * MathUtils.sin(angle) + offsetY * MathUtils.cos(angle);
    }

    public static void scroll(float delta){scroll += delta;}

    public static float getAngle(){return angle;}
    public float getX(){return x;}
    public float getY(){return y;}
    public float getInZ(){return inZ;}

}
