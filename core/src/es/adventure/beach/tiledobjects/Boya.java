package es.adventure.beach.tiledobjects;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class Boya {
	protected TiledMapTile tile;
	protected GameScreen screen;
	protected Rectangle pol;
	protected Body body;

	protected Fixture fixture;

	public Boya(Rectangle pol, GameScreen gameScreen) {
		this.pol = pol;
		this.screen = gameScreen;
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		

		bdef.type = BodyDef.BodyType.DynamicBody;
	//	fdef.filter.categoryBits = BeachAdventure.BOYA_BIT;
		//fdef.filter.maskBits = BeachEscape.GROUND_BIT | BeachEscape.BULLET_BIT;
		PolygonShape ps=new PolygonShape();
		ps.setAsBox((pol.width/2)/BeachAdventure.PPM, (pol.height/2)/BeachAdventure.PPM);
		//this.pol= new Polygon(pol.getVertices());
		
		bdef.position.set((pol.x ) / BeachAdventure.PPM,
				(pol.y ) / BeachAdventure.PPM);

		body = gameScreen.getWorld().createBody(bdef);

		fdef.shape = ps;
		fdef.restitution=0.9f;
		fixture = body.createFixture(fdef);
	}
}

