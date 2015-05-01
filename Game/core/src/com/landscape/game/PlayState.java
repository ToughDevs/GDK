package com.landscape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.landscape.game.keys.GameKeys;
import com.vova.land.Land;

public class PlayState extends GameState{

    ShapeRenderer sr;
    Land landscape;
    Transformafor tr[][];

    private final int SCALE = 100;
    private final int MAP_SIZE = 64;

    public PlayState(GameStateManager gsm){super(gsm);}

    public void draw3DPoint(ShapeRenderer sr, Transformafor tr){
        sr.setColor((tr.getInZ() - 20)/ 150, 0, 0, 1);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(tr.getX(), tr.getY(), 3);
        sr.end();
    }

    public void draw3DLine(ShapeRenderer sr, Transformafor tr1, Transformafor tr2){
        sr.setColor(0, ((tr1.getInZ() + tr2.getInZ()) / 2 - 100 )/ 10, 0, 1);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.line(tr1.getX(), tr1.getY(), tr2.getX(), tr2.getY());
        sr.end();
    }

    public void draw3DTriangle(ShapeRenderer sr, Transformafor tr1, Transformafor tr2, Transformafor tr3){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.triangle(tr1.getX(), tr1.getY(), tr2.getX(), tr2.getY(), tr3.getX(), tr3.getY(),
                new Color(0, tr1.getInZ() / SCALE * 10 / MAP_SIZE + (float)(0.02 * Math.random()), 0, 1),
                new Color(0, tr2.getInZ() / SCALE * 10 / MAP_SIZE + (float)(0.02 * Math.random()), 0, 1),
                new Color(0, tr3.getInZ() / SCALE * 10 / MAP_SIZE + (float)(0.02 * Math.random()), 0, 1));
        sr.end();
    }

    @Override
    public void init() {
        landscape = new Land();
        landscape.generateNew();
        landscape.normalizeHeight();
        for( int i = 0 ; i < 5 ; ++i )
            landscape.averageHeight();
        landscape.normalizeHeight();
        landscape.setScale(SCALE) ;
        sr = new ShapeRenderer();
        tr = new Transformafor[landscape.getDepth()][landscape.getWidth()];
        for (int i = 1; i <= landscape.getWidth(); i++) {
            for (int j = 1; j <= landscape.getDepth(); j++) {
                tr[i-1][j-1] = new Transformafor(i * MAP_SIZE, j * MAP_SIZE, (float) landscape.getPointHeight(i, j) * MAP_SIZE / 10);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        for(int i = 0; i < tr.length; i++){
            for(int j = 0; j < tr[0].length; j++)
                tr[i][j].update();
        }
    }

    @Override
    public void draw() {
        for (int i = 1; i < tr.length; i++) {
            for (int j = 1; j < tr[0].length; j++) {
                draw3DTriangle(sr, tr[i][j], tr[i - 1][j - 1], tr[i][j - 1]);
                draw3DTriangle(sr, tr[i][j], tr[i - 1][j - 1], tr[i - 1][j]);
            }
        }
        //sr.end();
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
        System.out.println(Transformafor.getAngle());
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
