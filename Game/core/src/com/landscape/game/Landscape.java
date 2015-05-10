package com.landscape.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.landscape.game.keys.GameKeys;
import com.landscape.game.keys.MyGameProcessor;

public class Landscape extends ApplicationAdapter {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 480;

    private GameStateManager gsm;

	@Override
	public void create () {
        gsm = new GameStateManager();
        gsm.init();

        Gdx.input.setInputProcessor(new MyGameProcessor(gsm));

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.6f, 0.6f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.draw();
        GameKeys.update();
	}
}
