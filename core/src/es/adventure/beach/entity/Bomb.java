package es.adventure.beach.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Pools;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class Bomb extends Entity{
	private float EXPLOSION_TIME = 3f;
	private float stateTimer;
	private boolean setToDestroy;
	private boolean destroyed;
	private float angularSpeed;// as a function of vx
	private Texture primary_RT;
	private TextureRegion primary_R;// no flip
	private int reflectBody;
	private BodyDef bdef;

	private FixtureDef fdef;

	private float vx;
	private int ready;

	public Bomb(GameScreen screen) {
		super(screen);
		this.stateTimer = 0;
		this.setToDestroy = false;
		this.destroyed = false;
		ready = 1;
		primary_RT=new Texture("BOMB1.png");
		primary_R = new TextureRegion(primary_RT);
		super.setBounds(getX(), getY(), 128f / BeachAdventure.PPM,
				64f / BeachAdventure.PPM);
		super.halfW = super.getWidth() / 2;
		super.halfH = super.getHeight() / 2;
		
		super.setOrigin(64/ BeachAdventure.PPM,
				32 / BeachAdventure.PPM);
		super.setRegion(primary_R);
		this.setToDestroy = false;
		this.destroyed = false;
		
		
		


	}

	public void update(float dt) {
		stateTimer += dt;
		if (destroyed) {
			stateTimer = 0;
		} else if (setToDestroy && !destroyed) {
			if (stateTimer > EXPLOSION_TIME) {
				screen.getWorld().destroyBody(super.getB2body());
				destroyed = true;
			}
			super.setPosition(super.getB2body().getPosition().x-halfW,
					super.getB2body().getPosition().y-halfH );

			super.setRotation(super.getB2body().getAngle() * 180 / 3.1419f);

		} else if (!destroyed) {
			stateTimer = 0;
			super.setPosition(super.getB2body().getPosition().x-halfW ,
					super.getB2body().getPosition().y-halfH );

			super.setRotation(super.getB2body().getAngle() * 180 / 3.1419f);
		}

		// }
	}

	public void draw(Batch batch) {
		if (!destroyed || stateTimer < EXPLOSION_TIME) {
			super.draw(batch);
		} else {

			screen.actives().entities0().removeValue(this, true);
			
			Pools.get(Bomb.class).free(this);

		}

	}

	public void init(float x, float y, float vx) {
		super.setPosition(x, y);

	
		this.vx = vx;
	

		defineBody();

	}

	public void defineBody() {
		bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
	
		super.setB2body(screen.getWorld().createBody(bdef));
		
		super.getB2body().setLinearVelocity(vx, 0);

		if (vx > 0) {
			reflectBody=1;
			angularSpeed = -1.5f;

		} else if (vx < 0) {
			reflectBody=-1;
			super.flip(true	, false);
			angularSpeed = 1.5f;
		}
		super.getB2body().setAngularVelocity(angularSpeed);

		fdef = new FixtureDef();

		PolygonShape pshape = new PolygonShape();
		pshape.setAsBox(55.0f/ BeachAdventure.PPM, 26.0f/ BeachAdventure.PPM);
		fdef.shape = pshape;
		fdef.restitution = 0.5f;
		fdef.density = 100f;// 10 es la densidad kg/m2
		// fdef.friction = 0.99f;// 0-
		fdef.filter.categoryBits = BeachAdventure.BOMB_BIT;
		fdef.filter.maskBits = BeachAdventure.DRONE1_BIT
				| BeachAdventure.GROUND_BIT | BeachAdventure.PLAYER_BIT
				| BeachAdventure.BOMB_BIT;
		super.getB2body().createFixture(fdef).setUserData(this);
		pshape.dispose();
	
	


	}

	public void hitByGround() {
		setToDestroy = true;
		angularSpeed=0;
	}

	public void hitByPlayer() {
		if (ready == 1) {
			angularSpeed=0;
			setToDestroy = true;
			screen.getVidaArsenal().shot();//Damage to player
			ready--;

		}

	}

	@Override
	public void reset() {
		setX(0);
		setY(0);
		
		ready = 1;
		setToDestroy = false;
		destroyed = false;
		if (vx > 0) {
			
			

		} else if (vx < 0) {
			
			super.flip(true	, false);
			
		}
	}

	@Override
	public void dispose() {
		primary_RT.dispose();
		
	}
}
