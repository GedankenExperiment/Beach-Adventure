package es.adventure.beach.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;

import es.adventure.beach.entity.Drone;
import es.adventure.beach.entity.Enemy;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.weapons.Escopeta;
import es.adventure.beach.weapons.Laser;
import es.adventure.beach.weapons.Mp5;
import es.adventure.beach.weapons.Pistola;
import es.adventure.beach.weapons.Rocket;

public abstract class AbstPlayer extends Sprite implements Disposable {
	protected static final boolean canHurtPlayer = false;
	protected Sound dessert = Gdx.audio.newSound(Gdx.files
			.internal("audio/sounds/DesertEagle.wav"));
	protected Sound empty = Gdx.audio.newSound(Gdx.files
			.internal("audio/sounds/empty.wav"));
	protected Sound reload = Gdx.audio.newSound(Gdx.files
			.internal("audio/sounds/reload.wav"));
	protected Sound escopeta = Gdx.audio.newSound(Gdx.files
			.internal("audio/sounds/escopeta.wav"));
	protected Sound mp5 = Gdx.audio.newSound(Gdx.files
			.internal("audio/sounds/mp5.wav"));

	protected Vector2 zeroV = new Vector2(0f, 0f);
	protected final float TIME_BETWEEN_FIRE = 0.22f;
	protected final float TIME_BETWEEN_ESCOPETA_FIRE = 1.0f;
	protected final float TIME_BETWEEN_MP5_FIRE = 0.14f;
	protected final float TIME_BETWEEN_ROCKET_FIRE = 0.9f;
	protected final float TIME_BETWEEN_LASER_FIRE = 1.5f;
	private final float RELOAD_TIME = 1.5f;
	protected float mp5Timer;
	protected boolean laserTrigger;

	


	protected GameScreen screen;
	protected Vector2 worldPosition;

	public Vector2 getWorldPosition() {
		return worldPosition;
	}

	protected float stateTimer;
	protected Body b2body;
	protected boolean firing, moving;
	protected float pistolTimer;
	protected float reloadTimer;

	protected double currentBeta;
	protected Pistola pistolItem;
	protected Escopeta escopetaItem;
	protected Mp5 mp5Item;
	protected Rocket rocketItem;
	// /LASER

	protected Vector2 p1 = new Vector2(), p2 = new Vector2(),
			collision = new Vector2(), normal = new Vector2();
	protected ShapeRenderer sr = new ShapeRenderer();

	protected boolean playReloadOnCall;
	private boolean reloadCheck;

	protected final Vector2 initPos;// for camera offset purpose
	RayCastCallback callback;
	public AbstPlayer(GameScreen gameScreen, Vector2 initPos) {
		this.screen = gameScreen;
		this.initPos = initPos;
		stateTimer = 0;
		reloadTimer = 0;
		currentBeta = 1.57;
		reloadCheck = false;
		playReloadOnCall = false;
		laserTrigger = false;
		moving = false;
		 callback = new RayCastCallback() {

			@Override
			public float reportRayFixture(Fixture fixture, Vector2 point,
					Vector2 normal, float fraction) {
				collision.set(point);
				AbstPlayer.this.normal.set(normal).add(point);

				if (fixture.getUserData() instanceof Enemy) {
					Gdx.app.log("LaserReaction Called ", "ENEMY");
					//This tells the Enemy to receive its own  Laser damage
					((Enemy) fixture.getUserData())
							.receiveDamage(((Enemy) fixture.getUserData()).getDamageList()[Laser.WEAPON_CODE]);
					;

				}
				return 1;
			}

		};
	}

	public void rayo(Vector2 diana, Color color) {

		sr.setProjectionMatrix(screen.getGamecam().combined);
		sr.begin(ShapeType.Line);
		if (firing) {
			
			p2.set(diana.x / 100 + screen.getGamecam().position.x
					- BeachAdventure.VW_WIDTH / 2,
					diana.y / 100 + screen.getGamecam().position.y
							- BeachAdventure.VW_HEIGHT / 2);
			p1.set(this.getWorldPosition().x, this.getWorldPosition().y);

			screen.getWorld().rayCast(callback, p1, p2);
			sr.line(p1, p2);
			sr.setColor(color);
			//sr.line(collision, normal);

		} else {

		}

		sr.end();

	}

