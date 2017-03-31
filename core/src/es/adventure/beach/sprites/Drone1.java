package es.adventure.beach.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import es.adventure.beach.main.Assets;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class Drone1 extends AbstPlayer {

	private Texture dataT;
	private TextureRegion dataR;
	private Array<Texture> framesT=new Array<Texture>();
	private Array<TextureRegion> frames=new Array<TextureRegion>();
	private Animation drone1Anim;

	private boolean destroyed;
	private boolean setToDestroy;

	private double modulo;
	private double fase;
	private boolean jetting;
	private Sprite data;
	private boolean dataLoaded;

	public Drone1(GameScreen gameScreen,Vector2 initPos) {
		super(gameScreen,initPos);
		this.screen = gameScreen;
		dataT = new Texture("DATA0.png");
		dataR = new TextureRegion(dataT);
		data = new Sprite(dataR);

		jetting = false;
		destroyed = false;
		setToDestroy = false;
		modulo = 0;
		fase = 0;
		drone1Body();
		dataLoaded = false;
	
		for (int i = 0; i < 3; i++) {
			framesT.add(new Texture("DRONE" + Integer.toString(i + 1)
					+ ".png"));
			frames.add(new TextureRegion(framesT.get(i)));
		}
	

		drone1Anim = new Animation(0.8f, frames);
		frames.clear();
		//

		// playerFiring = new TextureRegion(getTexture(), 256 * 4, 0, 256,
		// 256);//
		
		setBounds(0, 0, 128 / BeachAdventure.PPM, 64 / BeachAdventure.PPM);
		setRegion(drone1Anim.getKeyFrame(stateTimer, true));
		//data.setOrigin(128 / BeachAdventure.PPM, 64 / BeachAdventure.PPM);
		data.setBounds(0, 0, 32 / BeachAdventure.PPM, 32 / BeachAdventure.PPM);
	}

	public void update(float dt) {
		super.stateTimer += dt;
		super.update(dt);
	
		if (destroyed) {
			// setRegion(bull);
		} else if (setToDestroy && !destroyed) {

			screen.getWorld().destroyBody(b2body);
			destroyed = true;
			stateTimer = 0;

		} else if (!destroyed) {
			setRegion(drone1Anim.getKeyFrame(stateTimer, true));

			if (jetting) {

				b2body.setLinearVelocity(
						(float) (2 * modulo * Math.cos(fase) / BeachAdventure.PPM),
						(float) (2 * modulo * Math.sin(fase) / BeachAdventure.PPM));

			} else {
				b2body.setLinearVelocity(0f, 0f);
			}
			data.setPosition(b2body.getPosition().x - data.getWidth() / 2,
					b2body.getPosition().y - data.getHeight() / 2);
			setPosition(b2body.getPosition().x - getWidth() / 2,
					b2body.getPosition().y - getHeight() / 2);

		}

	}

	public void draw(Batch batch) {

		if (!destroyed) {
			super.draw(batch);
			data.draw(batch);
		}
	}

	public void drone1Body() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(super.initPos.x,super.initPos.y);
		bdef.type = BodyDef.BodyType.DynamicBody;
		// /////////

		// //////////
		super.b2body = screen.getWorld().createBody(bdef);
		super.b2body.setGravityScale(0f);
		// super.b2body.setLinearDamping(0f);
		FixtureDef fdef = new FixtureDef();

		// CircleShape shape = new CircleShape();
		// shape.setRadius(10 / BeachAdventure.PPM);
		PolygonShape pshape = new PolygonShape();
		pshape.setAsBox(40f / BeachAdventure.PPM, 16f / BeachAdventure.PPM);
		fdef.density = 220f;// 10 es la densidad kg/m2

		fdef.filter.categoryBits = BeachAdventure.DRONE1_BIT;
		fdef.filter.maskBits = BeachAdventure.GROUND_BIT
				| BeachAdventure.COMPUTER_BIT | BeachAdventure.BRICK_BIT
				| BeachAdventure.NUBE_BIT;

		// fdef.isSensor = true;
		fdef.shape = pshape;
		b2body.setFixedRotation(true);// no rota
		b2body.createFixture(fdef).setUserData(this);
		pshape.dispose();
		EdgeShape head = new EdgeShape();//60/15
		head.set(new Vector2(-100 / BeachAdventure.PPM, 50  / BeachAdventure.PPM),
				new Vector2(100 / BeachAdventure.PPM,       50/ BeachAdventure.PPM));
		fdef.shape = head;
		fdef.isSensor = true;
		b2body.createFixture(fdef).setUserData("head");
	
		EdgeShape left = new EdgeShape();
		left.set(new Vector2(-200 / BeachAdventure.PPM, 50 / BeachAdventure.PPM),
				new Vector2(-200 / BeachAdventure.PPM,-50/ BeachAdventure.PPM));
		fdef.shape = left;
		fdef.isSensor = true;
		b2body.createFixture(fdef).setUserData("left");
		
		EdgeShape bottom = new EdgeShape();
		bottom.set(new Vector2(-200 / BeachAdventure.PPM, -50 / BeachAdventure.PPM),
				new Vector2(200 / BeachAdventure.PPM,-50/ BeachAdventure.PPM));
		fdef.shape = bottom;
		fdef.isSensor = true;
		b2body.createFixture(fdef).setUserData("bottom");
		
		EdgeShape right = new EdgeShape();
		right.set(new Vector2(200 / BeachAdventure.PPM, 50 / BeachAdventure.PPM),
				new Vector2(200 / BeachAdventure.PPM,-50/ BeachAdventure.PPM));
		fdef.shape = right;
		fdef.isSensor = true;
		b2body.createFixture(fdef).setUserData("right");

	}

	public void setJetting(boolean jetting, double modulo, double fase) {
		this.jetting = jetting;
		this.modulo = modulo;
		this.fase = fase;
		this.moving = true;
	}

	public void setJetting(boolean jetting) {
		this.jetting = jetting;
		this.modulo = 0;
		this.fase = 0;
		this.moving = false;
	}

	public boolean checkData() {
		if (dataLoaded)
			return true;
		else
			return false;
	}

	public void loadFromComputer() {

		dataLoaded = true;
		data.setRegion(dataR);

	}

	public void deliver() {

		dataLoaded = false;
		data.setRegion(Assets.manager.get(Assets.bigAtlas).findRegion(
				"DATA" + Integer.toString(0)));
		

	}

	@Override
	public void dispose() {
		for (int i = 0; i < 3; i++) {
			framesT.get(i).dispose(); 
		}
		super.sr.dispose();
		super.dessert.dispose();
		super.empty.dispose();
		super.escopeta.dispose();
		super.mp5.dispose();
		super.reload.dispose();
	

	}

}
