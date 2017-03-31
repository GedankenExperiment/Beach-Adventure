package es.adventure.beach.weapons;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pools;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class Laser extends Weapon{
	public static final int WEAPON_CODE=4; 
	private static String regionName="bullet";
	private static float modulus = 700f / BeachAdventure.PPM;
	
	public Laser(GameScreen screen, float x, float y, double beta,Arsenal arsenal) {
		super(screen,WEAPON_CODE, x, y, beta,modulus,regionName,arsenal);

		this.setOrigin(8 / BeachAdventure.PPM, 8 / BeachAdventure.PPM);
		setBounds(getX(), getY(), 8 * 2 / BeachAdventure.PPM,
				8 * 2 / BeachAdventure.PPM);
		
		this.setRotation((float) (beta * 180 / 3.1415f));// is using DEGREES
	}

	

	public void draw(Batch batch) {
		if (!destroyed || stateTime < 0.135f * 15) {
			super.draw(batch);
		} else if (destroyed) {
			// alive = false;

			screen.actives().entities1().removeValue(this, true);
			Pools.get(Laser.class).free(this);

		}

	}

	

	

	@Override
	public void reset() {
		// setPosition(0, 0);
		this.beta = 0;
		stateTime = 0;
		this.setRegion(bullR);

		this.setToDestroy = false;
		this.destroyed = false;

		// this.stateTime = 0;
		// setPosition(screen.getPlayer().b2body.getPosition().x,
		// screen.getPlayer().b2body.getPosition().y);
		// bdef.position.set(getX(), getY());
		// this.beta = Math.PI / 2;
		// b2body.setLinearVelocity(velocidad);
	}

}
