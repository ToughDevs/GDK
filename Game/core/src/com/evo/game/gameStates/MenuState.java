package com.evo.game.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.evo.game.Jukebox;
import com.evo.game.keys.GameKeys;
import com.evo.game.Game;

public class MenuState extends GameState{

    private SpriteBatch batch;
    private BitmapFont titleFont;
    private BitmapFont font;

    private final String title = "EVO Game";

    private int currentItem = 0;
    private String[] menuItems;

    public MenuState(GameStateManager gsm){
        super(gsm);
    }

    public void init(){
        batch = new SpriteBatch();

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
                Gdx.files.internal("font/Hyperspace.ttf")
        );
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 56;
        titleFont = gen.generateFont(parameter);
        titleFont.setColor(Color.WHITE);
        parameter.size = 30;
        font = gen.generateFont(parameter);

        menuItems = new String[] {
            "Play",
            "Quit"
        };
    }

    public void update(float dt){

        handleInput();

    }
    public void draw(){

        batch.begin();

        titleFont.draw(batch, title, Game.WIDTH / 8, Game.HEIGHT - Game.HEIGHT / 6);

        for(int i = 0; i < menuItems.length; i++){
            if(i == currentItem)
                font.setColor(Color.RED);
            else
                font.setColor(Color.WHITE);
            font.draw(batch, menuItems[i], Game.WIDTH / 10, Game.HEIGHT  / 2 - i * 40 - 5);
        }

        batch.end();

    }
    public void handleInput(){
        if(GameKeys.isPressed(GameKeys.DOWN)){
            if(currentItem < menuItems.length - 1)
                currentItem++;
            else
                currentItem = 0;
            Jukebox.play("pulselow");
        }
        if(GameKeys.isPressed(GameKeys.UP)){
            if(currentItem > 0)
                currentItem--;
            else
                currentItem = menuItems.length - 1;
            Jukebox.play("pulsehigh");
        }
        if(GameKeys.isPressed(GameKeys.ENTER)){
            switch (currentItem){
                case 0:
                    gsm.setState(GameStateManager.PLAY);
                    break;
                case 1:
                    Gdx.app.exit();
            }
        }
    }

    public void scroll(int amount){

    }

    public void dispose(){

    }

}
