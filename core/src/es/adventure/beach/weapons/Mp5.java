package es.adventure.beach.weapons;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pools;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class Mp5 extends Weapon {
	public static final int WEAPON_CODE=2; 
	private static final float modulus = 400f / BeachAdventure.PPM;
	
	private static final String regionName = "mp5Bullet";

	public Mp5(GameScreen screen, float x, float y, double beta,Arsenal arsenal) {
		super(screen,WEAPON_CODE, x, y, beta, modulus, regionName,arsenal);
		setPosition(x, y);
		// init(x, y, beta);
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
			Pools.get(Mp5.class).free(this);

		}

	}







}
