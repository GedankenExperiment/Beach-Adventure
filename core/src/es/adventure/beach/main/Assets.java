package es.adventure.beach.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static AssetManager manager = new AssetManager();

	public static final AssetDescriptor<BitmapFont> fontGold = new AssetDescriptor<BitmapFont>(
			Gdx.files.internal("skin/menu.fnt"), BitmapFont.class);
	public static final AssetDescriptor<Skin> mySkin = new AssetDescriptor<Skin>(
			Gdx.files.internal("skin/uiskin.json"), Skin.class);
	public static final AssetDescriptor<Skin> scoreSkin = new AssetDescriptor<Skin>(
			Gdx.files.internal("skin/score_uiskin.json"), Skin.class);

	public static final AssetDescriptor<TextureAtlas> bigAtlas = new AssetDescriptor<TextureAtlas>(
			"total.pack", TextureAtlas.class);
	
	public static final AssetDescriptor<Texture> mainMenuScreen = new AssetDescriptor<Texture>(
			"MAINMENU.png", Texture.class);
	public static final AssetDescriptor<Texture> highScoresScreen = new AssetDescriptor<Texture>(
			"HIGHSCORES.png", Texture.class);
	public static final AssetDescriptor<Texture> overScreen = new AssetDescriptor<Texture>(
			"OVER.png", Texture.class);
	public static final AssetDescriptor<Texture> levelScreen = new AssetDescriptor<Texture>(
			"LEVEL.png", Texture.class);

	public static final AssetDescriptor<TextureAtlas> bulletExplosion = new AssetDescriptor<TextureAtlas>(
			"Bullet.pack", TextureAtlas.class);

	public static final AssetDescriptor<Music> music1 = new AssetDescriptor<Music>(
			"audio/music/mario_music.ogg", Music.class);
	public static final AssetDescriptor<Sound> coin = new AssetDescriptor<Sound>(
			"audio/sounds/coin.wav", Sound.class);

	public static final AssetDescriptor<Sound> bump = new AssetDescriptor<Sound>(
			"audio/sounds/bump.wav", Sound.class);

	public static final AssetDescriptor<Sound> breakblock = new AssetDescriptor<Sound>(
			"audio/sounds/breakblock.wav", Sound.class);
	public static final AssetDescriptor<Sound> dessert = new AssetDescriptor<Sound>(
			"audio/sounds/DesertEagle.wav", Sound.class);
	public static final AssetDescriptor<Sound> empty = new AssetDescriptor<Sound>(
			"audio/sounds/empty.wav", Sound.class);
	public static final AssetDescriptor<Sound> reload = new AssetDescriptor<Sound>(
			"audio/sounds/reload.wav", Sound.class);
	public static final AssetDescriptor<Sound> escopeta = new AssetDescriptor<Sound>(
			"audio/sounds/escopeta.wav", Sound.class);
	public static final AssetDescriptor<Sound> mp5 = new AssetDescriptor<Sound>(
			"audio/sounds/mp5.wav", Sound.class);

	// public static final AssetDescriptor<Texture> biased_L = new
	// AssetDescriptor<Texture>(
	// "airplanes/biased/747_BIASED_L.png", Texture.class);
	// public static final AssetDescriptor<Texture> biased_R = new
	// AssetDescriptor<Texture>(
	// "airplanes/biased/747_BIASED_R.png", Texture.class);
	// public static final AssetDescriptor<Texture> colateral_L = new
	// AssetDescriptor<Texture>(
	// "airplanes/colateral/747_COLATERAL_L.png", Texture.class);
	// public static final AssetDescriptor<Texture> colateral_R = new
	// AssetDescriptor<Texture>(
	// "airplanes/colateral/747_COLATERAL_R.png", Texture.class);

	public static void load() {
		
		
		manager.load(fontGold);
		manager.load(mySkin);
		manager.load(scoreSkin);
		manager.load(bigAtlas);
		// public static final TextureAtlas bigAtlas= new
		// TextureAtlas(Gdx.files.internal("total.atlas"));;
		
		manager.load(mainMenuScreen);
		manager.load(highScoresScreen);
		manager.load(levelScreen);
		manager.load(overScreen);
		

		manager.load(music1);
		manager.load(coin);
		manager.load(bump);
		manager.load(breakblock);
		manager.load(dessert);
		manager.load(empty);
		manager.load(reload);
		manager.load(escopeta);
		manager.load(mp5);
		manager.load(bulletExplosion);

	}

	public static void dispose() {
		manager.dispose();

	}
}