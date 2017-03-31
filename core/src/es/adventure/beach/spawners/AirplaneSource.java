package es.adventure.beach.spawners;

import com.badlogic.gdx.utils.Pools;

import es.adventure.beach.entity.Airplane;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.serialization.AirplaneData;

public class AirplaneSource extends Spawner {
	private float stateTime;

	private Airplane airplaneItem;
	private int airpDataIndex;
	private AirplaneData currentSD;

	public AirplaneSource(GameScreen gameScreen,float weight) {
		super(gameScreen,weight);

		airpDataIndex = 0;
		// b2body.setGravityScale(0f);

		stateTime = 0;

		currentSD = screen.getLevelData().airplanesArray.get(airpDataIndex);

	}

	@Override
	public void update(float dt) {
		stateTime += dt;

		if (stateTime > currentSD.sp) {
			stateTime = 0;
			if (airpDataIndex < screen.getLevelData().airplanesArray.size) {
				airplaneItem = Pools.get(Airplane.class).obtain();
				// Pools.get(Airplane.class).obtain();
				airplaneItem.init(currentSD);
				screen.actives().entities1().add(airplaneItem);
				screen.getActiveManager().getAirplaneSource().spawn();
				airpDataIndex++;
				if (airpDataIndex < screen.getLevelData().airplanesArray.size) {
					currentSD = screen.getLevelData().airplanesArray
							.get(airpDataIndex);
				}

			} else {
				// System.out.println(" finished Airplanes");

			}

		}

	}

}
