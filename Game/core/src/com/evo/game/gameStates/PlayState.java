package com.evo.game.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.evo.game.AnimalInstance;
import com.evo.game.Game;
import com.evo.game.Loading;
import com.evo.game.TestShader;
import com.evo.game.keys.GameKeys;
import gdk.land.* ;
import gdk.*;

public class PlayState extends GameState{

    public World world;
    private PerspectiveCamera camera;
    private AnimalInstance animalInstances[];
    private ImmediateModeRenderer20 renderer;
    public Color [][] cellsColor ;
    private Vector3 around;
    private Vector3 front;

    private ModelBatch modelBatch;
    private Model box;
    private ModelInstance boxInstance;
    private Environment environment;
    private Shader shader;

    public final int SCALE = 400;
    private float zoomPlane = 0.005f ;
    private final float MIN_CAM_HEIGHT = 20f;
    private final float MAX_CAM_HEIGHT = 300f;

    private float drawTypeX;
    private float drawTypeY;

    public volatile boolean ready;
    public boolean done;

    private float[] v1, v2, v3;

    Texture grassTexture ;

    public PlayState(GameStateManager gsm){
        super(gsm);
    }

    void loadTextures() {
        //grassTexture = new Texture(Gdx.files.internal("textures/grass.jpg"));
        //grassTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void init() {

        ready = false;
        done = false;

        Loading loading = new Loading(this);

        camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 0f, 30f);
        camera.lookAt(20f, 20f, 0f);
        camera.up.x = camera.up.y = 0;
        camera.up.z = 1;
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

        v1 = new float[3];
        v2 = new float[3];
        v3 = new float[3];

        around = new Vector3(0, 0, 1);
        front  = new Vector3();

        drawTypeX = 1;
        drawTypeY = 1;

        modelBatch = new ModelBatch();

        Color colorU = new Color(), colorV = new Color();

        ModelBuilder modelBuilder = new ModelBuilder();
        TestShader.DoubleColorAttribute attr;
        Material material = new Material();

        attr = new TestShader.DoubleColorAttribute(TestShader.DoubleColorAttribute.DiffuseUV,
                colorU.set(Color.GREEN),
                colorV.set(Color.BLUE)
        );
        material.set(attr);
        box = modelBuilder.createSphere(10, 10, 10, 20, 20, GL20.GL_TRIANGLES, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
        boxInstance = new ModelInstance(box, 20f, 20f, 0);

        shader = new TestShader(true);
        shader.init();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f));
        environment.add(new DirectionalLight().set(10, 10, 10, -10, -10, -10));

    }

    @Override
    public void update(float dt) {
        front.set(camera.direction.x, camera.direction.y, camera.direction.z);
        front = front.crs(around);
        handleInput();
        if(ready && !done) {
            camera.position.set(-40f, -40f, 60f);
            camera.lookAt(30f, 30f, 0f);
            camera.up.x = camera.up.y = 0;
            camera.up.z = 1;
            done = true;
        }
        if(!done){
            float range = (float)Math.sqrt((camera.position.x - 20) * (camera.position.x - 20) + (camera.position.y - 20) * (camera.position.y - 20) + (camera.position.z) * (camera.position.z));
            float rx = 20 + (camera.position.x - 20) / range * 10;
            float ry = 20 + (camera.position.y - 20) / range * 10;
            float rz = camera.position.z / range * 10;
            camera.rotate(camera.direction, 10);
            camera.translate(- (camera.position.x - rx) / world.landscape.getDepth(), - (camera.position.y - ry) / world.landscape.getDepth(), - (camera.position.z - rz) / world.landscape.getDepth() );
        }
        if(ready && done){
            world.update();
        }
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

    AnimalInstance animalInstance ;

    @Override
    public void draw() {
        if(ready && done) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            for (int i = world.landscape.getDepth() - 1; i >= 1; --i) {
                renderer.begin(camera.combined, GL20.GL_TRIANGLE_STRIP);
                for (int j = world.landscape.getWidth() - 1; j >= 1; --j) {
                    v1[0] = (i - 1) * SCALE * zoomPlane;
                    v1[1] = j * SCALE * zoomPlane;
                    v1[2] = (float) world.landscape.getCellHeight(i - 1, j);

                    renderer.texCoord(j % 2, 0f);
                    renderer.color(cellsColor[i - 1][j]);
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
            for (int i = 0; i < world.animals.size(); i++) {
                animalInstance = new AnimalInstance(
                        world.animals.get(i).coords.x * SCALE * zoomPlane,
                        world.animals.get(i).coords.y * SCALE * zoomPlane,
                        (float) world.landscape.getCellHeight(
                                Math.round(world.animals.get(i).coords.x),
                                Math.round(world.animals.get(i).coords.y)
                        ),
                        1
                );
                for (int k = 1; k < animalInstance.quality; k++) {
                    for (int m = 1; m < animalInstance.quality; m++) {
                        drawTriangle(animalInstance.getVertice(0, k, m), animalInstance.getVertice(0, k - 1, m - 1), animalInstance.getVertice(0, k - 1, m), Color.BLUE, Color.BLACK, Color.PURPLE);
                        drawTriangle(animalInstance.getVertice(0, k, m), animalInstance.getVertice(0, k - 1, m - 1), animalInstance.getVertice(0, k, m - 1), Color.BLUE, Color.BLACK, Color.PURPLE);
                        drawTriangle(animalInstance.getVertice(1, k, m), animalInstance.getVertice(1, k - 1, m - 1), animalInstance.getVertice(1, k - 1, m), Color.BLUE, Color.BLACK, Color.PURPLE);
                        drawTriangle(animalInstance.getVertice(1, k, m), animalInstance.getVertice(1, k - 1, m - 1), animalInstance.getVertice(1, k, m - 1), Color.BLUE, Color.BLACK, Color.PURPLE);
                    }
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
        else{
            Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            modelBatch.begin(camera);
            modelBatch.render(boxInstance, environment, shader);
            modelBatch.end();
        }
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
