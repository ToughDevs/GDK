package com.landscape.game;

public class GameStateManager{

    private GameState gameState;

    public static final int PLAY = 1;

    public GameStateManager(){setState(PLAY);}

    public void setState(int state){
        if(gameState != null)
            gameState.dispose();
        switch (state){
            case PLAY:
                gameState = new PlayState(this);
                break;
            default:
                gameState = null;
        }
    }

    public void init(){
        gameState.init();
    }
    public void update(float dt){
        gameState.update(dt);
    }
    public void scroll(int amount){
        gameState.scroll(-amount);
    }
    public void draw(){
        gameState.draw();
    }
    public void handleInput(){
        gameState.handleInput();
    }
    public void dispose(){
        gameState.dispose();
    }

}
