package es.adventure.beach.spawners;

import es.adventure.beach.entity.IntedPowUp;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.serialization.IntedPowUpData;

public class IntedPowUpSource extends Spawner {
	private float stateTime;
	private int weaponDataIndex;
	private IntedPowUpData currentSD;

	public IntedPowUpSource(GameScreen screen,float weight) {
		super(screen,weight);

		weaponDataIndex = 0;

		// b2body.setGravityScale(0f);

		stateTime = 0;

		if (screen.getLevelData().nubesArray.size > 0) {
			currentSD = screen.getLevelData().intedArray.get(weaponDataIndex);
		} else {
			currentSD = null;
		}
	}

	@Override
	public void update(float dt) {
		stateTime += dt;

		if (stateTime > currentSD.sp) {
			stateTime = 0;

			if (weaponDataIndex < screen.getLevelData().intedArray.size) {

				screen.actives()
						.inted()
						.add(new IntedPowUp(screen,
								screen.getLevelData().intedArray
										.get(weaponDataIndex)));
				weaponDataIndex++;

				if (weaponDataIndex < screen.getLevelData().intedArray.size) {
					currentSD = screen.getLevelData().intedArray
							.get(weaponDataIndex);
				}

			} else {
		

			}

		}
	}

}
