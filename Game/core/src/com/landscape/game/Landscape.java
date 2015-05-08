package com.landscape.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.landscape.game.keys.GameKeys;
import com.landscape.game.keys.MyGameProcessor;

public class Landscape extends ApplicationAdapter {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 600;

    private GameStateManager gsm;

	@Override
	public void create () {
        gsm = new GameStateManager();
        gsm.init();

        Gdx.input.setInputProcessor(new MyGameProcessor());

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.draw();
        GameKeys.update();
	}
}
