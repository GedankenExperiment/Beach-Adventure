package es.adventure.beach.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Pools;

import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class Rocket extends Weapon {
	public static final int WEAPON_CODE=3; 
	private RoundBullet rbulletItem;

	private boolean go;
	private boolean allowGo;
	private static final int fPower=4;
	private static String regionName = "bullet";
	private static float modulus = 800f / BeachAdventure.PPM;

	public Rocket(GameScreen screen, float x, float y, double beta,Arsenal arsenal) {
		super(screen,WEAPON_CODE, x, y, beta, modulus, regionName,arsenal);

		this.setOrigin(8 / BeachAdventure.PPM, 8 / BeachAdventure.PPM);
		setBounds(getX(), getY(), 8 * 2 / BeachAdventure.PPM,
				8 * 2 / BeachAdventure.PPM);

		this.setRotation((float) (beta * 180 / 3.1415f));// is using DEGREES
	}



	@Override
	public void update(float dt) {

//		stateTime += dt;
//
//		if (destroyed) {
//			setRegion(simpleExplosion.getKeyFrame(stateTime, true));
//		} else if (setToDestroy && !destroyed) {
//			screen.getWorld().destroyBody(super.getB2body());
//			destroyed = true;
//
//			stateTime = 0;
//
//		} else if (!destroyed) {
//			super.getB2body().setLinearVelocity(velocidad);
//			setPosition(super.getB2body().getPosition().x - getWidth() / 2,
//					super.getB2body().getPosition().y - getHeight() / 2);
//			if (stateTime > 1f) {
//				setToDestroy = true;
//				timeForEffect=timeForEffect0;
//			}
//		}
		super.update(dt);
		if (go && allowGo) {

			allowGo = false;
			for (int n = 0; n < 18; n++) {
				rbulletItem = Pools.get(RoundBullet.class).obtain();
				rbulletItem.init(super.getB2body().getPosition().x, super
						.getB2body().getPosition().y, 2 * n * Math.PI / 18);
				screen.actives().entities1().add(rbulletItem);
			}
		}

	}

	public void draw(Batch batch) {
		if (!destroyed || stateTime < timeForEffect) {
			super.draw(batch);
		} else if (destroyed) {
			// alive = false;

			screen.actives().entities1().removeValue(this, true);
			Pools.get(Rocket.class).free(this);

		}

	}

	public void hitByEnemy() {
		explode();
		setToDestroy = true;
		screen.getRocketArsenal().hits();

	}

	public void hitByGround() {
		explode();

		setToDestroy = true;

	}

	@Override
	public void reset() {
		// setPosition(0, 0);
		this.beta = 0;
		stateTime = 0;
		this.setRegion(bullR);
		go = false;
		this.setToDestroy = false;
		this.destroyed = false;
		allowGo = true;

	}

	public void explode() {
		go = true;
		
	}
}
