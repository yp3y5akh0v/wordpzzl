package com.yp3y5akh0v.games.wordpzzl.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.yp3y5akh0v.games.wordpzzl.WordPzzl;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 640;
        config.height = 640;
        config.resizable = false;
        new LwjglApplication(new WordPzzl(), config);
    }
}
