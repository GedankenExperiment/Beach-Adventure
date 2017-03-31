package es.adventure.beach.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import es.adventure.beach.main.Assets;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.singleton.ScreenEnum;
import es.adventure.beach.singleton.UIFactory;

public class GameOverScreen extends AbstractScreen {
private Texture overT;
	private TextButton _retry, _goToMenu;

	private Skin skin;

	private int wise_score = 0;

	private int currentLevel;
	// Labels con texturas
	private Label wiseTL;
	
	
	// Labels con variables
	private Label wiseVL;
	
	private Image backgroundImage;
	public GameOverScreen(int wise_score, int currentLevel) {
		super();

		
		this.wise_score = wise_score;
		this.currentLevel = currentLevel;
		
	

	}

	// public GameOverScreen(final BeachAdventure game,float wise_score, float
	// bad_score) {
	//
	//
	// //
	// }

	@Override
	public void buildStage() {
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		overT=new Texture("OVER.png");
		backgroundImage = new Image(overT);
		backgroundImage.setSize(BeachAdventure.V_WIDTH,BeachAdventure.V_HEIGHT);
		backgroundImage.setPosition(0f, 0f);
		_retry = new TextButton("retry", skin);
		_goToMenu = new TextButton("Menu", skin);
		final int anchoBoton = 300;
		final int altoBoton = 100;
		final int espacio = 21;

		final Table table = new Table();
		final Table bTable = new Table();
		table.setFillParent(true);
		bTable.setFillParent(true);
		// "siguiente fila
		wiseTL = new Label("wise",skin);
		wiseTL.setFontScaleX(BeachAdventure.LABELSCALE);
		wiseTL.setFontScaleY(BeachAdventure.LABELSCALE);
		wiseVL = new Label(Integer.toString(wise_score),
				skin);
		wiseVL.setFontScaleX(BeachAdventure.LABELSCALE);
		wiseVL.setFontScaleY(BeachAdventure.LABELSCALE);

		_retry.addListener(UIFactory.createListener(ScreenEnum.GAME,
				currentLevel));
		_goToMenu.addListener(UIFactory.createListener(ScreenEnum.MAIN_MENU));

		table.top();
		table.add(wiseTL).space(espacio).padTop(20);

		table.row();
		table.add(wiseVL).space(espacio).padTop(8);

		table.row();

		bTable.center();
		bTable.add(_retry).space(10f).padTop(20).width(anchoBoton)
				.height(altoBoton);
		bTable.add(_goToMenu).space(10f).padTop(20).width(anchoBoton)
				.height(altoBoton);
		// table.debug();
		addActor(table);
		addActor(bTable);

		Gdx.input.setInputProcessor(this);

	}

	// @Override
	// public void render(float delta) {
	// Gdx.gl.glClearColor(0.7f, 0.7f, 0.9f, 1);
	// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	// // draw();
	// // act(delta);
	// // Assets.manager.get(Assets.breakblock).play(
	// // 0.8f * screen.getGame().getGameVolume());
	//
	// }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		super.dispose();
		skin.dispose();
		overT.dispose();
	}



}