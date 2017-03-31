package es.adventure.beach.tiledobjects;

import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;

import es.adventure.beach.main.Assets;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class Sombrilla extends InteractiveTileObject {
	private TiledMapTileSet tileSet;
	private final int BLANK_COIN = 149;// ID + 1. Ya que su ID es 27 segun TILED

	public Sombrilla(Rectangle bounds, GameScreen screen) {
		super(bounds, screen, true);
		// tileSet =
		// super.screen.getMap().getTileSets().getTileSet("tileset_gutter");//
		// mirar
		// nombre
		// en
		// TILED
		fixture.setUserData(this);
		// setCategoryFilter(BeachAdventure.SOLARPANEL_BIT);
	}

	@Override
	public void tileHit() {
		// super.screen.addScore(15);
		// super.getCell().setTile(tileSet.getTile(149));
		// setCategoryFilter(BeachAdventure.DESTROYED_BIT);
		// super.screen.setSomb(super.screen.getSomb() - 1);

		// Assets.manager.get(Assets.coin).play(0.9f*screen.getGame().getGameVolume());
	}

}
