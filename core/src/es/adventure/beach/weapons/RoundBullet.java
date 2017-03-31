package es.adventure.beach.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import com.badlogic.gdx.utils.Pools;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class RoundBullet extends Weapon{
	public static final int WEAPON_CODE=5; 
	private static float modulus = 600 / BeachAdventure.PPM;
	private static BodyDef bdef;
	private CircleShape shape = new CircleShape();
	private static final String bulletTR = "escopetaBullet";
	private static FixtureDef fdef = new FixtureDef();;
	public RoundBullet(GameScreen gameScreen, float x, float y, double beta,Arsenal arsenal) {
		super(gameScreen,WEAPON_CODE, x, y, beta, modulus, bulletTR,arsenal);
		setPosition(x, y);
		// init(x, y, beta);

		setRotation((float) (beta * 180 / 3.1415f));// is using DEGREES

		this.beta = beta;
		// fdef.filter.categoryBits = BeachAdventure.ROCKET_BIT;
		// fdef.filter.maskBits = BeachAdventure.GROUND_BIT
		// | BeachAdventure.AIRPLANE_BIT;
		// fdef.isSensor = true;
		fdef.shape = shape;
		fdef.density = 50.0f;// 10 es la densidad kg/m2
		fdef.restitution = 0.1f;// 0-
		shape.setRadius(5f / BeachAdventure.PPM);

		// velocidad = new Vector2(modulus * (float) (Math.cos(beta)), modulus
		// * (float) (Math.sin(beta)));
		this.setToDestroy = false;
		this.destroyed = false;
		this.stateTime = 0;

		this.setOrigin(8 / BeachAdventure.PPM, 8 / BeachAdventure.PPM);
		setBounds(getX(), getY(), 8 * 2 / BeachAdventure.PPM,
				8 * 2 / BeachAdventure.PPM);

		this.setRotation((float) (beta * 180 / 3.1415f));// is using DEGREES
	}

	public void init(float posX, float posY, double beta) {
		setPosition(posX, posY);
		setRotation((float) (beta * 180 / 3.1415f));// is using DEGREES

		this.beta = beta;
		velocidad = new Vector2(modulus * (float) (Math.cos(beta)), modulus
				* (float) (Math.sin(beta)));
		defineBody();

		this.setToDestroy = false;
		this.destroyed = false;
		this.stateTime = 0;

	}



	public void draw(Batch batch) {
		if (!destroyed) {
			super.draw(batch);
		} else if (destroyed) {
			// alive = false;

			screen.actives().entities1().removeValue(this, true);
			Pools.get(RoundBullet.class).free(this);

		}

	}

	@Override
	public void update(float dt) {

		stateTime += dt;

		if (destroyed) {

		} else if (setToDestroy && !destroyed) {

			screen.getWorld().destroyBody(super.getB2body());
			destroyed = true;
			stateTime = 0;

		} else if (!destroyed) {

			setPosition(super.getB2body().getPosition().x - getWidth() / 2,
					super.getB2body().getPosition().y - getHeight() / 2);

		}
		if (stateTime > 1f) {
			setToDestroy = true;
		}

	}

	@Override
	protected void defineBody() {
		bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		super.setB2body(screen.getWorld().createBody(bdef));
		super.getB2body().setLinearDamping(1 / BeachAdventure.PPM);
		bdef.bullet = true;
		super.getB2body().setGravityScale(0.1f);
		// fdef.isSensor=true;
		fdef.friction = 0.5f;
		fdef.restitution = 0.61f;
		super.getB2body().createFixture(fdef).setUserData(this);
		// pshape.dispose();
		super.getB2body().applyLinearImpulse(velocidad,
				super.getB2body().getPosition(), true);

	}

	


	@Override
	public void hitByEnemy() {
		setToDestroy = true;

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	

}
