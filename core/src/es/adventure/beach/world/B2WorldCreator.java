package es.adventure.beach.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;

import es.adventure.beach.entity.IntedPowUp;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.tiledobjects.Boya;
import es.adventure.beach.tiledobjects.Brick;
import es.adventure.beach.tiledobjects.Computer;

public class B2WorldCreator implements Disposable {
	private GameScreen screen;
	private Texture sizedT;
	private TextureRegion brick11;
	private TextureRegion brick21;
	private TextureRegion brick41;
	

	private TextureRegion brick12;
	private TextureRegion brick14;
	private Brick brick;
	public B2WorldCreator(GameScreen gameScreen) {
		sizedT = new Texture("BRICKS.png");
		 brick11 = new TextureRegion(sizedT, 0, 0, 64, 64);
		 brick21 = new TextureRegion(sizedT, 0, 0, 128, 64);
		 brick41 = new TextureRegion(sizedT, 0, 0, 256, 64);
		 brick12= new TextureRegion(sizedT, 0, 0, 64, 128);
		 brick14= new TextureRegion(sizedT, 0, 0, 64, 256);
		
		// create body and fixture variables
		this.screen = gameScreen;

		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;

		// GROUNDS
		for (MapObject object : gameScreen.getMap().getLayers().get(2).getObjects()
				.getByType(RectangleMapObject.class)) {
			// este loop empieza en 2 correspondiente a la capa de objetos
			// ground del TILED
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;// mirar kinematic,
													// dynamicbody etc
			bdef.position.set((rect.getX() + rect.getWidth() / 2)
					/ BeachAdventure.PPM, (rect.getY() + rect.getHeight() / 2)
					/ BeachAdventure.PPM);
			body = gameScreen.getWorld().createBody(bdef);

			shape.setAsBox((rect.getWidth() / 2) / BeachAdventure.PPM,
					(rect.getHeight() / 2) / BeachAdventure.PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = BeachAdventure.GROUND_BIT;
			body.createFixture(fdef);

		}

		// COMPUTERS
		for (MapObject object : gameScreen.getMap().getLayers().get(3).getObjects()
				.getByType(RectangleMapObject.class)) {

			// Rectangle rect = ((RectangleMapObject) object).getRectangle();

			// new Computer(rect, gameScreen);
		}
		// NUBES
		// for (MapObject object : gameScreen.map.getLayers().get(4)
		// .getObjects().getByType(RectangleMapObject.class)) {
		//
		// Rectangle rect = ((RectangleMapObject) object).getRectangle();
		//
		// ///new Nube(rect, gameScreen);
		// }
		// DYNAMIC BRICKS
		for (MapObject object : gameScreen.getMap().getLayers().get(4).getObjects()
				.getByType(RectangleMapObject.class)) {

			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			// Brick brick = new Brick(rect, gameScreen);
			// new Boya(rect, gameScreen);
		if(rect.height<65){
			if(rect.width<65){
				 brick = new Brick(gameScreen, rect, brick11);
			}else if(rect.width<129){
				 brick = new Brick(gameScreen, rect, brick21);
			}else if(rect.width<257){
				 brick = new Brick(gameScreen, rect, brick41);
			}
		}else if(rect.height<129){
			 brick = new Brick(gameScreen, rect, brick12);
			
		}else if(rect.height<257){
			 brick = new Brick(gameScreen, rect, brick14);
				
		}
			
			screen.actives().entities0().add(brick);

		}

	}

	@Override
	public void dispose() {
		sizedT.dispose();

	}

}
