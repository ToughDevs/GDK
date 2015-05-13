package com.evo.game.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.evo.game.AnimalInstance;
import com.evo.game.Game;
import com.evo.game.keys.GameKeys;
import gdk.land.* ;
import gdk.*;

public class PlayState extends GameState{

    private World world;
    private PerspectiveCamera camera;
    private AnimalInstance animalInstances[];
    private ImmediateModeRenderer20 renderer;
    private Color [][] cellsColor ;
    private Vector3 around;
    private Vector3 front;

    private final int SCALE = 400;
    private float zoomPlane = 0.005f ;
    private final float MIN_CAM_HEIGHT = 20f;
    private final float MAX_CAM_HEIGHT = 300f;

    private float drawTypeX;
    private float drawTypeY;

    private float[] v1, v2, v3;

    Texture grassTexture ;

    public PlayState(GameStateManager gsm){
        super(gsm);
    }

    void loadTextures() {
        grassTexture = new Texture(Gdx.files.internal("textures/grass.jpg"));
        grassTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void init() {
        world = new World();
        world.generateNew(8, 32, 1000);
        world.landscape.setScale(SCALE);

        camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(-40f, -40f, 60f);
        camera.lookAt(30f, 30f, 0f);
        camera.rotate(camera.direction, 50);
        camera.near = 1f;
        camera.far = 5000f;
        camera.update();

        renderer = new ImmediateModeRenderer20(2000000, true, true, 0);

        loadTextures() ;

        /*
        animalInstances = new AnimalInstance[] {
                new AnimalInstance(5, 5, 20, 1),
                new AnimalInstance(10, 5, 20, 1),
                new AnimalInstance(5, 10, 20, 1)
        };*/

        cellsColor = new Color[world.landscape.getDepth()][world.landscape.getWidth()] ;
        for( int i = 0 ; i < world.landscape.getDepth() ; ++i )
            for( int j = 0 ; j < world.landscape.getWidth() ; ++j )
                cellsColor[i][j] = new Color(
                        world.landscape.getCellColor(i,j).getRed()/255.0f,
                        world.landscape.getCellColor(i,j).getGreen()/255.0f,
                        world.landscape.getCellColor(i,j).getBlue()/255.0f, 0
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
        world.update() ;
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

    AnimalInstance animalInstance ;

    @Override
    public void draw() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for( int i = world.landscape.getDepth()-1 ; i >= 1 ; --i ) {
            renderer.begin(camera.combined, GL20.GL_TRIANGLE_STRIP);
            for (int j = world.landscape.getWidth()-1; j >= 1; --j) {
                v1[0] = (i-1) * SCALE * zoomPlane;
                v1[1] = j * SCALE * zoomPlane;
                v1[2] = (float) world.landscape.getCellHeight(i-1, j);

                renderer.texCoord(j%2, 0f);
                renderer.color(cellsColor[i-1][j]);
                renderer.vertex(v1[0], v1[1], v1[2]);

                v1[0] = i * SCALE * zoomPlane;
                v1[1] = j * SCALE * zoomPlane;
                v1[2] = (float) world.landscape.getCellHeight(i, j);

                renderer.texCoord(j % 2, 1f);
                renderer.color(cellsColor[i][j]);
                renderer.vertex(v1[0], v1[1], v1[2]);
            }
            renderer.end();
        }

        renderer.begin(camera.combined, GL20.GL_TRIANGLES);
        for(int i = 0; i < world.animals.size(); i++){
            animalInstance = new AnimalInstance(
                    world.animals.get(i).coords.x * SCALE * zoomPlane,
                    world.animals.get(i).coords.y * SCALE * zoomPlane,
                    (float) world.landscape.getCellHeight(
                            Math.round(world.animals.get(i).coords.x),
                            Math.round(world.animals.get(i).coords.y)
                    ),
                    1
            );
            for(int j = 0; j < 8; j++){
                float triangle[][] = animalInstance.getVertice(j);
                drawTriangle(triangle[0], triangle[1], triangle[2], Color.BLUE, Color.BLACK, Color.PURPLE);
            }
        }
        /*
        for(int i = 0; i < animalInstances.length; i++){
            for(int j = 0; j < 8; j++){
                float triangle[][] = animalInstances[i].getVertice(j);
                drawTriangle(triangle[0], triangle[1], triangle[2], Color.BLUE, Color.BLACK, Color.PURPLE);
            }
        }*/
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
