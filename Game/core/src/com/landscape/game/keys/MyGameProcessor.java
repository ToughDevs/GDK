package com.landscape.game.keys;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.landscape.game.PlayState;
import com.landscape.game.Transformafor;

public class MyGameProcessor extends InputAdapter{
    @Override
    public boolean keyDown(int k){
        if(k == Keys.UP){
            GameKeys.setKey(GameKeys.UP, true);
        }
        if(k == Keys.RIGHT){
            GameKeys.setKey(GameKeys.RIGHT, true);
        }
        if(k == Keys.DOWN){
            GameKeys.setKey(GameKeys.DOWN, true);
        }
        if(k == Keys.LEFT){
            GameKeys.setKey(GameKeys.LEFT, true);
        }
        if(k == Keys.SPACE){
            GameKeys.setKey(GameKeys.SPACE, true);
        }
        if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT){
            GameKeys.setKey(GameKeys.SHIFT, true);
        }
        if(k == Keys.ENTER){
            GameKeys.setKey(GameKeys.ENTER, true);
        }
        if(k == Keys.ESCAPE){
            GameKeys.setKey(GameKeys.ESCAPE, true);
        }
        return true;
    }
    @Override
    public boolean keyUp(int k){
        if(k == Keys.UP){
            GameKeys.setKey(GameKeys.UP, false);
        }
        if(k == Keys.RIGHT){
            GameKeys.setKey(GameKeys.RIGHT, false);
        }
        if(k == Keys.DOWN){
            GameKeys.setKey(GameKeys.DOWN, false);
        }
        if(k == Keys.LEFT){
            GameKeys.setKey(GameKeys.LEFT, false);
        }
        if(k == Keys.SPACE){
            GameKeys.setKey(GameKeys.SPACE, false);
        }
        if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT){
            GameKeys.setKey(GameKeys.SHIFT, false);
        }
        if(k == Keys.ENTER){
            GameKeys.setKey(GameKeys.ENTER, false);
        }
        if(k == Keys.ESCAPE){
            GameKeys.setKey(GameKeys.ESCAPE, false);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY){
        GameKeys.mouseX = screenX;
        GameKeys.mouseY = screenY;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer){
        GameKeys.mouseX = screenX;
        GameKeys.mouseY = screenY;
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        GameKeys.pressMouse(true);
        GameKeys.mousePressedX = screenX;
        GameKeys.mousePressedY = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        GameKeys.pressMouse(false);
        return true;
    }

    @Override
    public boolean scrolled(int amount){
        if(amount == -1 && PlayState.camZ > 5f || amount == 1 && PlayState.camZ < 15f)
            PlayState.camZ += amount;
        return true;
    }

}
