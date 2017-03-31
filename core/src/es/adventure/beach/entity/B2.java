package es.adventure.beach.entity;

import com.badlogic.gdx.Gdx;
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
import es.adventure.beach.serialization.B2Data;

public class B2 extends Enemy {
	private static final String name = "B2";
	private  static final int[] damageList={1,1,1,5,5};
	private static final int initHitPoints = 2;
	private static final int damageOfLaser = 3;

	
	
	private Vector2 velocity;
	
	private Texture b2T;
	private TextureRegion primary;// no flip

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
	private float t_fall, t_horiz;
	private boolean ready;
	private Bomb bombItem;
	private float tRecalculate;

	public B2(GameScreen screen) {
		super(screen,name,initHitPoints,damageList);
//things from Entity
		t_fall = 0;
		t_horiz = 0;
	
		
		this.ready = true;
		
		tRecalculate = 0;
		
	
		b2T = new Texture("B21.png");
		primary = new TextureRegion(b2T);

		super.setOrigin(128f / BeachAdventure.PPM, 64 / BeachAdventure.PPM);

		super.setBounds(getX(), getY(), 256f / BeachAdventure.PPM,
				128 / BeachAdventure.PPM);
		super.halfW = super.getWidth() / 2;
		super.halfH = super.getHeight() / 2;
	}

	@Override
	public void reset() {
		// setPosition(0, 0);
		tRecalculate = 0;
		this.vida = initHitPoints;
		// this.velocity.x=0;
		this.ready = true;
		
		// x4shift = 0;
		// y4shift = 0;
		// x3shift = 0;
		// y3shift = 0;
		// x2shift = 0;
		// y2shift = 0;
		// x1shift = 0;
		// y1shift = 0;
		// pe4.reset();
		// pe3.reset();
		// pe2.reset();
		// pe1.reset();

	}

	public void init(B2Data airplaneData) {
		this.modulo = airplaneData.modulo;
		super.setX(airplaneData.xi);
		super.setY(airplaneData.yi);

		this.fase = Math.atan2((double) (airplaneData.yf - super.getY()),
				(double) (airplaneData.xf - super.getX()));

		defineBody();
		vida = initHitPoints;
		
		super.setRegion(primary);
		// SENTIDO DE LA MARCHA
		if (this.velocity.x > 0) {
			super.flip(false, false);
		} else if (this.velocity.x < 0) {

			super.flip(true, false);
		}

		this.setToDestroy = false;
		this.destroyed = false;
	}

	public void update(float dt) {
		tRecalculate += dt;
		if (destroyed) {
			// setRegion(bull);
		} else if (setToDestroy && !destroyed) {

			screen.getWorld().destroyBody(super.getB2body());
			destroyed = true;

		} else if (!destroyed) {

			super.setPosition(super.getB2body().getPosition().x - halfW,
					super.getB2body().getPosition().y - halfH);
			// super.getB2body().setLinearVelocity(velocity);

			if (tRecalculate > .3f && ready) {
				tRecalculate = 0;
				t_fall = (float) Math.sqrt(2 *

				(super.getB2body().getPosition().y - 1.28) / (-screen.getGRAVITY().y));
				t_horiz = (screen.getPlayer().getB2body().getPosition().x - super.getB2body()
						.getPosition().x) / super.getB2body().getLinearVelocity().x;
			}

			if (t_horiz < t_fall && ready) {
				ready = false;

				bombItem = Pools.get(Bomb.class).obtain();
				bombItem.init(super.getB2body().getPosition().x,
						super.getB2body().getPosition().y,
						super.getB2body().getLinearVelocity().x);
				screen.actives().entities0().add(bombItem);
				// screen.getCounter().s_shots();

			}

		}

	}

	public void draw(Batch batch) {

		if (!destroyed) {
			super.draw(batch);

		} else if (destroyed) {

			screen.actives().entities1().removeValue(this, true);
			Pools.get(B2.class).free(this);

		}
	}

