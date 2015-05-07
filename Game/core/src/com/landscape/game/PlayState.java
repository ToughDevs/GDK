package com.landscape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.landscape.game.keys.GameKeys;
import com.vova.land.Land;

public class PlayState extends GameState{

    ModelBatch modelBatch;
    PerspectiveCamera camera;
    Land landscape;
    float vertices[];
    short indices[];
    Mesh myMesh;
    Model model;
    ModelInstance instance;
    String vertexShader;
    String fragmentShader;
    ShaderProgram shader;

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
        camera.position.set(0.5f, 5f, 10f);
        camera.lookAt(0.5f, 0.5f, 0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();
        modelBatch = new ModelBatch();

        vertices = new float[18];

        vertices[4] = vertices[5] = vertices[10] = vertices[11] = vertices[16] = vertices[17] = 1f;

        indices = new short[] {0, 1, 2};

        myMesh = new Mesh( true, 3, 3,
                new VertexAttribute( VertexAttributes.Usage.ColorPacked, 4, "a_color" ),
                new VertexAttribute( VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE ),
                new VertexAttribute( VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE+"0" ) );

        myMesh.setIndices(indices);

        vertexShader = "attribute vec4 a_position;    \n" +
                "attribute vec4 a_color;\n" +
                "attribute vec2 a_texCoord0;\n" +
                "uniform mat4 u_worldView;\n" +
                "varying vec4 v_color;" +
                "varying vec2 v_texCoords;" +
                "void main()                  \n" +
                "{                            \n" +
                "   v_color = a_color; \n" +
                "   v_texCoords = a_texCoord0; \n" +
                "   gl_Position =  u_worldView * a_position;  \n"      +
                "}                            \n" ;
        fragmentShader = "#ifdef GL_ES\n" +
                "precision mediump float;\n" +
                "#endif\n" +
                "varying vec4 v_color;\n" +
                "varying vec2 v_texCoords;\n" +
                //"uniform sampler2D u_texture;\n" +
                "void main()                                  \n" +
                "{                                            \n" +
                "  gl_FragColor = v_color;\n" + // * texture2D(u_texture, v_texCoords);\n" +
                "}";

        shader = new ShaderProgram(vertexShader, fragmentShader);

    }

    @Override
    public void update(float dt) {
        handleInput();
        camera.update();
    }

    @Override
    public void draw() {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        for(int i = 2; i <= MAP_SIZE; i++){
            for(int j = 2; j <= MAP_SIZE; j++){

                System.out.println(Color.RED.toFloatBits());

                vertices[0] = (float) i / MAP_SIZE;
                vertices[1] = (float) j / MAP_SIZE;
                vertices[2] = (float) landscape.getPointHeight(i, j) / SCALE;
                vertices[3] = Color.toFloatBits(0f, (float) landscape.getPointHeight(i, j) / SCALE * 255, 0f, 255f);
                //vertices[3] = Color.YELLOW.toFloatBits();

                vertices[6] = (float) (i - 1) / MAP_SIZE;
                vertices[7] = (float) (j) / MAP_SIZE;
                vertices[8] = (float) landscape.getPointHeight(i - 1, j) / SCALE;
                vertices[9] = Color.toFloatBits(0f, (float) landscape.getPointHeight(i - 1, j) / SCALE * 255, 0f, 255f);

                vertices[12] = (float) (i - 1) / MAP_SIZE;
                vertices[13] = (float) (j - 1) / MAP_SIZE;
                vertices[14] = (float) landscape.getPointHeight(i - 1, j - 1) / SCALE;
                vertices[15] = Color.toFloatBits(0f, (float) landscape.getPointHeight(i - 1, j - 1) / SCALE * 255, 0f, 255f);

                myMesh.setVertices(vertices);

                ModelBuilder modelBuilder = new ModelBuilder();
                modelBuilder.begin();
                modelBuilder.part("triangle", myMesh, GL20.GL_TRIANGLES, new Material(ColorAttribute.createDiffuse(Color.GREEN)));
                model = modelBuilder.end();

                instance = new ModelInstance(model);

                /*modelBatch.begin(camera);
                modelBatch.render(instance);
                modelBatch.end();*/

                shader.begin();
                shader.setUniformMatrix("u_worldView", camera.combined);
                myMesh.render(shader, GL20.GL_TRIANGLES);
                shader.end();


                vertices[0] = (float) i / MAP_SIZE;
                vertices[1] = (float) j / MAP_SIZE;
                vertices[2] = (float)landscape.getPointHeight(i, j) / SCALE;
                vertices[3] = Color.toFloatBits(0f, (float) landscape.getPointHeight(i, j) / SCALE * 255, 0f, 255f);

                vertices[6] = (float) (i - 1) / MAP_SIZE;
                vertices[7] = (float) (j - 1) / MAP_SIZE;
                vertices[8] = (float)landscape.getPointHeight(i - 1, j - 1) / SCALE;
                vertices[9] = Color.toFloatBits(0f, (float) landscape.getPointHeight(i - 1, j - 1) / SCALE * 255f, 0f, 255f);

                vertices[12] = (float) i / MAP_SIZE;
                vertices[13] = (float) (j - 1) / MAP_SIZE;
                vertices[14] = (float)landscape.getPointHeight(i, j - 1) / SCALE;
                vertices[15] = Color.toFloatBits(0f, (float) landscape.getPointHeight(i, j - 1) / SCALE * 255, 0f, 255f);

                myMesh.setVertices(vertices);

                modelBuilder.begin();
                modelBuilder.part("triangle", myMesh, GL20.GL_TRIANGLES, new Material(ColorAttribute.createDiffuse(Color.GREEN)));
                model = modelBuilder.end();

                instance = new ModelInstance(model);

                /*modelBatch.begin(camera);
                modelBatch.render(instance);
                modelBatch.end();*/

                shader.begin();
                shader.setUniformMatrix("u_worldView", camera.combined);
                myMesh.render(shader, GL20.GL_TRIANGLES);
                shader.end();

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
