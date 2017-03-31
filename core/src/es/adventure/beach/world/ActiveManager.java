package es.adventure.beach.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import es.adventure.beach.entity.Entity;
import es.adventure.beach.entity.IntedPowUp;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.singleton.ScreenManager;
import es.adventure.beach.spawners.AirplaneSource;
import es.adventure.beach.spawners.B2Source;
import es.adventure.beach.spawners.DroneSource;
import es.adventure.beach.spawners.IntedPowUpSource;
import es.adventure.beach.spawners.NubeSource;
import es.adventure.beach.spawners.Spawner;

public class ActiveManager implements Disposable {
	private AirplaneSource airplaneSource;
	private B2Source b2Source;
	private DroneSource droneSource;
	private NubeSource nubeSource;
	private IntedPowUpSource intedSource;
	private Array<Entity> activeEntities0 = new Array<Entity>();
	private Array<Entity> activeEntities1 = new Array<Entity>();
	private Array<Spawner> spawners = new Array<Spawner>();

	public Array<Entity> entities0() {
		return activeEntities0;
	}

	public Array<Entity> entities1() {
		return activeEntities1;
	}

	private final Array<IntedPowUp> activeIntedPowUp = new Array<IntedPowUp>();

	public ActiveManager(GameScreen gameScreen) {

		airplaneSource = new AirplaneSource(gameScreen, 0.8f);
		b2Source = new B2Source(gameScreen, 0.1f);
		droneSource = new DroneSource(gameScreen, 0.1f);
		nubeSource = new NubeSource(gameScreen, 0f);
		intedSource = new IntedPowUpSource(gameScreen, 0f);
		spawners.add(airplaneSource);
		spawners.add(b2Source);
		spawners.add(droneSource);
		spawners.add(nubeSource);
		spawners.add(intedSource);
	}

	public void update(float dt) {

		for (int i = 0; i < spawners.size; i++) {
			spawners.get(i).update(dt);
		}
		for (int i = 0; i < activeEntities0.size; i++) {
			activeEntities0.get(i).update(dt);
		}

		for (int i = 0; i < activeEntities1.size; i++) {
			activeEntities1.get(i).update(dt);
		}
		for (int i = 0; i < activeIntedPowUp.size; i++) {
			activeIntedPowUp.get(i).update(dt);
		}
	}

	public void render(float dt) {

		for (int i = 0; i < activeEntities0.size; i++) {
			activeEntities0.get(i).draw(ScreenManager.getInstance().getBatch());
		}

		for (int i = 0; i < activeEntities1.size; i++) {
			activeEntities1.get(i).draw(ScreenManager.getInstance().getBatch());
		}
		for (int i = 0; i < activeIntedPowUp.size; i++) {
			activeIntedPowUp.get(i)
					.draw(ScreenManager.getInstance().getBatch());
		}
	}

	public final Array<IntedPowUp> inted() {
		return activeIntedPowUp;
	}

	@Override
	public void dispose() {
		Gdx.app.log("ACTIVEMANAGER DISPOSED", "");
		for (int i = 0; i < activeEntities0.size; i++) {
			activeEntities0.get(i).dispose();
		}

		for (int i = 0; i < activeEntities1.size; i++) {
			activeEntities1.get(i).dispose();
		}
		for (int i = 0; i < activeIntedPowUp.size; i++) {
			activeIntedPowUp.get(i).dispose();
		}
		spawners.clear();
		activeEntities0.clear();
		activeEntities1.clear();
		activeIntedPowUp.clear();

	}

	public int processScores() {
		float totalScore = 0.00f;
		for (int i = 0; i < spawners.size-1; i++) {
			totalScore += spawners.get(i).calculateScore();

		}
		int wise_stars = 0;
		// ///Formula for wise
		if (totalScore > 0.9f) {
			wise_stars = 3;
		} else if (totalScore > 0.35f) {
			wise_stars = 2;
		} else if (totalScore >= 0.1f) {
			wise_stars = 1;
		}
		Gdx.app.log(Float.toString(totalScore), "score");
		Gdx.app.log(Integer.toString(wise_stars), "stars");
		return wise_stars;
	}

	public IntedPowUpSource getIntedSource() {
		return intedSource;
	}

	public void setIntedSource(IntedPowUpSource intedSource) {
		this.intedSource = intedSource;
	}

	public AirplaneSource getAirplaneSource() {
		return airplaneSource;
	}

	public B2Source getB2Source() {
		return b2Source;
	}

	public DroneSource getDroneSource() {
		return droneSource;
	}

	public NubeSource getNubeSource() {
		return nubeSource;
	}

}
