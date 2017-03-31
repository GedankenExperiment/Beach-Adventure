package es.adventure.beach.spawners;

import com.badlogic.gdx.Gdx;

import es.adventure.beach.scenes.GameScreen;

public abstract class Spawner {
	protected GameScreen screen;
	protected int killed = 0;
	protected int spawned = 0;
	protected float score = 0.00f;
	protected float weight;

	public Spawner(GameScreen gameScreen, float weight) {
		this.screen = gameScreen;
		this.weight = weight;
	}

	public abstract void update(float dt);

	public void kill() {
		killed += 1;
	}

	public void spawn() {
		spawned += 1;
	}

	public float calculateScore() {
		score = weight * (float) killed / (float) spawned;
		Gdx.app.log(Float.toString(score)+"score,weight", Float.toString(weight));
		return score;
	}

}
