package es.adventure.beach.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.ScreenUtils;

import es.adventure.beach.entity.Drone;
import es.adventure.beach.entity.Entity;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.serialization.LevelData;
import es.adventure.beach.singleton.ScreenEnum;
import es.adventure.beach.singleton.ScreenManager;
import es.adventure.beach.sprites.Drone1;
import es.adventure.beach.sprites.Player1;
import es.adventure.beach.tiledobjects.Brick;
import es.adventure.beach.weapons.Arsenal;
import es.adventure.beach.weapons.Escopeta;
import es.adventure.beach.weapons.Laser;
import es.adventure.beach.weapons.Mp5;
import es.adventure.beach.weapons.Pistola;
import es.adventure.beach.weapons.Rocket;
import es.adventure.beach.weapons.RoundBullet;
import es.adventure.beach.world.ActiveManager;
import es.adventure.beach.world.B2WorldCreator;
import es.adventure.beach.world.PoolsManager;
import es.adventure.beach.world.WorldContactListener;

public class GameScreen extends AbstractScreen {
	// TEXTURE ATLAS FOR EXPLOSIONS BY NOW
	private TextureAtlas explosion;
	// SKIN
	private Skin skin;
	// MUSIC
	private boolean allowedVolume;
	private float currentMusicVolume;
	private float currentSoundVolume;
	private Music music = Gdx.audio.newMusic(Gdx.files
			.internal("audio/music/mario_music.ogg"));
	// START, BACK and go GAMEOVER BUTTONs HUD
	private Texture startButtonT, backButtonT;
	private TextureRegion startButtonR, backButtonR;
	private TextureRegionDrawable startDrawable, menuBackDrawable;
	private Image startTrigger, menuBackTrigger;
	private Image invisibleOverTrigger;
	// TOUCHPAD
	private Texture touchpadT, knobT;
	private TextureRegionDrawable trdPadBack;
	private TextureRegionDrawable trdPadBKnob;
	private TouchpadStyle hudStyle;
	private Touchpad touchpad;

	// SOUND on/off
	private Texture altavozT, altavozOffT;
	private TextureRegion altavozR, altavozOffR;
	private TextureRegionDrawable soundOn, soundOff;
	private Image soundOnOff;

	// ARSENALS AND ITS ACESSING VARIABLES
	private int currentHudWeapon;// 0:pistola,1:escopeta,2:mp5,3:rocket
	private boolean arsenalVisible;
	private Arsenal pistolArsenal;
	private Arsenal escopetaArsenal;
	private Arsenal mp5Arsenal;
	private Arsenal rocketArsenal;
	private Arsenal laserArsenal;

	private static final int pistolCode = 0, escopetaCode = 1, mp5Code = 2,
			rocketCode = 3, laserCode = 4;
	private Texture arsenalsT;
	private Image arsenalI;
	private InputListener weaponListener;
	// CHARACTER SELECTED
	private boolean whoFires;// TRUE: drone, FALSE: player
	private Texture selectDroneT, selectPlayerT;
	private TextureRegion selectDroneR, selectPlayerR;
	private TextureRegionDrawable selectDroneD, selectPlayerD;
	private Image selectImage;
	private DragListener fireSwitcherListener;
	// DIANA
	private Texture dianaT;
	private TextureRegion dianaR;
	private Image dianaImage;

	// COUNTDOWN
	private float timeCount;
	private Label countdownLabel;
	private Label worlTimeL;// lleva "Time"
	private Table topTable;
	// FPS and  INFO 
	private Integer fps;
	private Label fpsLabel;

	private Label multiLabel;
	// ACTORS and MATHS for Listener
	private Vector2 stageInputVector;
	
	private Vector2 screenVector;
	private Actor acTouched;
	float dragY;

	String message;
	// WORLD
	private static final float timeStep = 1 / 60f;
	private static final Vector2 GRAVITY = new Vector2(0, -10f);
	private B2WorldCreator creator;
	private World world;
	private Box2DDebugRenderer b2dr;

	// ENTITY FACTORY
	private int level;
	private static final int pooledW0 = 10;// Pistols PoolInit
	private static final int pooledW1 = 10;// Escopeta PoolInit
	private static final int pooledW2 = 16;// Mp5 PoolInit
	private static final int pooledW3 = 16;// Rocket PoolInit
	private static final int pooledW4 = 40;// RoundBullet PoolInit
	private LevelData levelData;// takes level to R/W JSON FILES
	private Array<Entity> entityPoolingArray = new Array<Entity>();
	private PoolsManager poolsManager;// ENTITY PROVIDER
	private ActiveManager activeManager;// SOURCES AND ENTITY TICKER()

