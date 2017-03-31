package es.adventure.beach.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Pools;

import es.adventure.beach.main.Assets;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class Escopeta extends Weapon {
	public static final int WEAPON_CODE=1; 
	private static float modulus = 400f / BeachAdventure.PPM;
	private static final String bulletTR = "escopetaBullet";

	

	public Escopeta(GameScreen screen, float x, float y, double beta,Arsenal arsenal) {
		super(screen,WEAPON_CODE, x, y, beta, modulus, bulletTR,arsenal);
		setPosition(x, y);

		setRotation((float) (beta * 180 / 3.1415f));// is using DEGREES

		this.setOrigin(8 / BeachAdventure.PPM, 8 / BeachAdventure.PPM);
		setBounds(getX(), getY(), 8 * 2 / BeachAdventure.PPM,
				8 * 2 / BeachAdventure.PPM);

		// string
		this.setRegion(bullR);
		this.setRotation((float) (beta * 180 / 3.1415f));// is using DEGREES

	}



	public void draw(Batch batch) {
		if (!destroyed || stateTime < timeForEffect) {
			super.draw(batch);
		} else if (destroyed) {
			// alive = false;

			screen.actives().entities1().removeValue(this, true);
			Pools.get(Escopeta.class).free(this);

		}

	}

	


}