	public void fire(Vector2 diana, int weaponIndex) {
		if (!reloadCheck) {

			setFiring(true);
			worldPosition = b2body.getPosition();

			currentBeta = Math
					.atan2((diana.y - worldPosition.y * 100
							+ screen.getGamecam().position.y * 100 - BeachAdventure.camY * 100) / 100,
							(diana.x + worldPosition.x * 100
									- BeachAdventure.camX * 100 + 3 * 64)
									/ 100f - worldPosition.x);// la diana lleva
			// un
			// ofset x la
			// camara
			// respecto al
			// mundo

			switch (weaponIndex) {
			case 0:

				if (stateTimer > TIME_BETWEEN_FIRE && !moving) {
					if (screen.getPistolArsenal().available()) {
						stateTimer = 0;
						pistolItem = Pools.get(Pistola.class).obtain();
						pistolItem.init(b2body.getPosition().x,
								b2body.getPosition().y, this.currentBeta,
								canHurtPlayer);
						screen.actives().entities1().add(pistolItem);
						screen.getPistolArsenal().shot();
						if (screen.isAllowedVolume()) {
							dessert.play(screen.getCurrentSoundVolume());
						}
						if (!screen.getPistolArsenal().available()) {
							reload();
							if (screen.isAllowedVolume()) {
								empty.play(screen.getCurrentSoundVolume());
							}

							if (screen.getPistolArsenal().dry()) {
								playReloadOnCall = false;

							} else {
								playReloadOnCall = true;

							}
						}
					}

				}
				// System.out.println("free"
				// + screen.pools().pistols().getFree());
				// System.out.println("pool peak"
				// + screen.pools().pistols().peak);
				break;
			case 1:// ESCOPETA

				if (stateTimer > TIME_BETWEEN_ESCOPETA_FIRE && !moving) {
					if (screen.getEscopetaArsenal().available()) {
						stateTimer = 0;

						escopetaItem = Pools.get(Escopeta.class).obtain();
						escopetaItem.init(b2body.getPosition().x,
								b2body.getPosition().y, this.currentBeta
										+ Math.PI / 18, canHurtPlayer);
						screen.actives().entities1().add(escopetaItem);
						escopetaItem = Pools.get(Escopeta.class).obtain();
						escopetaItem.init(b2body.getPosition().x,
								b2body.getPosition().y, this.currentBeta,
								canHurtPlayer);
						screen.actives().entities1().add(escopetaItem);
						escopetaItem = Pools.get(Escopeta.class).obtain();
						escopetaItem.init(b2body.getPosition().x,
								b2body.getPosition().y, this.currentBeta
										- Math.PI / 18, canHurtPlayer);
						screen.actives().entities1().add(escopetaItem);
						screen.getEscopetaArsenal().shot();
						if (screen.isAllowedVolume()) {
							escopeta.play(screen.getCurrentSoundVolume());
						}
						if (!screen.getEscopetaArsenal().available()) {
							reload();
							if (screen.isAllowedVolume()) {
								empty.play(screen.getCurrentSoundVolume());
							}
							if (screen.getEscopetaArsenal().dry()) {
								playReloadOnCall = false;

							} else {
								playReloadOnCall = true;

							}
						}
					}

				}

				break;
			case 2:// MP5
				if (stateTimer > TIME_BETWEEN_MP5_FIRE && !moving) {
					if (screen.getMp5Arsenal().available()) {
						stateTimer = 0;
						mp5Item = Pools.get(Mp5.class).obtain();
						mp5Item.init(b2body.getPosition().x,
								b2body.getPosition().y, this.currentBeta,
								canHurtPlayer);
						screen.actives().entities1().add(mp5Item);
						screen.getMp5Arsenal().shot();
						if (screen.isAllowedVolume()) {
							mp5.play(screen.getCurrentSoundVolume());
						}
						if (!screen.getMp5Arsenal().available()) {
							reload();
							if (screen.isAllowedVolume()) {
								empty.play(screen.getCurrentSoundVolume());
							}
							if (screen.getMp5Arsenal().dry()) {
								playReloadOnCall = false;

							} else {
								playReloadOnCall = true;

							}
						}
					}

				}

				break;

			case 3:// Rocket
				if (stateTimer > TIME_BETWEEN_ROCKET_FIRE && !moving) {
					if (screen.getRocketArsenal().available()) {
						stateTimer = 0;
						rocketItem = Pools.get(Rocket.class).obtain();
						rocketItem.init(b2body.getPosition().x,
								b2body.getPosition().y, this.currentBeta,
								canHurtPlayer);
						screen.actives().entities1().add(rocketItem);
						screen.getRocketArsenal().shot();
						if (screen.isAllowedVolume()) {
							dessert.play(screen.getCurrentSoundVolume());
						}
						if (!screen.getRocketArsenal().available()) {
							reload();
							if (screen.isAllowedVolume()) {
								empty.play(screen.getCurrentSoundVolume());
							}

							if (screen.getRocketArsenal().dry()) {
								playReloadOnCall = false;

							} else {
								playReloadOnCall = true;

							}
						}
					}

				}
			case 4:// Laser
				if (stateTimer > TIME_BETWEEN_LASER_FIRE && !moving) {
					if (screen.getLaserArsenal().available()) {
						stateTimer = 0;

						laserTrigger = true;

						screen.getLaserArsenal().shot();
						if (screen.isAllowedVolume()) {
							dessert.play(screen.getCurrentSoundVolume());
						}

						if (!screen.getLaserArsenal().available()) {
							reload();

							if (screen.isAllowedVolume()) {
								empty.play(screen.getCurrentSoundVolume());
							}

							if (screen.getLaserArsenal().dry()) {
								playReloadOnCall = false;

							} else {
								playReloadOnCall = true;

							}
						}
					}

				}
			}

		}

	}

	public void reload() {
		reloadTimer = 0;
		reloadCheck = true;
	}

	public void update(float dt) {
		reloadTimer += dt;
		if (reloadCheck) {
			
			if (reloadTimer > RELOAD_TIME) {
				reloadCheck = false;

				screen.getPistolArsenal().reload();
				screen.getEscopetaArsenal().reload();
				screen.getMp5Arsenal().reload();
				screen.getRocketArsenal().reload();
				screen.getLaserArsenal().reload();

			} else if (playReloadOnCall && reloadTimer > RELOAD_TIME - 0.8) {
				playReloadOnCall = false;
				if (screen.isAllowedVolume()) {
					reload.play(screen.getCurrentSoundVolume());
				}
			}
		} else if (this.firing) {
			fire(screen.getStageInputVector(), screen.getCurrentHudWeapon());
			if (laserTrigger) {
				if(stateTimer>TIME_BETWEEN_LASER_FIRE){
					laserTrigger=false;
				//firing=false;
					}
			}
		}

		

	}

	public void setFiring(boolean firing) {
		this.firing = firing;

	}

	public double getCurrentBeta() {
		return currentBeta;
	}

	public Body getB2body() {
		return b2body;
	}
	public boolean isLaserTrigger() {
		return laserTrigger;
	}
	public void setLaserTrigger(boolean laserTrigger) {
		this.laserTrigger = laserTrigger;
	}
}