	// TILED
	private TmxMapLoader maploader;
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;

	// CAMERA
	private OrthographicCamera gamecam;

	// GAME LOGIC
	public enum State {
		PAUSE, RUN, RESUME, STOPPED
	}

	private State state = State.PAUSE;

	// PLAYER AND DRONE
	private ArsenalVida vidaArsenal;
	private Player1 player;
	private Drone1 drone1;
	private boolean fir;// This avoid render() states to be switched when
						// hitting back button

	// tiene un metodo buildStage() que carga texturas actores y todo
	// metodo dipose() que es llamado cuando switching de Screen interface

	// Raycasting

	public GameScreen(Integer level) {
		super();
		this.level = level;
		levelData = new LevelData(level);
	}

	/*
	 * END OF CONSTRUCTOR BEGIN OF BUILDSTAGE
	 */
	@Override
	public void buildStage() {
		long time1=System.currentTimeMillis();
		// TEXTUREATLAS FOR EXPLOSIONS BY NOW
		this.explosion = new TextureAtlas("Bullet.pack");

		// SKIN
		this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		// SOUND ON/OFF
		altavozT = new Texture("altavoz.png");
		altavozOffT = new Texture("altavozOff.png");
		altavozR = new TextureRegion(altavozT);
		altavozOffR = new TextureRegion(altavozOffT);
		soundOn = new TextureRegionDrawable(altavozR);
		soundOff = new TextureRegionDrawable(altavozOffR);
		soundOnOff = new Image();

		// MUSIC
		this.currentMusicVolume = ScreenManager.getInstance().getGame()
				.getLeaderboard().getVolumeMusic();
		this.currentSoundVolume = ScreenManager.getInstance().getGame()
				.getLeaderboard().getVolumeSound();
		this.allowedVolume = ScreenManager.getInstance().getGame()
				.getLeaderboard().readAllowedVolume();
		if (allowedVolume) {

			this.music.setVolume(currentMusicVolume);
			this.soundOnOff.setDrawable(soundOn);
		} else {
			this.music.setVolume(0);

			this.soundOnOff.setDrawable(soundOff);
		}
		this.music.play();
		this.music.setLooping(true);

		// START, BACK and GAMEOVER BUTTONs
		this.startButtonT = new Texture("START.png");
		this.backButtonT = new Texture("MENUBACK.png");
		this.startButtonR = new TextureRegion(startButtonT);
		this.backButtonR = new TextureRegion(backButtonT);
		this.startDrawable = new TextureRegionDrawable(startButtonR);
		this.menuBackDrawable = new TextureRegionDrawable(backButtonR);

		// TOUCHPAD
		this.touchpadT = new Texture("TOUCHPADDRONE.png");

		trdPadBack = new TextureRegionDrawable(new TextureRegion(touchpadT));
		this.knobT = new Texture("DRONEKNOB.png");
		trdPadBKnob = new TextureRegionDrawable(new TextureRegion(knobT));
		this.hudStyle = new TouchpadStyle(trdPadBack, trdPadBKnob);

		this.touchpad = new Touchpad(5f, hudStyle);

		touchpad.setOrigin(touchpad.getWidth() / 2, touchpad.getHeight() / 2);
		touchpad.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				fir = false;
				if (isWhoFires()) {

					getDrone1().setJetting(
							true,
							150f * Math.sqrt(Math.pow(
									touchpad.getKnobPercentX(), 2)
									+ Math.pow(touchpad.getKnobPercentY(), 2)),
							Math.atan2(touchpad.getKnobPercentY(),
									touchpad.getKnobPercentX()));//

				} else {
					player.selectWeapon(currentHudWeapon);
					if (touchpad.getKnobPercentX() < -0.01) {
						getPlayer().moveLeft();

					} else if (touchpad.getKnobPercentX() > 0.01) {

						getPlayer().moveRight();

					}

				}
				return true;
			}

			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				fir = false;
				if (isWhoFires()) {

					getDrone1().setJetting(
							true,
							150f * Math.sqrt(Math.pow(
									touchpad.getKnobPercentX(), 2)
									+ Math.pow(touchpad.getKnobPercentY(), 2)),
							Math.atan2(touchpad.getKnobPercentY(),
									touchpad.getKnobPercentX()));//

				} else {

					if (touchpad.getKnobPercentX() < -0.01) {
						getPlayer().moveLeft();

					} else if (touchpad.getKnobPercentX() > 0.01) {

						getPlayer().moveRight();

					}

				}
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				fir = false;
				getDrone1().setJetting(false);
				getPlayer().stopMove();

			}
		});
		touchpad.setName("DronePad");

		this.dianaT = new Texture("DIANA.png");
		this.dianaR = new TextureRegion(dianaT);
		this.dianaImage = new Image(dianaR);

		// NON DISPOSABLE
		message = "hola";

		

		maploader = new TmxMapLoader();
		map = maploader.load("tiled_files/level" + Integer.toString(level)
				+ ".tmx");

		renderer = new OrthogonalTiledMapRenderer(map, 1f / BeachAdventure.PPM);

		dragY = 0f;
		acTouched = null;
		// Tabla de nivel

		timeCount = 0;

		fps = 0;

		world = new World(GRAVITY, true);// gravity,
		state = State.PAUSE;
		fir = false;
		whoFires = false;
		dragY = 0f;

		currentHudWeapon = 0;
		// DRAWABLES LOADING

		instancias();
		sizes();
		startTrigger.setVisible(true);// screen transition
		menuBackTrigger.setVisible(false);// screen transition

		invisibleOverTrigger.setVisible(false);// screen transition

		locations();

		listeners();

		origins();
		// Dragg Listeners
		arsenalI.addListener(weaponListener);
		selectImage.addListener(fireSwitcherListener);
		// this.addListener(new DragListener() {
		// @Override
		// public void dragStart(InputEvent event, float x, float y,
		// int pointer) {
		// }
		// @Override
		// public void dragStop(InputEvent event, float x, float y,
		// int pointer) {
		// }
		// });
		this.addListener(new InputListener() {

			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (state == State.RUN) {
					screenVector.x = Gdx.input.getX();
					screenVector.y = Gdx.input.getY();
					stageInputVector = screenToStageCoordinates(screenVector);

					if (stageInputVector.y > 3 * 64
							&& stageInputVector.y < BeachAdventure.V_HEIGHT) {
						acTouched = hit(stageInputVector.x, stageInputVector.y,
								true);

						if (acTouched == null) {
							dianaImage.setPosition(stageInputVector.x
									- dianaImage.getWidth() / 2,
									stageInputVector.y - dianaImage.getHeight()
											/ 2);

							if (whoFires) {

								drone1.fire(stageInputVector, currentHudWeapon);

							} else {
								player.fire(stageInputVector, currentHudWeapon);

							}
						}
					}
				}
				return true;
			}

			// Vector3 tmp = new Vector3();

			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				if (state == State.RUN) {
					screenVector.x = Gdx.input.getX();
					screenVector.y = Gdx.input.getY();
					stageInputVector = screenToStageCoordinates(screenVector);

					// //////////////////////
					// tmp.set(screenVector.x, screenVector.y, 0);
					// gamecam.unproject(tmp);

					if (stageInputVector.y > 3 * 64
							&& stageInputVector.y < BeachAdventure.V_HEIGHT) {
						acTouched = hit(stageInputVector.x, stageInputVector.y,
								true);

						if (acTouched == null) {
							dianaImage.setPosition(stageInputVector.x
									- dianaImage.getWidth() / 2,
									stageInputVector.y - dianaImage.getHeight()
											/ 2);

							if (whoFires) {

								drone1.fire(stageInputVector, currentHudWeapon);

							} else {
								player.fire(stageInputVector, currentHudWeapon);

							}
						}
					}
				}
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				drone1.setFiring(false);
				drone1.setLaserTrigger(false);

				player.setFiring(false);
				player.setLaserTrigger(false);

			}
			public boolean keyDown (InputEvent event, int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {

					if (state == State.PAUSE && levelData.worldTimer > 1) {
						state = State.RUN;
						fir = true;
						startTrigger.setVisible(false);
						menuBackTrigger.setVisible(false);

					} else if (state == State.RUN) {
						state = State.PAUSE;
						fir = false;
						menuBackTrigger.setVisible(true);

					}
				}
				if (keycode == Keys.P) {
					byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0,
							Gdx.graphics.getBackBufferWidth(),
							Gdx.graphics.getBackBufferHeight(), true);
					Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(),
							Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
					BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
					PixmapIO.writePNG(Gdx.files.external("mypixmap.png"), pixmap);
					pixmap.dispose();

				}
				return false;
			}
			

		});

		b2dr = new Box2DDebugRenderer();
		activeManager = new ActiveManager(this);
		creator = new B2WorldCreator(this);

		Gdx.input.setInputProcessor(this);
		// Gdx.input.setCatchBackKey(true);
		// create mario in our game world
		player = new Player1(this, new Vector2(levelData.initPlayer1X,
				levelData.initPlayer1Y));
		vidaArsenal = new ArsenalVida("ARTIFACT0.png", levelData.initialVida,
				skin);
		pistolArsenal = new Arsenal(this, pistolCode, 8,
				levelData.initialPistol8, skin);
		escopetaArsenal = new Arsenal(this, escopetaCode, 5,
				levelData.initialEscopeta5, skin);
		mp5Arsenal = new Arsenal(this, mp5Code, 16, levelData.initialMP516,
				skin);
		rocketArsenal = new Arsenal(this, rocketCode, 1,
				levelData.initialRocket1, skin);
		laserArsenal = new Arsenal(this, laserCode, 1, levelData.initialLaser1,
				skin);
		pistolArsenal.setVisible(false);
		escopetaArsenal.setVisible(false);
		mp5Arsenal.setVisible(false);
		rocketArsenal.setVisible(false);
		laserArsenal.setVisible(false);
		

		gamecam = new OrthographicCamera();
		gamecam.setToOrtho(false, BeachAdventure.VW_WIDTH,
				BeachAdventure.VW_HEIGHT);
		// gamecam.position.x = BeachAdventure.camX;
		// gamecam.position.y = BeachAdventure.camY;
		WorldContactListener wcl = new WorldContactListener(this);

		world.setContactListener(wcl);
		poolsManager = new PoolsManager(this);
		// Intento populate las pools
		
		populatePools();

		vidaArsenal.setPosition(0, BeachAdventure.V_HEIGHT - 64);
		Vector2 stageCenter = new Vector2(BeachAdventure.V_WIDTH / 2 + 200,
				BeachAdventure.V_HEIGHT / 2);

		float m = 150;
		int i = 0;
		double eo = 1.57;
		int numberOfWeapons = 5;
		double w = 2 * 3.1415 / numberOfWeapons;
		pistolArsenal.setPosition(
				stageCenter.x + m * (float) Math.cos(eo + i * w)
						- pistolArsenal.getWidth() / 2,
				stageCenter.y + m * (float) Math.sin(eo + i * w)
						- pistolArsenal.getHeight() / 2);
		i++;
		escopetaArsenal.setPosition(
				stageCenter.x + m * (float) Math.cos(eo + i * w)
						- pistolArsenal.getWidth() / 2,
				stageCenter.y + m * (float) Math.sin(eo + i * w)
						- pistolArsenal.getHeight() / 2);
		i++;
		mp5Arsenal.setPosition(stageCenter.x + m * (float) Math.cos(eo + i * w)
				- pistolArsenal.getWidth() / 2, stageCenter.y + m
				* (float) Math.sin(eo + i * w) - pistolArsenal.getHeight() / 2);
		i++;
		rocketArsenal.setPosition(
				stageCenter.x + m * (float) Math.cos(eo + i * w)
						- pistolArsenal.getWidth() / 2,
				stageCenter.y + m * (float) Math.sin(eo + i * w)
						- pistolArsenal.getHeight() / 2);
		i++;
		laserArsenal.setPosition(
				stageCenter.x + m * (float) Math.cos(eo + i * w)
						- pistolArsenal.getWidth() / 2,
				stageCenter.y + m * (float) Math.sin(eo + i * w)
						- pistolArsenal.getHeight() / 2);

		touchpad.setPosition(0, 0);
		touchpad.setSize(310, 310);
		addActor(touchpad);
		addActor(vidaArsenal);
		addActor(pistolArsenal);
		addActor(escopetaArsenal);
		addActor(mp5Arsenal);
		addActor(rocketArsenal);
		addActor(laserArsenal);
		addActor(fpsLabel);
		addActor(multiLabel);
		
		addActor(topTable);
		addActor(invisibleOverTrigger);
		addActor(menuBackTrigger);

		addActor(startTrigger);
		addActor(dianaImage);
		addActor(arsenalI);

		addActor(soundOnOff);
		addActor(selectImage);
		long timeT=System.currentTimeMillis()-time1;
		Long.toString(timeT);
		Gdx.app.log("Time Loading GameScreen ", Long.toString(timeT)+" ms");
