package es.adventure.beach.spawners;

import com.badlogic.gdx.utils.Pools;

import es.adventure.beach.entity.B2;
import es.adventure.beach.entity.Drone;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.serialization.DroneData;

public class DroneSource extends Spawner {
	private float stateTime;

	private Drone droneItem;
	private int dronDataIndex;
	private DroneData currentSD;

	public DroneSource(GameScreen gameScreen,float weight) {
		super(gameScreen,weight);

		dronDataIndex = 0;

	

		stateTime = 0;

		currentSD = screen.getLevelData().dronesArray.get(dronDataIndex);

	}

	@Override
	public void update(float dt) {
		stateTime += dt;

		if (stateTime > currentSD.sp) {
			stateTime = 0;
			if (dronDataIndex < screen.getLevelData().dronesArray.size) {
				droneItem = Pools.get(Drone.class).obtain();
				// System.out.println(currentSD.modulo);
				droneItem.init(currentSD);
				screen.actives().entities1().add(droneItem);
				screen.getActiveManager().getDroneSource().spawn();
				;
				// System.out.println("size: "
				// + screen.actives().drones().size);
				// System.out.println("free"
				// + screen.pools().airplanes().getFree());
				// System.out.println("pool peak"
				// + screen.pools().airplanes().peak);

				dronDataIndex++;
				if (dronDataIndex < screen.getLevelData().dronesArray.size) {
					currentSD = screen.getLevelData().dronesArray
							.get(dronDataIndex);
				}

			} else {
				// System.out.println(" finished Drones");

			}
		}
	}
}
