package com.landscape.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.landscape.game.keys.GameKeys;
import gdk.land.* ;

public class PlayState extends GameState{

    ModelBatch modelBatch;
    PerspectiveCamera camera;
    Land landscape;
    float vertices[];
    short indices[];
    Mesh myMesh1[][];
    Mesh myMesh2[][];
    //Model model;
    //ModelInstance instance;
    String vertexShader;
    String fragmentShader;
    ShaderProgram shader;

    public static float camX, camY, camZ;
    public static float camLookX, camLookY, camLookZ;

    private final int SCALE = 200;
    private final int MAP_SIZE = 64;

    public PlayState(GameStateManager gsm){super(gsm);}

    @Override
    public void init() {
        landscape = new Land();
        landscape.generateNew();
        landscape.setScale(SCALE) ;

        camX = landscape.getDepth()/2;
        camY = 1000f;
        camZ = 10f;
        camLookX = landscape.getDepth()/2;
        camLookY = landscape.getWidth()/2;
        camLookZ = 0f;

        camera = new PerspectiveCamera(5, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camX, camY, camZ);
        camera.lookAt(camLookX, camLookY, camLookZ);
        camera.near = 10f;
        camera.far = 5000f;
        camera.update();
        modelBatch = new ModelBatch();

        vertices = new float[18];

        vertices[4] = vertices[5] = vertices[10] = vertices[11] = vertices[16] = vertices[17] = 1f;

        indices = new short[] {0, 1, 2};

        myMesh1 = new Mesh[landscape.getDepth()][landscape.getWidth()];
        myMesh2 = new Mesh[landscape.getDepth()][landscape.getWidth()];

        /*vertexShader = "attribute vec4 a_position;    \n" +
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
                "}                           \n" ;*/
        vertexShader = "attribute vec4 a_position;\n" +
                "attribute vec4 a_color;\n" +
                "attribute vec2 a_texCoord;\n" +
                "\n" +
                "uniform mat4 u_projTrans;\n" +
                "\n" +
                "varying vec4 v_color;\n" +
                "varying vec2 v_texCoords;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    v_color = a_color;\n" +
                "    v_color.a = v_color.a * (256.0/255.0);\n" +
                "    v_texCoords = a_texCoord + 0;\n" +
                "    gl_Position =  u_projTrans * a_position;\n" +
                "}";
        /*fragmentShader = "#ifdef GL_ES\n" +
                "precision mediump float;\n" +
                "#endif\n" +
                "varying vec4 v_color;\n" +
                "varying vec2 v_texCoords;\n" +
                "uniform sampler2D u_texture;\n" +
                "void main()                                  \n" +
                "{                                            \n" +
                "  gl_FragColor = v_color;\n" +
                "}";*/
        fragmentShader = "#ifdef GL_ES\n" +
                "#define LOWP lowp\n" +
                "    precision mediump float;\n" +
                "#else\n" +
                "    #define LOWP\n" +
                "#endif\n" +
                "\n" +
                "varying LOWP vec4 v_color;\n" +
                "varying vec2 v_texCoords;\n" +
                "\n" +
                "uniform sampler2D u_texture;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" +
                "}";

        shader = new ShaderProgram(vertexShader, fragmentShader);

        for(int i = 1; i < landscape.getDepth(); i++){
            for(int j = 1; j < landscape.getWidth(); j++){

                System.out.println( landscape.getCellHeight(i-1, j-1) ) ;

                CellColor color ;

                color = landscape.getCellColor(i,j) ;
                vertices[0] = (float) i ;
                vertices[1] = (float) j ;
                vertices[2] = (float) landscape.getCellHeight(i, j) ;
                vertices[3] = new Color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, 0).toFloatBits() ;

                color = landscape.getCellColor(i-1,j) ;
                vertices[6] = (float) (i - 1) ;
                vertices[7] = (float) (j) ;
                vertices[8] = (float) landscape.getCellHeight(i - 1, j) ;
                vertices[9] = new Color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, 0).toFloatBits() ;

                color = landscape.getCellColor(i-1,j-1) ;
                vertices[12] = (float) (i - 1) ;
                vertices[13] = (float) (j - 1) ;
                vertices[14] = (float) landscape.getCellHeight(i - 1, j - 1) ;
                vertices[15] = (new Color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, 0)).toFloatBits() ;

