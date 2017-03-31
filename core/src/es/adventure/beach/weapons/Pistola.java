package es.adventure.beach.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class Pistola extends Weapon {
	public static final int WEAPON_CODE=0; 
	private static String regionName = "bullet";
	private static float modulus = 300f / BeachAdventure.PPM;

	public Pistola() {
	}

	public Pistola(GameScreen screen, float x, float y, double beta,Arsenal arsenal) {
		super(screen,WEAPON_CODE, x, y, beta, modulus, regionName,arsenal);

		this.setOrigin(8 / BeachAdventure.PPM, 8 / BeachAdventure.PPM);
		setBounds(getX(), getY(), 8 * 2 / BeachAdventure.PPM,
				8 * 2 / BeachAdventure.PPM);

		this.setRotation((float) (beta * 180 / 3.1415f));// is using DEGREES
	}

	public void draw(Batch batch) {
		if (!destroyed || stateTime < timeForEffect) {
			super.draw(batch);
		} else if (destroyed) {
			// alive = false;

			screen.actives().entities1().removeValue(this, true);
			Pools.get(Pistola.class).free(this);

		}

	}

	



}
