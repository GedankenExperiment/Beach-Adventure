package es.adventure.beach.main;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.FPSLogger;

import es.adventure.beach.singleton.ScreenEnum;
import es.adventure.beach.singleton.ScreenManager;

//www.CheapProFonts.com
public class BeachAdventure extends Game {
	/*
	 * Voy a desenchufar el AssetManager y a usar un atlas para cada Screen
	 */
	private  Leaderboard leaderboard;//informacion propia de plataforma
	// Collixion detection
	public static float PPM = 100f;// pixel per meter
	public static float LABELSCALE = 1.9f;
	public static int TILE_SIZE = 64;

	// max resolution
	public static float V_WIDTH = 21f * TILE_SIZE;// 1344
	public static float V_HEIGHT = 13f * TILE_SIZE;// 832

	public static  float camX = 27 * 64 * .5f / PPM;
	public static final float camY = 13 * 64 * .5f / PPM;
	
	public static float VW_WIDTH = V_WIDTH / PPM;

	// x must be between 0 and 192/100
	public static float VW_HEIGHT = V_HEIGHT / PPM;// 13*64/100
	// public static int V_WIDTH = 24*16;// 40 virtual width
	// public static int V_HEIGHT = 12*16;//208
	public static final int NO_LEVELS_TABLE = 12;

	public static final short GROUND_BIT = 1 << 0;
	public static final short WEAPONS_BIT = 1 << 1;
	public static final short ROUNDBULLET_BIT = 1 << 2;
	public static final short BRICK_BIT = 1 << 3;
	public static final short PLAYER_BIT = 1 << 4;
	public static final short ENEMY_BIT =  1 << 5;

	public static final short DRONE1_BIT = 1 << 9;
	public static final short NUBE_BIT = 1 << 10;
	public static final short COMPUTER_BIT = 1 << 11;
	public static final short BOMB_BIT = 1 << 12;
	public static final short INTED_ARTIFACT_BIT = 1 << 13;
	public static final short DRONE_BIT = 1 << 14;

	
	
	
	
	

	public FPSLogger myFPS;

	// private MenuScreen menuScreen;

	// private HighScoresScreen highScoresScreen;
	// private LevelScreen levelScreen;



	
	public BeachAdventure(Leaderboard leaderboard) {
	      this.leaderboard = leaderboard;
	   
	   }
	// public static SpriteBatch batch;
	// STORAGE FLODER
	// WINDOWS %UserProfile%/.prefs/My Preferences
	// LINUX AND IOS ~/.prefs/My Preferences
	@Override
	public void create() {
		

		leaderboard.initPrefs("init", 101);
//		leaderboard.borrar();




		Assets.manager.finishLoading();

		myFPS = new FPSLogger();

		ScreenManager.getInstance().initialize(this);
		
		ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);

	}

	protected void getPlayerPrefs() {


	}
	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	
	}
	public Leaderboard getLeaderboard() {
		return leaderboard;
	}

}
