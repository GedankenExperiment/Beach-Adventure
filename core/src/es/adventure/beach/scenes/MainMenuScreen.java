package es.adventure.beach.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import es.adventure.beach.main.Assets;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.singleton.ScreenEnum;
import es.adventure.beach.singleton.ScreenManager;

public class MainMenuScreen extends AbstractScreen {
	private Music music = Gdx.audio.newMusic(Gdx.files
			.internal("audio/music/mario_music.ogg"));
	private Sound sound = Gdx.audio.newSound(Gdx.files
			.internal("audio/sounds/coin.wav"));
	private Texture mainMenuScreen;
	private Texture altavoz;
	private Texture altavozOff;
	private TextureRegion altavozR;
	private TextureRegion altavozOffR;
	public Skin skin;
	private Image backgroundImage;
	private Slider musicSlider;
	private Slider soundSlider;

	private SliderStyle sliderStyle;

	private Table musicTable, soundTable;
	private Label musicLabel;
	private Label soundLabel;
	private TextureRegionDrawable onDraw;
	private TextureRegionDrawable offDraw;
	private TextureRegionDrawable musicSliderBack;
	private TextureRegionDrawable musicKnob;
	private Image musicI, soundI;
	private Image _newgame;
	private Image _highScores;

	private Image _quit;
	private static final int anchoBoton = 289;
	private static final int altoBoton = 111;
	private static final float anchoHalf = anchoBoton / 2;
	private static final float altoHalf = altoBoton / 2;

	private static final Vector2 tMusic = new Vector2(
			BeachAdventure.V_WIDTH / 3, BeachAdventure.V_HEIGHT * 2 / 3);
	private static final Vector2 tSound = new Vector2(
			BeachAdventure.V_WIDTH / 3, BeachAdventure.V_HEIGHT / 3);
	private static final Vector2 tPlay = new Vector2(
			BeachAdventure.V_WIDTH * 2 / 3, BeachAdventure.V_HEIGHT * 2 / 3);
	private static final Vector2 tHighScores = new Vector2(
			BeachAdventure.V_WIDTH * 2 / 3, BeachAdventure.V_HEIGHT / 3);
	private static final Vector2 tQuit = new Vector2(
			BeachAdventure.V_WIDTH / 2, 100f);
	private float musicVolume, soundVolume = 0.00f;

	public MainMenuScreen() {
		super();

	}

