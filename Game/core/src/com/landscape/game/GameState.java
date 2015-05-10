package com.landscape.game;

public abstract class GameState {

    protected GameStateManager gsm;

    protected GameState(GameStateManager gsm){
        this.gsm = gsm;
    }

    public abstract void init();
    public abstract void update(float dt);
    public abstract void scroll(int amount);
    public abstract void draw();
    public abstract void handleInput();
    public abstract void dispose();
}
