package com.landscape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.landscape.game.keys.GameKeys;
import com.vova.land.Land;

public class PlayState extends GameState{

    ShapeRenderer sr;
    ModelBatch modelBatch;
    PerspectiveCamera camera;
    Land landscape;
    Transformafor tr[][];
    float vertices[];
    short indices[];
    Mesh myMesh;
    Model model;
    ModelInstance instance;

    private final int SCALE = 100;
    private final int MAP_SIZE = 64;

    public PlayState(GameStateManager gsm){super(gsm);}

    @Override
    public void init() {
        landscape = new Land();
        landscape.generateNew();
        landscape.normalizeHeight();
        for( int i = 0 ; i < 5 ; ++i )
            landscape.averageHeight();
        landscape.normalizeHeight();
        landscape.setScale(SCALE) ;
        camera = new PerspectiveCamera(5, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 10f, 10f);
        camera.lookAt(0,0,0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();
        modelBatch = new ModelBatch();
        sr = new ShapeRenderer();
        sr.setProjectionMatrix(camera.combined);
        //sr.rotate(1, 0, 0, -45);
        tr = new Transformafor[landscape.getDepth()][landscape.getWidth()];
        for (int i = 1; i <= landscape.getWidth(); i++) {
            for (int j = 1; j <= landscape.getDepth(); j++) {
                tr[i-1][j-1] = new Transformafor(i * MAP_SIZE, j * MAP_SIZE, (float) landscape.getPointHeight(i, j) * MAP_SIZE / 10);
            }
        }

        vertices = new float[15];
        indices = new short[] {0, 1, 2};

        myMesh = new Mesh( true, 3, 3,
                new VertexAttribute( VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE ),
                new VertexAttribute( VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE+"0" ) );

        myMesh.setIndices(indices);

    }

    @Override
    public void update(float dt) {
        handleInput();
        for(int i = 0; i < tr.length; i++){
            for(int j = 0; j < tr[0].length; j++)
                tr[i][j].update();
        }
        camera.update();
    }

    @Override
    public void draw() {
        /*for (int i = 1; i < tr.length; i++) {
            for (int j = 1; j < tr[0].length; j++) {
                draw3DTriangle(sr, tr[i][j], tr[i - 1][j - 1], tr[i][j - 1]);
                draw3DTriangle(sr, tr[i][j], tr[i - 1][j - 1], tr[i - 1][j]);
            }
        }*/

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        for(int i = 2; i <= MAP_SIZE; i++){
            for(int j = 2; j <= MAP_SIZE; j++){
                System.out.println(landscape.getPointHeight(i, j));

                int x = 0;

                vertices[x++] = (float) i / MAP_SIZE;
                vertices[x++] = (float) j / MAP_SIZE;
                vertices[x++] = (float)landscape.getPointHeight(i, j) / SCALE;
                vertices[x++] = 0f;
                vertices[x++] = 0f;

                vertices[x++] = (float) (i - 1) / MAP_SIZE;
                vertices[x++] = (float) (j - 1) / MAP_SIZE;
                vertices[x++] = (float)landscape.getPointHeight(i - 1, j - 1) / SCALE;
                vertices[x++] = 1f;
                vertices[x++] = 0f;

                vertices[x++] = (float) (i - 1) / MAP_SIZE;
                vertices[x++] = (float) j / MAP_SIZE;
                vertices[x++] = (float)landscape.getPointHeight(i - 1, j) / SCALE;
                vertices[x++] = 1f;
                vertices[x++] = 1f;

                myMesh.setVertices(vertices);

                ModelBuilder modelBuilder = new ModelBuilder();
                modelBuilder.begin();
                modelBuilder.part("triangle", myMesh, GL20.GL_TRIANGLES, new Material(ColorAttribute.createDiffuse(Color.GREEN)));
                model = modelBuilder.end();

                instance = new ModelInstance(model);

                modelBatch.begin(camera);
                modelBatch.render(instance);
                modelBatch.end();

                x = 0;

                vertices[x++] = (float) i / MAP_SIZE;
                vertices[x++] = (float) j / MAP_SIZE;
                vertices[x++] = (float)landscape.getPointHeight(i, j) / SCALE;
                vertices[x++] = 0f;
                vertices[x++] = 0f;

                vertices[x++] = (float) (i - 1) / MAP_SIZE;
                vertices[x++] = (float) (j - 1) / MAP_SIZE;
                vertices[x++] = (float)landscape.getPointHeight(i - 1, j - 1) / SCALE;
                vertices[x++] = 1f;
                vertices[x++] = 0f;

                vertices[x++] = (float) i / MAP_SIZE;
                vertices[x++] = (float) (j - 1) / MAP_SIZE;
                vertices[x++] = (float)landscape.getPointHeight(i, j - 1) / SCALE;
                vertices[x++] = 1f;
                vertices[x++] = 1f;

                myMesh.setVertices(vertices);

                modelBuilder.begin();
                modelBuilder.part("triangle", myMesh, GL20.GL_TRIANGLES, new Material(ColorAttribute.createDiffuse(Color.GREEN)));
                model = modelBuilder.end();

                instance = new ModelInstance(model);

                modelBatch.begin(camera);
                modelBatch.render(instance);
                modelBatch.end();
            }
        }
    }

    @Override
    public void handleInput() {
        if(GameKeys.isDown(GameKeys.DOWN)){
            Transformafor.moveBot();
        }
        if(GameKeys.isDown(GameKeys.UP)){
            Transformafor.moveTop();
        }
        if(GameKeys.isDown(GameKeys.LEFT)){
            Transformafor.moveLeft();
        }
        if(GameKeys.isDown(GameKeys.RIGHT)){
            Transformafor.moveRight();
        }
        if(GameKeys.mousePressed()) {
            if (GameKeys.mouseX > GameKeys.mousePressedX)
                Transformafor.left();
            else if (GameKeys.mouseX < GameKeys.mousePressedX)
                Transformafor.right();
            if (GameKeys.mouseY > GameKeys.mousePressedY)
                Transformafor.down();
            else if (GameKeys.mouseY < GameKeys.mousePressedY)
                Transformafor.up();
        }
    }

    @Override
    public void dispose() {

    }
}
