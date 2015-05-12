package com.landscape.game;

/**
 * Created by Рома on 12.05.2015.
 */
public class AnimalInstance {

    private float x;
    private float y;
    private float z;
    private float r;

    private float vertices[][][];

    public AnimalInstance(float x, float y, float z, float r){
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;

        buildVertices();
    }

    private void buildVertices(){

        vertices = new float[8][3][3];

        int k = 0;

        vertices[k][0][0] = x - r;
        vertices[k][0][1] = y;
        vertices[k][0][2] = z;

        vertices[k][1][0] = x;
        vertices[k][1][1] = y;
        vertices[k][1][2] = z + r;

        vertices[k][2][0] = x;
        vertices[k][2][1] = y - r;
        vertices[k][2][2] = z;

        k++;

        vertices[k][0][0] = x - r;
        vertices[k][0][1] = y;
        vertices[k][0][2] = z;

        vertices[k][1][0] = x;
        vertices[k][1][1] = y;
        vertices[k][1][2] = z + r;

        vertices[k][2][0] = x;
        vertices[k][2][1] = y + r;
        vertices[k][2][2] = z;

        k++;

        vertices[k][0][0] = x - r;
        vertices[k][0][1] = y;
        vertices[k][0][2] = z;

        vertices[k][1][0] = x;
        vertices[k][1][1] = y;
        vertices[k][1][2] = z - r;

        vertices[k][2][0] = x;
        vertices[k][2][1] = y - r;
        vertices[k][2][2] = z;

        k++;

        vertices[k][0][0] = x - r;
        vertices[k][0][1] = y;
        vertices[k][0][2] = z;

        vertices[k][1][0] = x;
        vertices[k][1][1] = y;
        vertices[k][1][2] = z - r;

        vertices[k][2][0] = x;
        vertices[k][2][1] = y + r;
        vertices[k][2][2] = z;

        k++;

        vertices[k][0][0] = x + r;
        vertices[k][0][1] = y;
        vertices[k][0][2] = z;

        vertices[k][1][0] = x;
        vertices[k][1][1] = y;
        vertices[k][1][2] = z + r;

        vertices[k][2][0] = x;
        vertices[k][2][1] = y - r;
        vertices[k][2][2] = z;

        k++;

        vertices[k][0][0] = x + r;
        vertices[k][0][1] = y;
        vertices[k][0][2] = z;

        vertices[k][1][0] = x;
        vertices[k][1][1] = y;
        vertices[k][1][2] = z + r;

        vertices[k][2][0] = x;
        vertices[k][2][1] = y + r;
        vertices[k][2][2] = z;

        k++;

        vertices[k][0][0] = x + r;
        vertices[k][0][1] = y;
        vertices[k][0][2] = z;

        vertices[k][1][0] = x;
        vertices[k][1][1] = y;
        vertices[k][1][2] = z - r;

        vertices[k][2][0] = x;
        vertices[k][2][1] = y - r;
        vertices[k][2][2] = z;

        k++;

        vertices[k][0][0] = x + r;
        vertices[k][0][1] = y;
        vertices[k][0][2] = z;

        vertices[k][1][0] = x;
        vertices[k][1][1] = y;
        vertices[k][1][2] = z - r;

        vertices[k][2][0] = x;
        vertices[k][2][1] = y + r;
        vertices[k][2][2] = z;
    }

    public float[][] getVertice(int k){
        return vertices[k];
    }

}