	@Override
	public void buildStage() {
		// ///////////MUSIC//////////

		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		mainMenuScreen = new Texture("MAINMENU.png");
		altavoz = new Texture("altavoz.png");
		altavozOff = new Texture("altavozOff.png");

		altavozR = new TextureRegion(altavoz);
		backgroundImage = new Image(mainMenuScreen);
		altavozOffR = new TextureRegion(altavozOff);
		backgroundImage = new Image(mainMenuScreen);
		backgroundImage
				.setSize(BeachAdventure.V_WIDTH, BeachAdventure.V_HEIGHT);
		onDraw = new TextureRegionDrawable(altavozR);
		offDraw = new TextureRegionDrawable(altavozOffR);
		_newgame = new Image();
		_highScores = new Image();
		_quit = new Image();
		musicSliderBack = new TextureRegionDrawable(altavozR);
		musicKnob = new TextureRegionDrawable(altavozR);
		musicSlider = new Slider(0f, 1f, 0.01f, false, skin);
		soundSlider = new Slider(0f, 1f, 0.01f, false, skin);

		sliderStyle = new SliderStyle(musicSliderBack, musicKnob);
		// ////////MUSIC//////////
		musicTable = new Table();

		musicVolume = ScreenManager.getInstance().getGame().getLeaderboard()
				.getVolumeMusic();
		musicLabel = new Label(Integer.toString((int) (musicVolume * 100)),
				skin, "default-font", Color.PINK);
		musicLabel.setFontScale(BeachAdventure.LABELSCALE * 2);
		musicLabel.setText(Integer.toString((int) (musicVolume * 100)));
		musicSlider.setValue(musicVolume);
		musicSlider.setStyle(sliderStyle);
		// musicSlider.setSize(anchoBoton, altoBoton);
		musicI = new Image(onDraw);
		musicTable.setSize(anchoBoton, altoBoton);
		musicTable.addActor(musicSlider);
		musicSlider.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				musicVolume = musicSlider.getValue();
				musicLabel.setText(Integer.toString((int) (musicVolume * 100)));
				ScreenManager.getInstance().getGame().getLeaderboard()
						.writeVolumeMusic(musicVolume);

				// Gdx.app.log("VOLUME:", Float.toString(currentMusicVolume));
				music.setVolume(musicVolume);
				music.play();

				if (musicVolume < 0.01f) {
					musicI.setDrawable(offDraw);
				} else {
					musicI.setDrawable(onDraw);
				}
				// volumePref.putString("Volume",
				// Float.toString(volume));
			}
		});
		// //////////SOUND//////////
		soundTable = new Table();
		soundVolume = ScreenManager.getInstance().getGame().getLeaderboard()
				.getVolumeSound();

		soundLabel = new Label(Integer.toString((int) (soundVolume * 100)),
				skin, "default-font", Color.WHITE);
		soundLabel.setFontScale(BeachAdventure.LABELSCALE * 2);
		soundLabel.setText(Integer.toString((int) (soundVolume * 100)));
		soundSlider.setValue(soundVolume);
		soundSlider.setStyle(sliderStyle);
		soundI = new Image(onDraw);
		soundTable.setSize(anchoBoton, altoBoton);
		soundTable.addActor(soundSlider);
		soundSlider.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				soundVolume = soundSlider.getValue();
				soundLabel.setText(Integer.toString((int) (soundVolume * 100)));
				ScreenManager.getInstance().getGame().getLeaderboard()
						.writeVolumeSound(soundVolume);

				if (soundVolume < 0.01f) {
					soundI.setDrawable(offDraw);
				} else {
					soundI.setDrawable(onDraw);
				}

			}
		});

		soundSlider.addListener(new InputListener() {

			@Override
			public void exit(InputEvent event, float x, float y, int pointer,
					Actor toActor) {
				sound.play(soundVolume);

			}
		});
		_newgame.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ScreenManager.getInstance().showScreen(ScreenEnum.LEVEL_SELECT);
				return false;
			}
		});
		_highScores.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ScreenManager.getInstance().showScreen(ScreenEnum.HIGH_SCORES);
				return false;
			}
		});

		_quit.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
				return false;
			}
		});
		musicSlider.setSize(anchoBoton, altoBoton);
		musicI.setBounds(tMusic.x - anchoHalf - 130, tMusic.y - altoHalf, 130,
				111);
		musicLabel.setBounds(tMusic.x - anchoHalf - 98, tMusic.y - altoHalf,
				111, 111);
		musicTable.setBounds(tMusic.x - anchoHalf, tMusic.y - altoHalf,
				anchoBoton, altoBoton);

		// /////////SOUNDS//////////////////
		soundSlider.setSize(anchoBoton, altoBoton);
		soundI.setBounds(tSound.x - anchoHalf - 130, tSound.y - altoHalf, 130,
				111);
		soundLabel.setBounds(tSound.x - anchoHalf - 98, tSound.y - altoHalf,
				111, 111);
		soundTable.setBounds(tSound.x - anchoHalf, tSound.y - altoHalf,
				anchoBoton, altoBoton);
		// ///////////
		_newgame.setSize(anchoBoton, altoBoton);
		_highScores.setSize(anchoBoton, altoBoton);
		_quit.setSize(anchoBoton, altoBoton);
		soundSlider.setSize(anchoBoton, altoBoton);

		_newgame.setBounds(tPlay.x - anchoHalf, tPlay.y - altoHalf, anchoBoton,
				altoBoton);
		_highScores.setBounds(tHighScores.x - anchoHalf, tHighScores.y
				- altoHalf, anchoBoton, altoBoton);
		_quit.setBounds(tQuit.x - anchoHalf, tQuit.y - altoHalf, anchoBoton,
				altoBoton);

		musicSlider.debug();
		musicTable.debug();
		soundSlider.debug();
		_newgame.debug();
		_highScores.debug();
		_quit.debug();

		addActor(backgroundImage);

		addActor(musicI);
		addActor(musicLabel);
		addActor(musicTable);

		addActor(soundI);
		addActor(soundLabel);
		addActor(soundTable);

		addActor(_newgame);
		addActor(_highScores);
		addActor(_quit);

	}

	@Override
	public void dispose() {
		skin.dispose();
		super.dispose();
		mainMenuScreen.dispose();
		altavoz.dispose();
		altavozOff.dispose();
		music.dispose();
		sound.dispose();

	}

}
