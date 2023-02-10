package com.emexgames.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.emexgames.game.LaunchGame;
import com.emexgames.game.util.Constants;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setWindowedMode(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

		config.setTitle(Constants.GAME_TITLE);

		new Lwjgl3Application(new LaunchGame(), config);
	}
}
