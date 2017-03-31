package es.adventure.beach.tiledobjects;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;


public abstract class InteractiveTileObject {
	protected TiledMapTile tile;
	protected GameScreen screen;

	protected Rectangle bounds;
	protected Body body;

	protected Fixture fixture;

	public InteractiveTileObject(Rectangle bounds, GameScreen gameScreen,boolean boolSensor) {

		this.bounds = bounds;
		this.screen = gameScreen;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		bdef.type = BodyDef.BodyType.DynamicBody;// mirar kinematic,
		// dynamicbody etc
		bdef.position.set((bounds.getX() + bounds.getWidth() / 2)
				/ BeachAdventure.PPM, (bounds.getY() + bounds.getHeight() / 2)
				/ BeachAdventure.PPM);
		body = gameScreen.getWorld().createBody(bdef);

		shape.setAsBox((bounds.getWidth() / 2) / BeachAdventure.PPM,
				(bounds.getHeight() / 2) / BeachAdventure.PPM);
		fdef.shape = shape;
		fdef.isSensor=false;
		fixture = body.createFixture(fdef);

	}

	
	public abstract void tileHit();// para mario

	public void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
	}

	// ESTE METODO SE USA PARA REEMPLAZAR EL LAYER DE GRAFICOS
//	public TiledMapTileLayer.Cell getCell() {
////		// box2d body
////		TiledMapTileLayer layer = (TiledMapTileLayer) screen.getMap()
////				.getLayers().get(1);
////		return layer.getCell((int) (body.getPosition().x * BeachAdventure.PPM / 16),
////				(int) (body.getPosition().y * BeachAdventure.PPM / 16));// 16
//																	// tilesize
//
//	}
}