multiLabel.setText(Long.toString(timeT));
drone1 = new Drone1(this, new Vector2(levelData.initDrone1X,
		levelData.initDrone1Y));
	}

	/*
	 * END OF BUILDSTAGE BEGIN OF UPDATE
	 */
	public void update(float dt) {
		// handle user input first
		timeCount += dt;
		fps = Gdx.graphics.getFramesPerSecond();
		fpsLabel.setText(Integer.toString(fps));

		while (timeCount >= 1f) {
			levelData.worldTimer--;
			countdownLabel.setText(String.format("%03d", levelData.worldTimer));
			timeCount = 0;
		}
		if (levelData.worldTimer < 50) {

			if (levelData.worldTimer < 47) {

				message = "";

			} else {
				message = "Hazlo Bien";
			}
		}
		if (levelData.worldTimer < 1) {

			invisibleOverTrigger.setVisible(true);

			state = State.PAUSE;

		}
		if (!vidaArsenal.available()) {
			ScreenManager.getInstance().showScreen(ScreenEnum.OVER);
		}
		arsenalVisible = ScreenManager.getInstance().getGame().getLeaderboard()
				.isArsenalVisible();
		pistolArsenal.setVisible(arsenalVisible);
		escopetaArsenal.setVisible(arsenalVisible);
		mp5Arsenal.setVisible(arsenalVisible);
		rocketArsenal.setVisible(arsenalVisible);
		laserArsenal.setVisible(arsenalVisible);
		// hud.update(dt);
		// takes 1 step in the physics simulation(60 times per second)
		world.step(timeStep, 6, 2);// 1/60,6,2
		// pe.update(dt);

		player.update(dt);

		drone1.update(dt);
		activeManager.update(dt);

		if (whoFires) {
			gamecam.position.x = drone1.getB2body().getPosition().x;
			if (drone1.getB2body().getPosition().y < BeachAdventure.camY) {
				gamecam.position.y = BeachAdventure.camY;
			} else {
				gamecam.position.y = drone1.getB2body().getPosition().y;
			}
			gamecam.position.x = drone1.getB2body().getPosition().x;
		} else {
			gamecam.position.x = player.getB2body().getPosition().x;
			gamecam.position.y = BeachAdventure.camY;

		}

		gamecam.update();

		renderer.setView(gamecam);// solo renderiza lo q ve la camara

	}

	/*
	 * END OF UPDATE BEGIN OF RENDER
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		switch (state) {
		case RUN:

			update(delta);
			renderer.render();

			// b2dr.render(world, gamecam.combined);// esto renderiza las
			// cajitas

			ScreenManager.getInstance().getBatch()
					.setProjectionMatrix(gamecam.combined);

			ScreenManager.getInstance().getBatch().begin();
			activeManager.render(delta);

			player.draw(ScreenManager.getInstance().getBatch());// here goes
			player.currentWeapon.draw(ScreenManager.getInstance().getBatch());

			drone1.draw(ScreenManager.getInstance().getBatch());// here goes
			if (player.isLaserTrigger()) {
				player.rayo(stageInputVector, Color.RED);
			}
			if (drone1.isLaserTrigger()) {
				drone1.rayo(stageInputVector, Color.GREEN);
			}

			ScreenManager.getInstance().getBatch().end();

			// ///////////

			draw();

			break;
		case PAUSE:

			// shapeRenderer.end();
			// shapeRenderer.begin(ShapeType.Filled);
			// shapeRenderer.rect(0, 0, BeachAdventure.V_WIDTH,
			// BeachAdventure.V_HEIGHT, Color.CYAN, Color.CYAN, Color.RED,
			// Color.RED);
			// shapeRenderer.end();

			draw();
			break;
		case RESUME:

			break;

		default:
			break;
		}

	}

	/*
	 * END OF RENDER BEGIN OF KEY DOWN
	 */
	
	/*
	 * 
	 */
	private void instancias() {
		arsenalVisible = false;
		// START, BACK and go GAMEOVER BUTTONs HUD
		menuBackTrigger = new Image(menuBackDrawable);
		startTrigger = new Image(startDrawable);
		invisibleOverTrigger = new Image(menuBackDrawable);
		// WHO IS FIRING HUD
		selectDroneT = new Texture("DRONE_NAME.png");
		selectPlayerT = new Texture("PLAYER_NAME.png");
		selectDroneR = new TextureRegion(selectDroneT);
		selectPlayerR = new TextureRegion(selectPlayerT);
		selectDroneD = new TextureRegionDrawable(selectDroneR);
		selectPlayerD = new TextureRegionDrawable(selectPlayerR);
		selectImage = new Image(selectPlayerD);

		// WEAPONS HUD
		arsenalsT = new Texture("WEAPONS_SWITCH.png");
		arsenalI = new Image(arsenalsT);

		// // PUNTUACION TIEMPO VIDA FPS
		worlTimeL = new Label("Time", skin);
		worlTimeL.setFontScaleX(BeachAdventure.LABELSCALE);
		worlTimeL.setFontScaleY(BeachAdventure.LABELSCALE);

		countdownLabel = new Label(String.format("%03d", levelData.worldTimer),
				skin);
		countdownLabel.setFontScaleX(BeachAdventure.LABELSCALE);
		countdownLabel.setFontScaleY(BeachAdventure.LABELSCALE);

		fpsLabel = new Label(String.format("%03d", fps), skin);
		fpsLabel.setFontScaleX(BeachAdventure.LABELSCALE);
		fpsLabel.setFontScaleY(BeachAdventure.LABELSCALE);
		multiLabel= new Label(String.format("%03d", 0), skin);
		multiLabel.setFontScaleX(BeachAdventure.LABELSCALE);
		multiLabel.setFontScaleY(BeachAdventure.LABELSCALE);
		topTable = new Table();

		// SOUND HUD

	}

	public void locations() {
		startTrigger.setPosition(
				.5f * BeachAdventure.V_WIDTH - startTrigger.getWidth() / 2, .5f
						* BeachAdventure.V_HEIGHT - startTrigger.getHeight()
						/ 2);
		invisibleOverTrigger.setPosition(.5f * BeachAdventure.V_WIDTH
				- invisibleOverTrigger.getWidth() / 2, .5f
				* BeachAdventure.V_HEIGHT - invisibleOverTrigger.getHeight()
				/ 2);

		menuBackTrigger.setBounds(.5f * BeachAdventure.V_WIDTH - 170,
				.5f * BeachAdventure.V_HEIGHT - 65 / 2, 340, 130);
		startTrigger.setBounds(.5f * BeachAdventure.V_WIDTH - 170,
				.5f * BeachAdventure.V_HEIGHT - 65 / 2, 340, 130);
		topTable.add(worlTimeL);

		topTable.row();

		topTable.add(countdownLabel);

		topTable.setTouchable(Touchable.disabled);
		topTable.top();
		topTable.setFillParent(true);
		soundOnOff.setPosition(21 * 64 - soundOnOff.getWidth(),
				13 * 64 - soundOnOff.getHeight());

		screenVector = new Vector2(0, 0);
		dianaImage.setPosition(BeachAdventure.camX * BeachAdventure.PPM
				- dianaImage.getWidth() * 2, BeachAdventure.camY
				* BeachAdventure.PPM - dianaImage.getHeight() / 2);
		dianaImage.setTouchable(Touchable.disabled);
		arsenalI.setPosition(256 + 64, 0);
		selectImage.setPosition(512, 0);

		fpsLabel.setPosition(BeachAdventure.V_WIDTH * .90f,
				BeachAdventure.V_HEIGHT * .05f);
		multiLabel.setPosition(BeachAdventure.V_WIDTH * .80f,
				BeachAdventure.V_HEIGHT * .05f);
	}

	private void listeners() {
		startTrigger.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				startTrigger.setVisible(false);
				state = State.RUN;
				fir = true;
				return true;
			}

		});

		invisibleOverTrigger.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				int stars = activeManager.processScores();
				Gdx.app.log(Integer.toString(stars), "stars");
				ScreenManager.getInstance().getGame().getLeaderboard()
						.writeWise(stars, level);
				// getCounter().calculateScores();
				ScreenManager.getInstance().showScreen(ScreenEnum.OVER, stars,
						level);
				ScreenManager.getInstance().getGame().getLeaderboard()
						.promoteLevel(stars, level);
				return true;
			}
		});
		menuBackTrigger.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
				return true;
			}
		});

		soundOnOff.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				if (allowedVolume) {
					soundOnOff.setDrawable(soundOff);
					music.setVolume(0);
					allowedVolume = false;
					ScreenManager.getInstance().getGame().getLeaderboard()
							.writeAllowedVolume(false);
				} else {
					soundOnOff.setDrawable(soundOn);
					music.setVolume(currentMusicVolume);
					allowedVolume = true;
					ScreenManager.getInstance().getGame().getLeaderboard()
							.writeAllowedVolume(true);
				}

				return true;
			}

		});
		// ///WEAPON LISTENER PARA MOVIL MULTITOUCH?
		// weaponListener = new InputListener() {
		//
		// public boolean touchDown(InputEvent event, float x, float y,
		// int pointer, int button) {
		//
		// pistolArsenal.setVisible(true);
		// escopetaArsenal.setVisible(true);
		// mp5Arsenal.setVisible(true);
		// rocketArsenal.setVisible(true);
		// laserArsenal.setVisible(true);
		//
		//
		// return true;
		// }
		// @Override
		// public void touchUp(InputEvent event, float x, float y,
		// int pointer, int button) {
		// pistolArsenal.setVisible(false);
		// escopetaArsenal.setVisible(false);
		// mp5Arsenal.setVisible(false);
		// rocketArsenal.setVisible(false);
		// laserArsenal.setVisible(false);
		//
		// }
		// };
		weaponListener = ScreenManager.getInstance().getGame().getLeaderboard()
				.weaponSelectionListener();
		// weaponListener = new InputListener() {
		//
		// public boolean touchDown(InputEvent event, float x, float y,
		// int pointer, int button) {
		// if (arsenalsVisible) {
		// arsenalsVisible = false;
		// pistolArsenal.setVisible(false);
		// escopetaArsenal.setVisible(false);
		// mp5Arsenal.setVisible(false);
		// rocketArsenal.setVisible(false);
		// laserArsenal.setVisible(false);
		// } else {
		// arsenalsVisible = true;
		// pistolArsenal.setVisible(true);
		// escopetaArsenal.setVisible(true);
		// mp5Arsenal.setVisible(true);
		// rocketArsenal.setVisible(true);
		// laserArsenal.setVisible(true);
		// }
		//
		// return true;
		// }
		//
		// };

		fireSwitcherListener = new DragListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				whoFires = !whoFires;
				if (whoFires)
					selectImage.setDrawable(selectDroneD);
				else
					selectImage.setDrawable(selectPlayerD);
				return true;
			}

		};

	}

	public float getCurrentMusicVolume() {
		return currentMusicVolume;
	}

	public void sizes() {

		dianaImage.setSize(64f, 64f);
		soundOnOff.setSize(48f, 48f);

		// fireSwitcherImage.setSize(128f, 128f);
		// currentWeaponImagen.setSize(128, 128);
	}

	public void origins() {
		// dianaImage.setOrigin(16, 16);
		// fireSwitcherImage.setOrigin(64f, 32f);
		// faseSelectorI.setOrigin(64f, 64f);

	}

	@Override
	public void dispose() {
		super.dispose();
		Gdx.app.log("GAME SCREEN DISPOSED", "");
		activeManager.dispose();
		selectDroneT.dispose();
		selectPlayerT.dispose();
		touchpadT.dispose();
		knobT.dispose();
		player.dispose();
		startButtonT.dispose();
		backButtonT.dispose();
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
		arsenalsT.dispose();

	}

	public ActiveManager getActiveManager() {
		return activeManager;
	}

	public B2WorldCreator getCreator() {
		return creator;
	}

	public World getWorld() {
		return world;
	}

	private void populatePools() {
		populatePistols();
		populateEscopetas();
		populateMp5s();
		populateRockets();
		populateRoundBullets();

	}

	public void populatePistols() {
		for (int i = 0; i < pooledW0; i++) {

			Pistola pistolItem = Pools.get(Pistola.class).obtain();
			pistolItem.init(0, 0, 0, false);
			entityPoolingArray.add(pistolItem);

		}

		for (int i = 0; i < pooledW0; i++) {

			getWorld().destroyBody(entityPoolingArray.get(i).getB2body());
			Pools.get(Pistola.class).free((Pistola) entityPoolingArray.get(i));

		}

		entityPoolingArray.clear();
	}

	public void populateEscopetas() {
		for (int i = 0; i < pooledW1; i++) {

			Escopeta escopetaItem = Pools.get(Escopeta.class).obtain();
			escopetaItem.init(0, 0, 0, false);
			entityPoolingArray.add(escopetaItem);

		}

		for (int i = 0; i < pooledW1; i++) {
			getWorld().destroyBody(entityPoolingArray.get(i).getB2body());
			Pools.get(Escopeta.class)
					.free((Escopeta) entityPoolingArray.get(i));

		}
		entityPoolingArray.clear();
	}

	public void populateMp5s() {
		for (int i = 0; i < pooledW2; i++) {

			Mp5 mp5Item = Pools.get(Mp5.class).obtain();
			mp5Item.init(0, 0, 0, false);
			entityPoolingArray.add(mp5Item);

		}

		for (int i = 0; i < pooledW2; i++) {
			getWorld().destroyBody(entityPoolingArray.get(i).getB2body());
			Pools.get(Mp5.class).free((Mp5) entityPoolingArray.get(i));

		}
		entityPoolingArray.clear();
	}

	public void populateRockets() {
		for (int i = 0; i < pooledW3; i++) {

			Rocket rocketItem = Pools.get(Rocket.class).obtain();
			rocketItem.init(0, 0, 0, false);
			entityPoolingArray.add(rocketItem);

		}

		for (int i = 0; i < pooledW3; i++) {
			getWorld().destroyBody(entityPoolingArray.get(i).getB2body());
			Pools.get(Rocket.class).free((Rocket) entityPoolingArray.get(i));

		}
		entityPoolingArray.clear();
	}

	public void populateRoundBullets() {
		for (int i = 0; i < pooledW4; i++) {

			RoundBullet roundBulletItem = Pools.get(RoundBullet.class).obtain();
			roundBulletItem.init(0, 0, 0);
			entityPoolingArray.add(roundBulletItem);

		}

		for (int i = 0; i < pooledW4; i++) {
			getWorld().destroyBody(entityPoolingArray.get(i).getB2body());
			Pools.get(RoundBullet.class).free(
					(RoundBullet) entityPoolingArray.get(i));

		}
		entityPoolingArray.clear();
	}

	public void setCurrentHudWeapon(int currentHudWeapon) {
		this.currentHudWeapon = currentHudWeapon;
		player.selectWeapon(currentHudWeapon);

	}

	public PoolsManager pools() {
		return poolsManager;
	}

	public ActiveManager actives() {
		return activeManager;
	}

	public Player1 getPlayer() {
		return player;
	}

	public Drone1 getDrone1() {
		return drone1;
	}

	public Vector2 getGRAVITY() {
		return GRAVITY;
	}

	public int getLevel() {
		return level;
	}

	public int getCurrentHudWeapon() {
		return currentHudWeapon;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public OrthographicCamera getGamecam() {
		return gamecam;
	}

	public LevelData getLevelData() {
		return levelData;
	}

	public ArsenalVida getVidaArsenal() {
		return vidaArsenal;
	}

	public Arsenal getPistolArsenal() {
		return pistolArsenal;
	}

	public Arsenal getEscopetaArsenal() {
		return escopetaArsenal;
	}

	public Arsenal getMp5Arsenal() {
		return mp5Arsenal;
	}

	public Arsenal getRocketArsenal() {
		return rocketArsenal;
	}

	public Arsenal getLaserArsenal() {
		return laserArsenal;
	}

	public float getCurrentSoundVolume() {
		return currentSoundVolume;
	}

	public void setCurrentSoundVolume(float currentSoundVolume) {
		this.currentSoundVolume = currentSoundVolume;
	}

	public boolean isWhoFires() {
		return whoFires;
	}

	public boolean isAllowedVolume() {
		return allowedVolume;
	}

	public void setAllowedVolume(boolean allowedVolume) {
		this.allowedVolume = allowedVolume;
	}

	public TextureAtlas getExplosion() {
		return explosion;
	}

	public TiledMap getMap() {
		return map;
	}
	public Vector2 getStageInputVector() {
		return stageInputVector;
	}

}
