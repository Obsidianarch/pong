package com.github.obsidianarch.pong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.obsidianarch.pong.PongGame;

public class DesktopLauncher
{

	public static void main( String[] arg )
    {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 500;
        config.width = 750;
        config.title = "Pong!";
		new LwjglApplication( new PongGame(), config );
	}
}
