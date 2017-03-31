package es.adventure.beach.tiledobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;

import es.adventure.beach.main.Assets;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class Computer extends InteractiveTileObject {
	private static TiledMapTileSet tileSet;
	private final int BLANK_COIN = 10;// ID + 1. Ya que su ID es 27 segun TILED
	private Sound dessert = Gdx.audio.newSound(Gdx.files
			.internal("audio/sounds/DesertEagle.wav"));
	public Computer(Rectangle bounds, GameScreen gameScreen) {
		super(bounds, gameScreen, true);
		tileSet = super.screen.getMap().getTileSets().getTileSet("santileset");// mirar
		// nombre
		// en
		// TILED
		fixture.setUserData(this);
		setCategoryFilter(BeachAdventure.COMPUTER_BIT);
	}

	@Override
	public void tileHit() {
		dessert.play(
				screen.getCurrentMusicVolume());
		// super.screen.addScore(15);
		// getCell().setTile(tileSet.getTile(149));
		// setCategoryFilter(BeachAdventure.DESTROYED_BIT);
		// super.screen.setSomb(super.screen.getSomb() - 1);

		// Assets.manager.get(Assets.coin).play(0.9f*screen.getGame().getGameVolume());
	}

}
