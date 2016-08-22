package com.yp3y5akh0v.games.wordpzzl.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.user.client.Window;
import com.yp3y5akh0v.games.wordpzzl.WordPzzl;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(640, 640);
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return new WordPzzl();
    }

    @Override
    public void exit() {
        Window.alert("You can only exit the application, just hit close tab...");
    }
}