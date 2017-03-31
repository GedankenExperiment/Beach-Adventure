package es.adventure.beach.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;

import es.adventure.beach.main.Assets;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.serialization.AirplaneData;

public class Airplane extends Enemy implements Disposable {
	private static final String name = "Airplane";
	private  static final int[] damageList={1,1,1,5,5};
	private AirplaneData data;
	private static final int initHitPoints = 0;
	
	
	private Texture primary_LT;
	private Texture primary_RT;
	private TextureRegion primary_L;// no flip
	private TextureRegion primary_R;// no flip

	private BodyDef bdef;

	private FixtureDef fdef;


	public Airplane(GameScreen screen, String COMPANY) {
		super(screen, name, initHitPoints, damageList);
		

		
		primary_LT = new Texture("A" + COMPANY + "L.png");
		primary_RT = new Texture("A" + COMPANY + "R.png");

		primary_L = new TextureRegion(primary_LT);
		primary_R = new TextureRegion(primary_RT);

		super.setOrigin(128f / BeachAdventure.PPM, 64 / BeachAdventure.PPM);

		super.setBounds(getX(), getY(), 256f / BeachAdventure.PPM,
				128 / BeachAdventure.PPM);
		super.halfW = super.getWidth() / 2;
		super.halfH = super.getHeight() / 2;
	}

	@Override
	public void reset() {
		// setPosition(0, 0);
		super.destroyed = false;

		super.setToDestroy = false;

	}

	public void init(AirplaneData airplaneData) {
		this.data = airplaneData;

		super.setX(airplaneData.xi);
		super.setY(airplaneData.yi);

		this.fase = Math.atan2((double) (airplaneData.yf - super.getY()),
				(double) (airplaneData.xf - super.getX()));

		defineBody();

		// SENTIDO DE LA MARCHA
		if (this.velocity.x > 0) {
			super.setRegion(primary_R);
		} else if (this.velocity.x < 0) {
			super.setRegion(primary_L);
		}

	}

	public void update(float dt) {

		if (destroyed) {
			// setRegion(bull);
		} else if (setToDestroy && !destroyed) {

			screen.getWorld().destroyBody(super.getB2body());
			destroyed = true;

		} else if (!destroyed) {

			super.setPosition(super.getB2body().getPosition().x - halfW, super
					.getB2body().getPosition().y - halfH);
			// b2body.setLinearVelocity(velocity);

		}
	}

	public void draw(Batch batch) {

		if (!destroyed) {
			super.draw(batch);

		} else if (destroyed) {

			screen.actives().entities1().removeValue(this, true);

			Pools.get(Airplane.class).free(this);
	

		}
	}

	protected void defineBody() {
		bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;

		super.setB2body(screen.getWorld().createBody(bdef));
		this.velocity = new Vector2(
				(float) (this.data.modulo * Math.cos(fase)),
				(float) (this.data.modulo * Math.sin(fase)));
		// this.velocity.x = ;
		// this.velocity.y = ;
		super.getB2body().setGravityScale(0f);
		super.getB2body().setLinearVelocity(this.velocity.x, this.velocity.y);
		if (super.getX() < BeachAdventure.VW_WIDTH / 2) {
			this.setRegion(primary_R);
		} else {
			this.setRegion(primary_L);
		}
		super.getB2body().setLinearVelocity(velocity);
		super.getB2body().setFixedRotation(true);// no rota

		fdef = new FixtureDef();

		// ///////////////////////////////
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(60.0f / BeachAdventure.PPM, 25.0f / BeachAdventure.PPM);

		// /////////////////////////////

		fdef.filter.categoryBits = BeachAdventure.ENEMY_BIT;
		fdef.filter.maskBits = BeachAdventure.GROUND_BIT
				| BeachAdventure.PLAYER_BIT | BeachAdventure.WEAPONS_BIT;

		fdef.shape = shape;
		fdef.restitution = 0f;// bouncines!! 0-1
		fdef.isSensor = false;
		fdef.density = 2f;// 10 es la densidad kg/m2
		// fdef.friction = 0.99f;// 0-

		super.getB2body().createFixture(fdef).setUserData(this);
		shape.dispose();

	}

	@Override
	public void hitByGround() {
		setToDestroy = true;

	}
	@Override
	public void dispose() {
		primary_LT.dispose();
		primary_RT.dispose();
Gdx.app.log("texture AIR DISPOSED", "");
	}

	@Override
	public void notifySpawner() {
		screen.getActiveManager().getAirplaneSource().kill();
		
	}

	
}
