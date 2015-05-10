package com.landscape.game.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector3;
import com.landscape.game.Game;
import com.landscape.game.keys.GameKeys;
import gdk.land.* ;

public class PlayState extends GameState{

    private PerspectiveCamera camera;
    private Land landscape;
    private ImmediateModeRenderer20 renderer;
    private Color [][] cellsColor ;
    private Vector3 around;
    private Vector3 front;

    private final int SCALE = 10;
    private float zoomPlane = 0.01f ;
    private final float MIN_CAM_HEIGHT = 20f;
    private final float MAX_CAM_HEIGHT = 300f;

    private float drawTypeX;
    private float drawTypeY;

    private float[] v1, v2, v3;

    public PlayState(GameStateManager gsm){super(gsm);}

    @Override
    public void init() {
        camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0, 0, 30f);
        camera.lookAt(20f, 20f, 0f);
        camera.rotate(camera.direction, 50);
        camera.near = 1f;
        camera.far = 5000f;
        camera.update();

        renderer = new ImmediateModeRenderer20(20000000, false, true, 0);

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

        v1 = new float[3];
        v2 = new float[3];
        v3 = new float[3];

        around = new Vector3(0, 0, 1);
        front  = new Vector3();

        drawTypeX = 1;
        drawTypeY = 1;
    }

    @Override
    public void update(float dt) {
        front.set(camera.direction.x, camera.direction.y, camera.direction.z);
        front = front.crs(around);
        handleInput();
        camera.update();
    }

    @Override
    public void scroll(int amount){
        if(camera.position.z > MIN_CAM_HEIGHT && amount > 0 || amount < 0 && camera.position.z < MAX_CAM_HEIGHT)
            camera.translate(camera.direction.x * amount, camera.direction.y * amount, camera.direction.z * amount);
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

        int startI = 0;
        int finishI = landscape.getDepth();
        int iteratorI = 1;
        int startJ = 0;
        int finishJ = landscape.getWidth();
        int iteratorJ = 1;

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.begin(camera.combined, GL20.GL_TRIANGLES);
        if(drawTypeX < 0 && drawTypeY < 0) {
            startI = 1;
            finishI = landscape.getDepth();
            iteratorI = 1;
            startJ = 1;
            finishJ = landscape.getWidth();
            iteratorJ = 1;
        }else if(drawTypeX < 0 && drawTypeY >= 0){
            startI = 1;
            finishI = landscape.getDepth();
            iteratorI = 1;
            startJ = landscape.getWidth() - 1;
            finishJ = 0;
            iteratorJ = -1;
        }else if(drawTypeX >= 0 && drawTypeY < 0){
            startI = landscape.getDepth() - 1;
            finishI = 0;
            iteratorI = -1;
            startJ = 1;
            finishJ = landscape.getWidth();
            iteratorJ = 1;
        }else{
            startI = landscape.getDepth() - 1;
            finishI = 0;
            iteratorI = -1;
            startJ = landscape.getWidth() - 1;
            finishJ = 0;
            iteratorJ = -1;
        }
        for (int i = startI; Math.abs(finishI - i) >= 1; i += iteratorI)
            for (int j = startJ; Math.abs(finishJ - j) >= 1; j += iteratorJ) {
                v1[0] = i * SCALE * zoomPlane;
                v1[1] = j * SCALE * zoomPlane;
                v1[2] = (float) landscape.getCellHeight(i, j);
                v2[0] = (i - 1) * SCALE * zoomPlane;
                v2[1] = (j - 1) * SCALE * zoomPlane;
                v2[2] = (float) landscape.getCellHeight(i - 1, j - 1);

                v3[0] = (i - 1) * SCALE * zoomPlane;
                v3[1] = j * SCALE * zoomPlane;
                v3[2] = (float) landscape.getCellHeight(i - 1, j);
                drawTriangle(v1, v2, v3, cellsColor[i][j], cellsColor[i - 1][j - 1], cellsColor[i - 1][j]);

                v3[0] = i * SCALE * zoomPlane;
                v3[1] = (j - 1) * SCALE * zoomPlane;
                v3[2] = (float) landscape.getCellHeight(i, j - 1);
                drawTriangle(v1, v2, v3, cellsColor[i][j], cellsColor[i - 1][j - 1], cellsColor[i][j - 1]);
            }
        renderer.end();
    }

    @Override
    public void handleInput() {
        if(GameKeys.isDown(GameKeys.DOWN)){
            camera.translate(-camera.direction.x, -camera.direction.y, 0);
        }
        if(GameKeys.isDown(GameKeys.UP)){
            camera.translate(camera.direction.x, camera.direction.y, 0);
        }
        if(GameKeys.isDown(GameKeys.LEFT)){
            camera.translate(-front.x / front.len(), -front.y / front.len(), 0);
        }
        if(GameKeys.isDown(GameKeys.RIGHT)) {
            camera.translate(front.x / front.len(), front.y / front.len(), 0);
        }
        if(GameKeys.mousePressed()) {
            if (GameKeys.mouseX > Game.WIDTH - Game.WIDTH / 10) {
                camera.rotate(around, -1);
            } else if (GameKeys.mouseX < Game.WIDTH / 10) {
                camera.rotate(around, 1);
            }
            if (GameKeys.mouseY > Game.HEIGHT - Game.HEIGHT / 10) {
                camera.rotate(front, -1);
            } else if (GameKeys.mouseY < Game.HEIGHT / 10) {
                camera.rotate(front, 1);
            }
        }
        if(GameKeys.isPressed(GameKeys.ESCAPE)){
            gsm.setState(gsm.MENU);
        }
        if(GameKeys.isPressed(GameKeys.ENTER)){
            drawTypeY = -drawTypeY;
        }
        if(GameKeys.isPressed(GameKeys.SHIFT)){
            drawTypeX = -drawTypeX;
        }
    }

    @Override
    public void dispose() {

    }
}
