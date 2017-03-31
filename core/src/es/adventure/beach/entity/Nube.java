package es.adventure.beach.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;

import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.serialization.NubeData;

public class Nube extends Entity implements Disposable{

	private Vector2 velocity;
	private Texture primary_RT;
	private TextureRegion primary_R;// no flip
	private boolean setToDestroy;
	private boolean destroyed;
	private BodyDef bdef;
	
	private FixtureDef fdef;


	public Nube(GameScreen screen) {

		super(screen);
		primary_RT=new Texture("NUBE.png");
		primary_R = new TextureRegion(primary_RT);

		super.setBounds(getX(), getY(), 128f / BeachAdventure.PPM,
				64f / BeachAdventure.PPM);
		super.halfW = super.getWidth() / 2;
		super.halfH = super.getHeight() / 2;
		super.setRegion(primary_R);
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

			super.getB2body().setLinearVelocity(velocity);
			super.setPosition(super.getB2body().getPosition().x - halfW,
					super.getB2body().getPosition().y - halfH);

		}

		// b2body.setLinearVelocity(velocity);

	}

	public void init(NubeData nubedata) {
		this.velocity = new Vector2(
				(float) (nubedata.modulo * Math.cos(nubedata.fase)),
				(float) (nubedata.modulo * Math.sin(nubedata.fase)));
		super.setPosition(nubedata.xi, nubedata.yi);

		defineBody();
	}

	public void draw(Batch batch) {
		if (!destroyed) {
			super.draw(batch);

		} else if (destroyed) {

			screen.actives().entities1().removeValue(this, true);
			Pools.get(Nube.class).free(this);

		}
	}

	protected void defineBody() {
		bdef = new BodyDef();
		bdef.position.set(super.getX(), super.getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		super.setB2body(super.screen.getWorld().createBody(bdef));
		super.getB2body().setLinearVelocity(velocity);
		super.getB2body().setFixedRotation(true);// no rota

		fdef = new FixtureDef();

		// ///////////////////////////////
		PolygonShape pshape = new PolygonShape();

//		Vector2[] vertice = new Vector2[4];
//		// this.pol= new Polygon(pol.getVertices());
//
//		vertice[0] = new Vector2(-64, -32).scl(1 / BeachAdventure.PPM);
//		// vertice[1] = new Vector2(-20, -10).scl(1 / BeachAdventure.PPM);
//		vertice[1] = new Vector2(-33, 32).scl(1 / BeachAdventure.PPM);
//
//		vertice[2] = new Vector2(33, 32).scl(1 / BeachAdventure.PPM);
//
//		vertice[3] = new Vector2(64, -32).scl(1 / BeachAdventure.PPM);

		pshape.setAsBox(128.0f/BeachAdventure.PPM, 128.0f/BeachAdventure.PPM);
		// /////////////////////////////

		fdef.filter.categoryBits = BeachAdventure.NUBE_BIT;
		fdef.filter.maskBits = BeachAdventure.GROUND_BIT
				| BeachAdventure.DRONE1_BIT;

		fdef.shape = pshape;
		fdef.isSensor = true;
		fdef.restitution = 0f;// bouncines!! 0-1
		fdef.density = 100f;// 10 es la densidad kg/m2
		// fdef.friction = 0.99f;// 0-

		super.getB2body().createFixture(fdef).setUserData(this);
		pshape.dispose();

	}

	@Override
	public void reset() {
		this.setToDestroy = false;
		this.destroyed = false;

	}


	@Override
	public void dispose() {
		primary_RT.dispose();
		
	}

	
	public void hitByGround() {
		setToDestroy = false;
		destroyed = false;
		
	}

}
