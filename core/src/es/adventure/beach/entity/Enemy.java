package es.adventure.beach.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import es.adventure.beach.scenes.GameScreen;

public abstract class Enemy extends Entity {
	protected float modulo;
	protected double fase;
	public int vida;// Each enemy has its own
	protected  int[] damageList;// Each enemy has its own

	protected String name;

	public Enemy() {
	}

	public String getName() {
		return name;
	}

	public Enemy(GameScreen screen, String name, int initHitPoints,
			int[] damageList) {
		super(screen);
		this.name = name;
		this.vida = initHitPoints;
		this.fase = 0f;

		this.modulo = 0;
		this.setToDestroy = false;
		this.destroyed = false;
	}

	public void receiveDamage(int WEAPON_CODE) {
		this.vida -= damageList[WEAPON_CODE];
		Gdx.app.log("damage taken "+this.name, Integer.toString(damageList[WEAPON_CODE]));
		if (vida < 1) {
			
			Gdx.app.log(this.name, "enemydied");
			setToDestroy = true;
			notifySpawner();// tells spawner it was killed
		}
	}
	public int[] getDamageList() {
		return damageList;
	}
	public abstract void hitByGround();
	// tells its spawner it was killed
	public abstract void notifySpawner();
}