                myMesh1[i - 1][j - 1] = new Mesh( true, 3, 3,
                        new VertexAttribute( VertexAttributes.Usage.ColorPacked, 4, "a_color" ),
                        new VertexAttribute( VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE ),
                        new VertexAttribute( VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE+"0" ) );

                myMesh1[i - 1][j - 1].setVertices(vertices);
                myMesh1[i - 1][j - 1].setIndices(indices);

                color = landscape.getCellColor(i,j) ;
                vertices[0] = (float) i ;
                vertices[1] = (float) j ;
                vertices[2] = (float)landscape.getCellHeight(i, j) ;
                vertices[3] = (new Color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, 0)).toFloatBits() ;

                color = landscape.getCellColor(i-1,j-1) ;
                vertices[6] = (float) (i - 1) ;
                vertices[7] = (float) (j - 1) ;
                vertices[8] = (float)landscape.getCellHeight(i - 1, j - 1) ;
                vertices[3] = (new Color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, 0)).toFloatBits() ;

                color = landscape.getCellColor(i,j-1) ;
                vertices[12] = (float) i ;
                vertices[13] = (float) (j - 1) ;
                vertices[14] = (float)landscape.getCellHeight(i, j - 1) ;
                vertices[3] = (new Color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, 0)).toFloatBits() ;

                myMesh2[i - 1][j - 1] = new Mesh( true, 3, 3,
                        new VertexAttribute( VertexAttributes.Usage.ColorPacked, 4, "a_color" ),
                        new VertexAttribute( VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE ),
                        new VertexAttribute( VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE+"0" ) );

                myMesh2[i - 1][j - 1].setVertices(vertices);
                myMesh2[i - 1][j - 1].setIndices(indices);
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        //camera.rotate(3, 0, 0, 1);
        //camera.rotate(new Vector3(0, 0, 10f), 1);
        camera.position.set(camX, camY, camZ);
        camera.lookAt(camLookX, camLookY, camLookZ);
        camera.update();
        //System.out.println(camera.direction.x + " " + camera.direction.y + " " + camera.direction.z);
    }

    @Override
    public void draw() {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        for(int i = 1; i < landscape.getDepth(); i++){
            for(int j = 1; j < landscape.getWidth(); j++){

                /*ModelBuilder modelBuilder = new ModelBuilder();
                modelBuilder.begin();
                modelBuilder.part("triangle", myMesh, GL20.GL_TRIANGLES, new Material(ColorAttribute.createDiffuse(Color.GREEN)));
                model = modelBuilder.end();

                instance = new ModelInstance(model);

                modelBatch.begin(camera);
                modelBatch.render(instance);
                modelBatch.end();*/

                shader.begin();
                shader.setUniformMatrix("u_projTrans", camera.combined);
                myMesh1[i - 1][j - 1].render(shader, GL20.GL_TRIANGLES);
                myMesh2[i - 1][j - 1].render(shader, GL20.GL_TRIANGLES);
                shader.end();
            }
        }
    }

    @Override
    public void handleInput() {
        System.out.println(String.format("Cam direction: %.2f %.2f %.2f", camera.direction.x, camera. direction.y, camera.direction.z));
        System.out.println(String.format("Cam position: %.2f %.2f %.2f", camera.position.x, camera. position.y, camera.position.z));
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
            /*camera.rotate(new Vector3(camera.direction.y * camera.position.z - camera.direction.z * camera.position.y,
                                    camera.direction.x * camera.position.z - camera.direction.z * camera.position.x,
                                    camera.direction.x * camera.position.y - camera.direction.y * camera.position.x),
                                    -0.1f);*/
            System.out.println(camLookX + " " + camLookY + " " + camLookZ);
            camLookX += (camera.position.x - camera.direction.x) / 100f;
            camLookY += (camera.position.y - camera.direction.y) / 100f;
            camLookZ += (camera.position.z - camera.direction.z) / 100f;
        }
        else if(GameKeys.mouseY < Landscape.HEIGHT / 10){
            System.out.println(camLookX + " " + camLookY + " " + camLookZ);
            camLookX -= (camera.position.x - camera.direction.x) / 100f;
            camLookY -= (camera.position.y - camera.direction.y) / 100f;
            camLookZ -= (camera.position.z - camera.direction.z) / 100f;
        }
    }

    @Override
    public void dispose() {

    }
}
