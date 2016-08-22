package com.yp3y5akh0v.games.wordpzzl;

import com.badlogic.gdx.Game;
import com.yp3y5akh0v.games.wordpzzl.screen.GameScreen;

/**
 * Game WordPzzl
 *
 * @author Yuriy Peysakhov
 * @version 1.0.0
 **/
public class WordPzzl extends Game {

    @Override
    public void create() {
        ResourceManager.getInstance().load();
        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        ResourceManager.getInstance().dispose();
    }
}
