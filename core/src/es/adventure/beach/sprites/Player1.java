package es.adventure.beach.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class Player1 extends AbstPlayer {

	private Texture p0T, p1T, p2T, p3T, p4T;
	private TextureRegion p0R, p1R, p2R, p3R, p4R;
	private Texture emptyT, w0T, w1T, w2T, w3T,w4T;
	private TextureRegion emptyR, w0R, w1R, w2R, w3R,w4R;
	private TextureRegion current;

	public enum State {
		FIRING, JUMPING, STANDING, RUNNING;
	}

	private State currentState;

	public State previousState;
	// private Texture firingT;

	private Array<TextureRegion> framesR = new Array<TextureRegion>();
	private TextureRegion playerFiring;
	private TextureRegion playerStanding;
	private Animation runningAnim;
	private TextureRegion standing;

	private float stateTimer;

	private boolean runningRight;

	public Sprite currentWeapon;

	public Player1(GameScreen gameScreen, Vector2 initPos) {
		// super(Assets.bigAtlas.findRegion("player"));//
		// actions
		super(gameScreen, initPos);

		stateTimer = 0;

		// mp5Timer = 0;
		// firingTimer = 0;
		// currentBeta = 1.57;
		// automaticMp5 = 5;
		// laserTimer = 0;
		// laserTmax = 2f;// laser art last for 2 secs
		// this.currentHudWeapon = 0;// 0 corresponding to baseball

		definePlayer1Body();
		runningRight = false;
		// setOrigin(16 / BeachEscape.PPM, 16 / BeachEscape.PPM);

		currentState = State.STANDING;
		previousState = State.STANDING;
		runningRight = true;

		// TEXTURES AND ANIMATIONS
		p0T = new Texture("PLAYER0.png");
		p1T = new Texture("PLAYER1.png");
		p2T = new Texture("PLAYER2.png");
		p3T = new Texture("PLAYER3.png");
		p4T = new Texture("PLAYER4.png");

		p0R = new TextureRegion(p0T);
		p1R = new TextureRegion(p1T);
		p2R = new TextureRegion(p2T);
		p3R = new TextureRegion(p3T);
		p4R = new TextureRegion(p4T);
		framesR.add(p0R);
		framesR.add(p1R);
		framesR.add(p2R);
		framesR.add(p3R);
		runningAnim = new Animation(0.1f, framesR);
		framesR.clear();
		emptyT = new Texture("PLAYERW-1.png");
		w0T = new Texture("PLAYERW0.png");
		w1T = new Texture("PLAYERW1.png");
		w2T = new Texture("PLAYERW2.png");
		w3T = new Texture("PLAYERW3.png");
		w4T = new Texture("PLAYERW4.png");
		emptyR = new TextureRegion(emptyT);
		w0R = new TextureRegion(w0T);
		w1R = new TextureRegion(w1T);
		w2R = new TextureRegion(w2T);
		w3R = new TextureRegion(w3T);
		w4R = new TextureRegion(w4T);
		standing = p1R;
		playerFiring = p4R;
		playerStanding = standing;
		current = playerStanding;

		currentWeapon = new Sprite(w0R);

		currentWeapon.setBounds(0, 0, 128 / BeachAdventure.PPM,
				128 / BeachAdventure.PPM);
		setRegion(current);
		setBounds(0, 0, 128 / BeachAdventure.PPM, 128 / BeachAdventure.PPM);

	}

	// needs to be triggered when switching weapon in the arsenal class
	public void selectWeapon(int weapon) {
		switch (weapon) {
		case (-1):
			currentWeapon.setRegion(emptyR);
			break;
		case (0):

			currentWeapon.setRegion(w0R);
			break;
		case (1):

			currentWeapon.setRegion(w1R);
			break;
		case (2):

			currentWeapon.setRegion(w2R);
			break;
		case (3):

			currentWeapon.setRegion(w3R);
			break;
		case (4):

			currentWeapon.setRegion(w4R);
			break;
		}
		if (runningRight && currentWeapon.isFlipX()) {
			currentWeapon.flip(true, false);
		} else if (!runningRight && !currentWeapon.isFlipX()) {
			currentWeapon.flip(true, false);
		}
	}

	public void update(float dt) {
		super.stateTimer += dt;
		super.update(dt);

		setPosition(b2body.getPosition().x - getWidth() / 2,
				b2body.getPosition().y - getHeight() / 2);

		setRegion(getFrame(dt));
		this.currentWeapon.setPosition(b2body.getPosition().x - getWidth() / 2,
				b2body.getPosition().y - getHeight() / 2);

		// BeachAdventure.camX=b2body.getPosition().x;
	}

	private TextureRegion getFrame(float dt) {
		currentState = getState();

		switch (currentState) {
		case RUNNING:
			current = runningAnim.getKeyFrame(stateTimer, true);
			break;
		case FIRING:
			if (b2body.getLinearVelocity().x != 0) {
				current = runningAnim.getKeyFrame(stateTimer, true);
			} else {
				selectWeapon(-1);
				current = playerFiring;
			}
			break;
		default:
			selectWeapon(screen.getCurrentHudWeapon());
			current = playerStanding;

			break;
		}
		if ((b2body.getLinearVelocity().x < 0 || !runningRight)
				&& !current.isFlipX()) {
			current.flip(true, false);
			runningRight = false;

		} else if ((b2body.getLinearVelocity().x > 0 || runningRight)
				&& current.isFlipX()) {
			current.flip(true, false);
			runningRight = true;
		}

		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;

		return current;
	}

	private State getState() {
		if (firing) {
			// selectWeapon(-1);
			return State.FIRING;
		} else if (b2body.getLinearVelocity().x != 0) {
			// selectWeapon(screen.getCurrentHudWeapon());
			return State.RUNNING;
		} else {
			// selectWeapon(screen.getCurrentHudWeapon());
			return State.STANDING;
		}
	}

	public void definePlayer1Body() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(super.initPos.x, super.initPos.y);
		bdef.type = BodyDef.BodyType.KinematicBody;
		// /////////

		// //////////
		b2body = screen.getWorld().createBody(bdef);
		// b2body.setLinearDamping(0.9f);
		FixtureDef fdef = new FixtureDef();

		// CircleShape shape = new CircleShape();
		// shape.setRadius(80 / BeachAdventure.PPM);
		PolygonShape pshape = new PolygonShape();
		pshape.setAsBox(38.7f / BeachAdventure.PPM, 58.0f / BeachAdventure.PPM);
		// fdef.density = 220f;// 10 es la densidad kg/m2
		fdef.isSensor = true;
		fdef.filter.categoryBits = BeachAdventure.PLAYER_BIT;
		fdef.filter.maskBits = BeachAdventure.GROUND_BIT
				| BeachAdventure.INTED_ARTIFACT_BIT | BeachAdventure.BOMB_BIT;
		// fdef.filter.maskBits = BeachAdventure.GROUND_BIT |
		// BeachAdventure.ENEMY_BIT
		// | BeachAdventure.TREE_BIT;

		// fdef.isSensor = true;
		fdef.shape = pshape;
		b2body.setFixedRotation(true);// no rota
		b2body.createFixture(fdef).setUserData("player");
		pshape.dispose();
	}

	public void moveLeft() {

		// b2body.applyLinearImpulse(-100f, 0, b2body.getLocalCenter().x,
		// b2body.getLocalCenter().y, true);
		// runningRight = false;
		if (!currentWeapon.isFlipX()) {
			currentWeapon.flip(true, false);
		}

		super.moving = true;
		b2body.setLinearVelocity(-261f / BeachAdventure.PPM, 0);
		setFiring(false);
	}

	public void moveRight() {

		// b2body.applyLinearImpulse(+100f, 0, b2body.getLocalCenter().x,
		// b2body.getLocalCenter().y, true);
		if (currentWeapon.isFlipX()) {
			currentWeapon.flip(true, false);
		}
		super.moving = true;
		b2body.setLinearVelocity(261f / BeachAdventure.PPM, 0);
		setFiring(false);
	}

	public void stopMove() {
		super.moving = false;
		b2body.setLinearVelocity(super.zeroV);

	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public boolean isRunningRight() {
		// TODO Auto-generated method stub
		return runningRight;
	}

	@Override
	public void dispose() {

		p0T.dispose();
		p1T.dispose();
		p2T.dispose();
		p3T.dispose();
		p4T.dispose();
		super.sr.dispose();
		emptyT.dispose();
		w0T.dispose();
		w1T.dispose();
		w2T.dispose();
		w3T.dispose();
		w4T.dispose();
	}

	

}
