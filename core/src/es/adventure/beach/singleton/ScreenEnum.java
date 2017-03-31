package es.adventure.beach.singleton;

import es.adventure.beach.scenes.AbstractScreen;
import es.adventure.beach.scenes.GameOverScreen;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.scenes.HighScoresScreen;
import es.adventure.beach.scenes.LevelSelectScreen;
import es.adventure.beach.scenes.MainMenuScreen;
import es.adventure.beach.scenes.SplashScreen;

public enum ScreenEnum {
	SPLASH_SCREEN {
		public AbstractScreen getScreen(Object... params) {
			return new SplashScreen();
		}
	},
	MAIN_MENU {
		public AbstractScreen getScreen(Object... params) {
			return new MainMenuScreen();
		}
	},
	
	LEVEL_SELECT {
		public AbstractScreen getScreen(Object... params) {
			return new LevelSelectScreen();
		}
	},
	
	HIGH_SCORES {
		public AbstractScreen getScreen(Object... params) {
			return new HighScoresScreen();
		}
	},
	OVER {
		public AbstractScreen getScreen(Object... params) {
			return new GameOverScreen((Integer) params[0],(Integer) params[1]);
		}
	},
	GAME {
		public AbstractScreen getScreen(Object... params) {
			return new GameScreen((Integer) params[0]);
		}
	};
	
	public abstract AbstractScreen getScreen(Object... params);
}
