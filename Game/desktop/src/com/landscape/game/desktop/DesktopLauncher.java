package com.landscape.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.landscape.game.Landscape;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Landscape.WIDTH;
        config.height = Landscape.HEIGHT;
		new LwjglApplication(new Landscape(), config);
	}
}
