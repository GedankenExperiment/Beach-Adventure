package es.adventure.beach.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.serialization.DroneData;

public class Drone extends Enemy {
	
private static final String name = "Drone";
private static final int initHitPoints = 1;
private  static final int[] damageList={1,1,1,5,5};

private Texture drone1T, drone2T, drone3T, drone4T;
private TextureRegion drone1R, drone2R, drone3R, drone4R;
	private float stateTime;
	private Array<TextureRegion> frames;
	private Animation droneAnim;
	private BodyDef bdef;
//	private Body b2body;
	private FixtureDef fdef;
	private boolean patrol;//un bool leido del JSON para switch de states

	public Drone(GameScreen screen) {
		super(screen,name,initHitPoints,damageList);
	



		stateTime = 0;
		drone1T = new Texture("DRONE1.png");
		drone2T = new Texture("DRONE2.png");
		drone3T = new Texture("DRONE3.png");
		drone4T = new Texture("DRONE4.png");

		drone1R = new TextureRegion(drone1T);
		drone2R = new TextureRegion(drone2T);
		drone3R = new TextureRegion(drone3T);
		drone4R = new TextureRegion(drone4T);

		frames = new Array<TextureRegion>();

		frames.add(drone1R);// i
		frames.add(drone2R);// i
		frames.add(drone3R);// i
		frames.add(drone4R);// i
		droneAnim = new Animation(.013f, frames);

		super.setBounds(getX(), getY(), 128 / BeachAdventure.PPM,
				64 / BeachAdventure.PPM);
		super.setOrigin(128 / BeachAdventure.PPM, 64 / BeachAdventure.PPM);
		super.halfW = super.getWidth() / 2;
		super.halfH = super.getHeight() / 2;
		super.setRegion(droneAnim.getKeyFrame(stateTime, true));
		// flip(true, false);
	}

	public void update(float dt) {
		stateTime += dt;

		if (destroyed) {
			// setRegion(bull);
		} else if (setToDestroy && !destroyed) {

			screen.getWorld().destroyBody(b2body);
			destroyed = true;
			stateTime = 0;

		} else if (!destroyed) {
			super.setRegion(droneAnim.getKeyFrame(stateTime, true));

			 //b2body.setLinearVelocity(velocity);
			super.setPosition(b2body.getPosition().x - halfW,
					b2body.getPosition().y - halfH);

		}

	}

	public void init(DroneData droneData) {
		this.modulo = droneData.modulo;
		this.patrol = droneData.patrol;
		super.setX(droneData.xi);
		super.setY(droneData.yi);

		this.fase = Math.atan2((double) (droneData.yf - droneData.yi),
				(double) (droneData.xf - droneData.xi));
		defineBody();
	

		// SENTIDO DE LA MARCHA

		this.setToDestroy = false;
		this.destroyed = false;
		this.stateTime = 0;
	}

	public void draw(Batch batch) {

		if (!destroyed) {
			super.draw(batch);
		} else if (destroyed) {

			screen.actives().entities1().removeValue(this, true);
			Pools.get(Drone.class).free(this);

		}
	}

	

	protected void defineBody() {
		bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = screen.getWorld().createBody(bdef);
		b2body.setGravityScale(0f);
		b2body.setFixedRotation(true);// no rota
		velocity = new Vector2((float) (this.modulo * Math.cos(fase)),
				(float) (this.modulo * Math.sin(fase)));
		b2body.setLinearVelocity(velocity);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(60.0f / BeachAdventure.PPM, 25.0f / BeachAdventure.PPM);

		fdef = new FixtureDef();
		fdef.filter.categoryBits = BeachAdventure.ENEMY_BIT;
		fdef.filter.maskBits = BeachAdventure.GROUND_BIT
				| BeachAdventure.PLAYER_BIT | BeachAdventure.WEAPONS_BIT
				| BeachAdventure.BRICK_BIT;
		fdef.isSensor = false;
		fdef.shape = shape;
		fdef.restitution = 1f;// bouncines!! 0-1
		fdef.density = 1f;// 10 es la densidad kg/m2
		super.getB2body().createFixture(fdef).setUserData(this);
		shape.dispose();
	}

	@Override
	public void reset() {
		stateTime = 0;
		this.setToDestroy = false;
		this.destroyed = false;
	}

	

	@Override
	public void dispose() {
		drone1T.dispose();
		drone2T.dispose();
		drone3T.dispose();
		drone4T.dispose();
	}

	

	@Override
	public void notifySpawner() {
		screen.actives().getDroneSource().kill();
		
	}

	@Override
	public void hitByGround() {
		//setToDestroy = true;
		
	}

}
