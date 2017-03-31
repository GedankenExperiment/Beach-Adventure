package es.adventure.beach.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import es.adventure.beach.main.Assets;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.serialization.IntedPowUpData;

public class IntedPowUp extends Entity {

	private boolean setToDestroy;
	private boolean destroyed;
	private Texture primary_RT;
	private TextureRegion primary_R;// no flip

	private IntedPowUpData intedData;

	public IntedPowUp(GameScreen screen, IntedPowUpData intedData) {
		super(screen);
		this.screen = screen;
		this.intedData = intedData;
		this.setToDestroy = false;
		this.destroyed = false;
		primary_RT = new Texture("MENUBACK.png");
		primary_R = new TextureRegion(primary_RT);

		this.setOrigin(32 / BeachAdventure.PPM, 32f / BeachAdventure.PPM);

		this.setBounds(getX(), getY(), 64 / BeachAdventure.PPM,
				64f / BeachAdventure.PPM);

		this.setX(intedData.xi);
		this.setY(intedData.yi);
		this.setRegion(primary_R);
		super.halfW = super.getWidth() / 2;
		super.halfH = super.getHeight() / 2;
		defineBody();
	}

	public void intedSwitch() {
		switch (intedData.intcode) {
		case 0:
			screen.getPistolArsenal().cargadorExtra();
			setToDestroy = true;
			if (!screen.getPistolArsenal().available()) {
				screen.getPlayer().reload();
				screen.getDrone1().reload();
			}
			break;
		case 1:// ESCOPETA
			screen.getEscopetaArsenal().cargadorExtra();
			this.setToDestroy = true;
			if (!screen.getEscopetaArsenal().available()) {
				screen.getPlayer().reload();
				screen.getDrone1().reload();
			}

			break;
		case 2:// MP5
			screen.getMp5Arsenal().cargadorExtra();
			setToDestroy = true;
			if (!screen.getMp5Arsenal().available()) {
				screen.getPlayer().reload();
				screen.getDrone1().reload();
			}

			break;

		case 3:// LASER

			break;
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

		}

	}

	public void draw(Batch batch) {
		if (!destroyed) {
			super.draw(batch);
		} else if (destroyed) {

			screen.actives().inted().removeValue(this, true);
			// screen.pools().inted().free(this);

		}

	}

	protected void defineBody() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(intedData.xi, intedData.yi);
		bdef.type = BodyDef.BodyType.DynamicBody;
		super.setB2body(screen.getWorld().createBody(bdef));
		// b2body.setGravityScale(1f);
		// b2body.setFixedRotation(true);// no rota

		FixtureDef fdef = new FixtureDef();

		// ///////////////////////////////
		PolygonShape pshape = new PolygonShape();

		pshape.setAsBox(32 / BeachAdventure.PPM, 32 / BeachAdventure.PPM);
		// /////////////////////////////

		fdef.filter.categoryBits = BeachAdventure.INTED_ARTIFACT_BIT;
		fdef.filter.maskBits = BeachAdventure.DRONE1_BIT
				| BeachAdventure.GROUND_BIT | BeachAdventure.PLAYER_BIT;

		fdef.shape = pshape;
		// fdef.isSensor = true;
		fdef.restitution = 0f;// bouncines!! 0-1
		fdef.density = 100f;// 10 es la densidad kg/m2
		// fdef.friction = 0.99f;// 0-

		super.getB2body().createFixture(fdef).setUserData(this);
		pshape.dispose();

	}





	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		primary_RT.dispose();
		
	}


	public void hitByGround() {
		// Timer before disappear gets negative and wait to zero before being freed
		
	}
}
