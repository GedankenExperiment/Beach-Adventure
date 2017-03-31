package es.adventure.beach.tiledobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;

import es.adventure.beach.entity.Airplane;
import es.adventure.beach.entity.Entity;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.serialization.AirplaneData;


public class Brick extends Entity implements Disposable{

private static final float dpr=57.2957795f;
	private boolean setToDestroy;
	private boolean destroyed;
	private int vida;

	private Rectangle data;
	
	
	private TextureRegion region;// no flip

	// private ParticleEffect pe4;
	// private ParticleEffect pe3;
	// private ParticleEffect pe2;
	// private ParticleEffect pe1;
	//
	// private float x4shift, y4shift;
	// private float x3shift, y3shift;
	// private float x2shift, y2shift;
	// private float x1shift, y1shift;
	private BodyDef bdef;

	private FixtureDef fdef;

	public Brick(GameScreen screen, Rectangle data,TextureRegion region) {
		super(screen);
		this.region=region;
		this.data=data;
		this.vida = 5;

		this.setToDestroy = false;
		this.destroyed = false;
		
		init(data);
	}

	@Override
	public void reset() {
		// setPosition(0, 0);
		this.destroyed = false;
		this.vida = 5;

		this.setToDestroy = false;

	}

	public void init(Rectangle data) {
		this.data = data;
		

			
		
	
			super.setOrigin(region.getRegionWidth()/2 / BeachAdventure.PPM, region.getRegionHeight()/2 / BeachAdventure.PPM);

			super.setBounds(getX(), getY(), region.getRegionWidth()/ BeachAdventure.PPM,
					region.getRegionHeight() / BeachAdventure.PPM);
			super.halfW = region.getRegionWidth() / 2/BeachAdventure.PPM;
			super.halfH = region.getRegionHeight() / 2/BeachAdventure.PPM;
		super.setX(data.getX()+super.halfW/BeachAdventure.PPM);
		super.setY(data.getY()+super.halfH/BeachAdventure.PPM);

		

		defineBody();
		vida = 5;
		super.setRegion(region);
		

		this.setToDestroy = false;
		this.destroyed = false;
	}

	public void update(float dt) {

		if (destroyed) {
			// setRegion(bull);
		} else if (setToDestroy && !destroyed) {

			screen.getWorld().destroyBody(super.getB2body());
			destroyed = true;

		} else if (!destroyed) {

			super.setPosition(super.getB2body().getPosition().x - halfW,
					super.getB2body().getPosition().y - halfH);
			super.setRotation(super.getB2body().getAngle()*dpr);
			// b2body.setLinearVelocity(velocity);

		}

	}

	public void draw(Batch batch) {

		if (!destroyed) {
			super.draw(batch);

		} else if (destroyed) {

			screen.actives().entities1().removeValue(this, true);

			//Pools.get(Bric.class).free(this);
			Gdx.app.log("Brick Removed",
					"Brick Removed");

		}
	}

	protected void defineBody() {
		bdef = new BodyDef();
		bdef.position.set((data.getX() + data.getWidth() / 2)
				/ BeachAdventure.PPM, (data.getY() + data.getHeight() / 2)
				/ BeachAdventure.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		super.setB2body(screen.getWorld().createBody(bdef));
		
		// this.velocity.x = ;
		// this.velocity.y = ;
		
	
		

		

		fdef = new FixtureDef();

		// ///////////////////////////////
		PolygonShape pshape = new PolygonShape();
		
		pshape.setAsBox((data.getWidth() / 2) / BeachAdventure.PPM,
				(data.getHeight() / 2) / BeachAdventure.PPM);
		// /////////////////////////////

		fdef.filter.categoryBits = BeachAdventure.BRICK_BIT;
		fdef.filter.maskBits = BeachAdventure.GROUND_BIT
				|  BeachAdventure.DRONE_BIT|BeachAdventure.ENEMY_BIT|BeachAdventure.DRONE1_BIT|  BeachAdventure.BRICK_BIT;

		fdef.shape = pshape;
		fdef.restitution = 0f;// bouncines!! 0-1
	
		fdef.density = 10f;// 10 es la densidad kg/m2
		// fdef.friction = 0.99f;// 0-

		super.getB2body().createFixture(fdef).setUserData(this);
		pshape.dispose();

	}


	public void hitByGround() {
		setToDestroy = true;

	}

	public void hitOnBullet(float bx, float by) {
		
		
//		if (this.company == "BIASED") {
//
//			if (this.vida > 0)
//				this.vida--;
//
//			if (this.vida <= 0) {
//				screen.getCounter().k_airplane();
//				setToDestroy = true;
//			}
//		} else if (this.company == "PAHAMA") {
//
//			if (this.vida > 0)
//				this.vida--;
//
//			if (this.vida <= 0) {
//				screen.getCounter().k_airplane();
//				setToDestroy = true;
//			}
//		}

		// Create effect:

	}

	public void hitOnEscopeta(float bx, float by) {

//		if (this.vida > 0)
//			this.vida--;
//		if (this.vida <= 0) {
//			screen.getCounter().k_airplane();
//			setToDestroy = true;
//		}

		// particleTriger(bx);

	}

	public void hitOnMp5(float bx, float by) {

//		if (this.vida > 0)
//			this.vida--;
//		if (this.vida <= 0) {
//			setToDestroy = true;
//			screen.getCounter().k_airplane();
//		}

		// particleTriger(bx);

	}

	@Override
	public void dispose() {
	


	}

}
