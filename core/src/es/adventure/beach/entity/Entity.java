package es.adventure.beach.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool.Poolable;

import es.adventure.beach.scenes.GameScreen;

public abstract class Entity extends Sprite implements Poolable,Disposable {
	protected Body b2body;
	protected boolean setToDestroy;
	protected boolean destroyed;

	protected Vector2 velocity;
	public void setB2body(Body b2body) {
		this.b2body = b2body;
	}

	protected GameScreen screen;


	protected float halfW, halfH;

	public Entity() {
	}

	public Entity(GameScreen gameScreen) {
		this.screen = gameScreen;
		
		
		setToDestroy=false;
		destroyed=false;

	}

	public abstract void update(float dt);

	protected abstract void defineBody();
	
	

	public Body getB2body() {
		return b2body;
	}
}