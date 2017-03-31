package es.adventure.beach.singleton;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.AbstractScreen;

public class ScreenManager {
	// Singleton: unique instance
		private static ScreenManager instance;
		
		// Reference to game
		private BeachAdventure game;
		protected SpriteBatch batch;
		
		
		// Singleton: private constructor
		private ScreenManager() {
			super();
		}
		
		// Singleton: retrieve instance
		public static ScreenManager getInstance() {
			if (instance == null) {
				instance = new ScreenManager();
			}
			return instance;
		}
		
		// Initialization with the game class
		public void initialize(BeachAdventure game) {
			this.game = game;
			this.batch = new SpriteBatch();
		
		}
		
		// Show in the game the screen which enum type is received
		public void showScreen(ScreenEnum screenEnum, Object... params) {
			
			// Get current screen to dispose it
			com.badlogic.gdx.Screen currentScreen = game.getScreen();
			
			// Show new screen
			AbstractScreen newScreen = screenEnum.getScreen(params);
			newScreen.buildStage();
			game.setScreen(newScreen);
			
			// Dispose previous screen
			if (currentScreen != null) {
				currentScreen.dispose();
			}
		}

		public BeachAdventure getGame() {
			return game;
		}
		public SpriteBatch getBatch() {
			return batch;
		}

	
}
