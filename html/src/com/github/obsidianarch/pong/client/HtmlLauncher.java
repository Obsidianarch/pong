package com.github.obsidianarch.pong.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.github.obsidianarch.pong.PongGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(750, 500);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new PongGame();
        }
}