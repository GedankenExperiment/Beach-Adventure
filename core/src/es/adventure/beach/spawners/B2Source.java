package es.adventure.beach.spawners;

import com.badlogic.gdx.utils.Pools;

import es.adventure.beach.entity.B2;
import es.adventure.beach.scenes.GameScreen;
import es.adventure.beach.serialization.B2Data;

public class B2Source extends Spawner {
	private float stateTime;

	private B2 b2Item;
	private int b2DataIndex;
	private B2Data currentSD;

	public B2Source(GameScreen gameScreen,float weight) {
		super(gameScreen,weight);

		b2DataIndex = 0;
		// b2body.setGravityScale(0f);

		stateTime = 0;

		currentSD = screen.getLevelData().b2sArray.get(b2DataIndex);

	}

	@Override
	public void update(float dt) {
		stateTime += dt;

		if (stateTime > currentSD.sp) {
			stateTime = 0;
			if (b2DataIndex < screen.getLevelData().b2sArray.size) {
				b2Item = Pools.get(B2.class).obtain();
				b2Item.init(currentSD);
				screen.actives().entities1().add(b2Item);
				screen.getActiveManager().getB2Source().spawn();
			
				b2DataIndex++;
				if (b2DataIndex < screen.getLevelData().b2sArray.size) {
					currentSD = screen.getLevelData().b2sArray
							.get(b2DataIndex);
				}

			} else {
			

			}

		}

	}
}
