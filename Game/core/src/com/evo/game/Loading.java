package com.evo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evo.game.gameStates.PlayState;
import gdk.World;

/**
 * Created by Рома on 14.05.2015.
 */
public class Loading implements Runnable {

    public Thread t;
    PlayState playState;

    public Loading(PlayState playState){
        t = new Thread(this);
        this.playState = playState;
        t.start();
    }

    @Override
    public void run() {
        playState.world = new World();
        playState.world.generateNew(8, 32, 1000);
        playState.world.landscape.setScale(playState.SCALE);

        playState.cellsColor = new Color[playState.world.landscape.getDepth()][playState.world.landscape.getWidth()] ;
        for( int i = 0 ; i < playState.world.landscape.getDepth() ; ++i )
            for( int j = 0 ; j < playState.world.landscape.getWidth() ; ++j )
                playState.cellsColor[i][j] = new Color(
                        playState.world.landscape.getCellColor(i,j).getRed()/255.0f,
                        playState.world.landscape.getCellColor(i,j).getGreen()/255.0f,
                        playState.world.landscape.getCellColor(i,j).getBlue()/255.0f, 0
                ) ;

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {

            }
        });

        playState.ready = true;

    }
}
