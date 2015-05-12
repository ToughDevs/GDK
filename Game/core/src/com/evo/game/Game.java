package com.evo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.evo.game.gameStates.GameStateManager;
import com.evo.game.keys.GameKeys;
import com.evo.game.keys.MyGameProcessor;

public class Game extends ApplicationAdapter {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 960;

    private GameStateManager gsm;

	@Override
	public void create () {
        gsm = new GameStateManager();

        Gdx.input.setInputProcessor(new MyGameProcessor(gsm));

        Jukebox.load("sounds/pulsehigh.ogg", "pulsehigh");
        Jukebox.load("sounds/pulselow.ogg", "pulselow");
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
