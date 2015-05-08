package com.landscape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector3;
import com.landscape.game.keys.GameKeys;
import gdk.land.* ;

public class PlayState extends GameState{

    PerspectiveCamera camera;
    Land landscape;
    ImmediateModeRenderer20 renderer;
    Color [][] cellsColor ;

    public static float camX, camY, camZ;
    public static float camLookX, camLookY, camLookZ;

    private final int SCALE = 10;
    private float zoomPlane = 0.01f ;

    private float[] v1, v2, v3;

    public PlayState(GameStateManager gsm){super(gsm);}

    @Override
    public void init() {
        landscape = new Land();
        landscape.generateNew();
        landscape.setScale(SCALE) ;

        cellsColor = new Color[landscape.getDepth()][landscape.getWidth()] ;
        for( int i = 0 ; i < landscape.getDepth() ; ++i )
            for( int j = 0 ; j < landscape.getWidth() ; ++j )
                cellsColor[i][j] = new Color(
                        landscape.getCellColor(i,j).getRed()/255.0f,
                        landscape.getCellColor(i,j).getGreen()/255.0f,
                        landscape.getCellColor(i,j).getBlue()/255.0f, 0
                ) ;

        v1 = v2 = v3  = new float[3];

        camX = 0;
        camY = 0f;
        camZ = 800f;
        camLookX = 0;
        camLookY = 5f;
        camLookZ = 0f;

        camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camX, camY, camZ);
        camera.lookAt(camLookX, camLookY, camLookZ);
        camera.near = 10f;
        camera.far = 5000f;
        camera.update();

        renderer = new ImmediateModeRenderer20(20000000, false, true, 0);
    }

    @Override
    public void update(float dt) {
        handleInput();
        camera.position.set(camX, camY, camZ);
        camera.lookAt(camLookX, camLookY, camLookZ);
        camera.update();
    }

    void drawTriangle(float []v1, float []v2, float []v3, Color c1, Color c2, Color c3) {
        renderer.color(c1);
        renderer.vertex(v1[0], v1[1], v1[2]);
        renderer.color(c2);
        renderer.vertex(v2[0], v2[1], v2[2]);
        renderer.color(c3);
        renderer.vertex(v3[0], v3[1], v3[2]);
    }

    @Override
    public void draw() {

        renderer.begin(camera.combined, GL20.GL_TRIANGLES);
        for( int i = 1 ; i < landscape.getDepth() ; ++i )
            for( int j = 1 ; j < landscape.getWidth() ; ++j ) {
                System.out.println(String.format("Coords: %.2f %.2f %.2f", i * SCALE * zoomPlane , j * SCALE * zoomPlane, (float) landscape.getCellHeight(i, j)));
                v1[0] = i * SCALE * zoomPlane ;
                v1[2] = j * SCALE * zoomPlane ;
                v1[1] = (float) landscape.getCellHeight(i, j) ;
                v2[0] = (i-1) * SCALE * zoomPlane ;
                v2[2] = (j-1) * SCALE * zoomPlane ;
                v2[1] = (float) landscape.getCellHeight(i-1, j-1) ;

                v3[0] = (i-1) * SCALE * zoomPlane ;
                v3[2] = j * SCALE * zoomPlane ;
                v3[1] = (float) landscape.getCellHeight(i-1, j) ;
                drawTriangle(v1, v2, v3, cellsColor[i][j], cellsColor[i-1][j-1], cellsColor[i-1][j]);

                v3[0] = i * SCALE * zoomPlane ;
                v3[2] = (j-1) * SCALE * zoomPlane ;
                v3[1] = (float) landscape.getCellHeight(i, j-1) ;
                drawTriangle(v1, v2, v3, cellsColor[i][j], cellsColor[i-1][j-1], cellsColor[i][j-1]);
            }
        renderer.end();
    }

    @Override
    public void handleInput() {
        //System.out.println(String.format("Cam direction: %.2f %.2f %.2f", camera.direction.x, camera. direction.y, camera.direction.z));
        //System.out.println(String.format("Cam position: %.2f %.2f %.2f", camera.position.x, camera. position.y, camera.position.z));
        if(GameKeys.isDown(GameKeys.DOWN)){
            camY -= 10f;
        }
        if(GameKeys.isDown(GameKeys.UP)){
            camY += 10f;
        }
        if(GameKeys.isDown(GameKeys.LEFT)){
            camX -= 10f;
        }
        if(GameKeys.isDown(GameKeys.RIGHT)) {
            camX += 10f;
        }
        if(GameKeys.mouseX > Landscape.WIDTH - Landscape.WIDTH / 10){
            camera.rotate(new Vector3(0, 0, 1f), -2);
        }
        else if(GameKeys.mouseX < Landscape.WIDTH / 10){
            camera.rotate(new Vector3(0, 0, 1f), 2);
        }
        if(GameKeys.mouseY > Landscape.HEIGHT - Landscape.HEIGHT / 10){
            //System.out.println(camLookX + " " + camLookY + " " + camLookZ);
            camLookX += (camera.position.x - camera.direction.x) / 100f;
            camLookY += (camera.position.y - camera.direction.y) / 100f;
            camLookZ += (camera.position.z - camera.direction.z) / 100f;
        }
        else if(GameKeys.mouseY < Landscape.HEIGHT / 10){
            //System.out.println(camLookX + " " + camLookY + " " + camLookZ);
            camLookX -= (camera.position.x - camera.direction.x) / 100f;
            camLookY -= (camera.position.y - camera.direction.y) / 100f;
            camLookZ -= (camera.position.z - camera.direction.z) / 100f;
        }
    }

    @Override
    public void dispose() {

    }
}
