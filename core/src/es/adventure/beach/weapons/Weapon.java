package es.adventure.beach.weapons;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import es.adventure.beach.entity.Entity;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public abstract class Weapon extends Entity {
	public int WEAPON_CODE;
	protected Arsenal arsenal;
	
	protected float stateTime;
	protected Vector2 velocidad;
	protected TextureRegion bullR;
	protected String bulletTR;
	protected double beta;
	protected Array<TextureRegion> frames;
	protected Animation simpleExplosion;
	protected boolean setToDestroy;
	protected boolean destroyed;
	protected float modulus;
	protected BodyDef bdef;
	protected FixtureDef fdef;
	protected boolean canHurtPlayer;
	protected float timeForEffect;
	protected static final float timeForEffect0 = 0;;// this ll be called in the
														// draw()//can be 0
	// or 2.02f
	protected static final float timeForEffect1 = 2.02f;

	//
	public Weapon() {
	}

	public Weapon(GameScreen gameScreen,int WEAPON_CODE, float x, float y, double beta,
			float modulus, String bulletTR,  Arsenal arsenal) {
		super(gameScreen);
		setPosition(x, y);
		setRotation((float) (beta * 180 / 3.1415f));// is using DEGREES
		this.WEAPON_CODE = WEAPON_CODE;
		this.arsenal = arsenal;
		this.bulletTR = bulletTR;
		this.modulus = modulus;
		this.setToDestroy = false;
		this.destroyed = false;
		this.canHurtPlayer = false;
		this.beta = beta;
		this.stateTime = 0;
		
		frames = new Array<TextureRegion>();

		for (int i = 0; i < 15; i++) {
			frames.add(new TextureRegion(screen.getExplosion()
					.findRegion("bulletExplosion"), (i) * 32, 0, 32, 32));// i
		}
		simpleExplosion = new Animation(0.135f, frames);
		bullR = new TextureRegion(screen.getExplosion().findRegion(bulletTR), 0, 0, 32, 32);
		this.setRegion(bullR);
		velocidad = new Vector2(modulus * (float) (Math.cos(beta)), modulus
				* (float) (Math.sin(beta)));
	}

	@Override
	public void reset() {
		this.beta = 0;
		stateTime = 0;
		this.setRegion(bullR);

		this.setToDestroy = false;
		this.destroyed = false;
		timeForEffect = 0;
	}

	public void init(float posX, float posY, double beta, boolean canHurtPlayer) {
		this.canHurtPlayer = canHurtPlayer;
		setPosition(posX, posY);
		setRotation((float) (beta * 180 / 3.1415f));// is using DEGREES
		this.beta = beta;
		velocidad = new Vector2(modulus * (float) (Math.cos(beta)), modulus
				* (float) (Math.sin(beta)));
		defineBody();
		timeForEffect = 0;

		this.setToDestroy = false;
		this.destroyed = false;
		this.stateTime = 0;
		velocidad = new Vector2(modulus * (float) (Math.cos(beta)), modulus
				* (float) (Math.sin(beta)));
	}

	@Override
	public void update(float dt) {
		stateTime += dt;

		if (destroyed) {
			// explosion animation is only trigered after Enemy/Players
			// collision
			setRegion(simpleExplosion.getKeyFrame(stateTime, true));
		} else if (setToDestroy && !destroyed) {
			screen.getWorld().destroyBody(super.getB2body());
			destroyed = true;

			stateTime = 0;

		} else if (!destroyed) {
			super.getB2body().setLinearVelocity(velocidad);
			setPosition(super.getB2body().getPosition().x - getWidth() / 2,
					super.getB2body().getPosition().y - getHeight() / 2);
			if (stateTime > 1f) {
				setToDestroy = true;
				timeForEffect=timeForEffect0;
			}
		}
	}

	@Override
	protected void defineBody() {
		bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		super.setB2body(screen.getWorld().createBody(bdef));
		super.getB2body().setGravityScale(0f);
		super.getB2body().setLinearVelocity(velocidad);
		fdef = new FixtureDef();
		velocidad = new Vector2(modulus * (float) (Math.cos(beta)), modulus
				* (float) (Math.sin(beta)));
		PolygonShape pshape = new PolygonShape();
		pshape.setAsBox(10.0f / BeachAdventure.PPM, 10.0f / BeachAdventure.PPM);
		fdef.filter.categoryBits = BeachAdventure.WEAPONS_BIT;
		fdef.filter.groupIndex = -1;
		fdef.filter.maskBits =BeachAdventure.ENEMY_BIT;
		fdef.isSensor = false;

		fdef.shape = pshape;
		fdef.density = 100.0f;// 10 es la densidad kg/m2
		// fdef.friction = 0.99f;// 0-
		super.getB2body().createFixture(fdef).setUserData(this);

	}




	public void hitByEnemy() {
		setToDestroy = true;
		arsenal.hits();
		timeForEffect = timeForEffect1;

	}


	@Override
	public void dispose() {
		
		bullR.getTexture().dispose();

	}

}
