package es.adventure.beach.spawners;

import com.badlogic.gdx.utils.Pools;

import es.adventure.beach.entity.B2;
import es.adventure.beach.entity.Nube;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.serialization.NubeData;

public class NubeSource extends Spawner {

	private float stateTime;
	private int nubeDataIndex;
	private NubeData currentSD;
	private Nube nubeItem;

	public NubeSource(GameScreen gameScreen,float weight) {
		super(gameScreen,weight);
		nubeDataIndex = 0;
		// b2body.setGravityScale(0f);
		stateTime = 0;

		currentSD = screen.getLevelData().nubesArray.get(nubeDataIndex);

	}

	@Override
	public void update(float dt) {
		stateTime += dt;

		if (stateTime > currentSD.sp) {
			stateTime = 0;
			// /Add AIRPLANE airplane item as in Tourist
			if (nubeDataIndex < screen.getLevelData().nubesArray.size) {
				nubeItem = Pools.get(Nube.class).obtain();
				nubeItem.init(currentSD);
				screen.actives().entities1().add(nubeItem);
				screen.getActiveManager().getNubeSource().spawn();
				nubeDataIndex++;
				if (nubeDataIndex < screen.getLevelData().nubesArray.size) {
					currentSD = screen.getLevelData().nubesArray
							.get(nubeDataIndex);
				}

			} else {
	

			}
		}

	}
}