	protected void defineBody() {
		bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		super.setB2body(screen.getWorld().createBody(bdef));
		this.velocity = new Vector2((float) (this.modulo * Math.cos(fase)),
				(float) (this.modulo * Math.sin(fase)));
		// this.velocity.x = ;
		// this.velocity.y = ;
		super.getB2body().setGravityScale(0f);
		super.getB2body().setLinearVelocity(this.velocity.x, this.velocity.y);
		// if (super.getX() < BeachAdventure.VW_WIDTH / 2) {
		// this.setRegion(primary);
		// } else {
		// this.setRegion(primary_L);
		// }
		super.getB2body().setLinearVelocity(velocity);
		super.getB2body().setFixedRotation(true);// no rota

		fdef = new FixtureDef();

		// ///////////////////////////////
		PolygonShape pshape = new PolygonShape();

		Vector2[] vertice = new Vector2[4];
		// this.pol= new Polygon(pol.getVertices());

		vertice[0] = new Vector2(-50, -5).scl(1 / BeachAdventure.PPM);
		// vertice[1] = new Vector2(-20, -10).scl(1 / BeachAdventure.PPM);
		vertice[1] = new Vector2(-50, 5).scl(1 / BeachAdventure.PPM);

		vertice[2] = new Vector2(50, 5).scl(1 / BeachAdventure.PPM);

		vertice[3] = new Vector2(50, -5).scl(1 / BeachAdventure.PPM);

		pshape.set(vertice);
		// /////////////////////////////

		fdef.filter.categoryBits = BeachAdventure.ENEMY_BIT;
		fdef.filter.maskBits = BeachAdventure.GROUND_BIT
				| BeachAdventure.WEAPONS_BIT

		;
		fdef.isSensor=true;
		fdef.shape = pshape;
		fdef.restitution = 0f;// bouncines!! 0-1
		fdef.density = 100f;// 10 es la densidad kg/m2
		// fdef.friction = 0.99f;// 0-

		super.getB2body().createFixture(fdef).setUserData(this);
		pshape.dispose();

	}

	@Override
	public void hitByGround() {
		setToDestroy = true;

	}

	public void particleTriger(float bx) {
		// switch (vida) {
		// case 4:
		// pe4.start();
		// x4shift = bx - b2body.getPosition().x;
		// break;
		// case 3:
		// pe3.start();
		// x3shift = bx - b2body.getPosition().x;
		// break;
		// case 2:
		// pe2.start();
		// x2shift = bx - b2body.getPosition().x;
		// break;
		// case 1:
		// pe1.start();
		// x1shift = bx - b2body.getPosition().x;
		// break;
		// default:
		// break;
		// }
	}

	public void hitOnBullet(float bx, float by) {

		if (this.vida > 0)
			this.vida--;

		if (this.vida <= 0) {
			screen.getActiveManager().getB2Source().kill();;
			setToDestroy = true;
		}

		// Create effect:

	}

	public void hitOnEscopeta(float bx, float by) {

		if (this.vida > 0)
			this.vida--;
		if (this.vida <= 0) {
			
			setToDestroy = true;
		}

		// particleTriger(bx);

	}

	public void hitOnMp5(float bx, float by) {

		if (this.vida > 0)
			this.vida--;
		if (this.vida <= 0) {
			setToDestroy = true;
			screen.getActiveManager().getB2Source().kill();
		}

		// particleTriger(bx);

	}

	public void aterrizar() {

		super.getB2body().setGravityScale(4f * screen.getGRAVITY().y);

	}

	public void despegar() {

		super.getB2body().setGravityScale(-3f * screen.getGRAVITY().y);
	}

	public void estabilizar() {
		super.getB2body().setLinearVelocity(super.getB2body().getLinearVelocity().x, 0f);
		super.getB2body().setGravityScale(1f);

	}

	@Override
	public void dispose() {
		b2T.dispose();

	}

	@Override
	public void notifySpawner() {
		screen.actives().getB2Source().kill();
		
	}

}
