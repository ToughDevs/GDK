package com.evo.game;

/**
 * Created by Рома on 12.05.2015.
 */
public class AnimalInstance {

    final public int quality = 8;

    private float x;
    private float y;
    private float z;
    private float r;

    private float vertices[][][][];

    public AnimalInstance(float x, float y, float z, float r){
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;

        buildVertices();
    }

    private void buildVertices(){

        vertices = new float[2][quality][quality][3];

        float k = r / (quality / 2);

        for(int i = 0; i < quality; i++){
            for(int j = 0; j < quality; j++){
                vertices[0][i][j][0] = vertices[1][i][j][0] = x + (i - quality / 2) * k;
                vertices[0][i][j][1] = vertices[1][i][j][1] = y + (j - quality / 2) * k;
                vertices[0][i][j][2] = (float)Math.sqrt(r * r - (vertices[0][i][j][0] - x) * (vertices[0][i][j][0] - x)
                        - (vertices[0][i][j][1] - y) * (vertices[0][i][j][1] - y)) + z;

                vertices[1][i][j][2] = - (float)Math.sqrt(r * r - (vertices[0][i][j][0] - x) * (vertices[0][i][j][0] - x)
                        - (vertices[0][i][j][1] - y) * (vertices[0][i][j][1] - y)) + z;
            }
        }
    }

    public float[] getVertice(int side, int i, int j){
        return vertices[side][i][j];
    }

}
