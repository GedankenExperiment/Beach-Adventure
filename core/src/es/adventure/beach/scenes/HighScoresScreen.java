package es.adventure.beach.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import es.adventure.beach.gameutil.StarsImage;
import es.adventure.beach.main.Assets;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.singleton.ScreenEnum;
import es.adventure.beach.singleton.ScreenManager;

public final class HighScoresScreen extends AbstractScreen {
	private final int anchoBoton = 100;
	private final int altoBoton = 61;
	private final int espacio = 26;
	private Texture backT;

	private Texture highScoresScreenT;
	private Image back;
	private Table tableA;

	private Image backgroundImage;
	private int colsperrow;
	private int index;

	private Array<StarsImage> tables;

	public HighScoresScreen() {
		super();

	}

	@Override
	public void buildStage() {
		Gdx.input.setInputProcessor(this);
		
		backT = new Texture("MENUBACK.png");
		highScoresScreenT = new Texture("HIGHSCORES.png");

		back = new Image(backT);
		colsperrow = 0;
		index = 0;
		tables = new Array<StarsImage>();
		backgroundImage = new Image(highScoresScreenT);
		backgroundImage
				.setSize(BeachAdventure.V_WIDTH, BeachAdventure.V_HEIGHT);
		backgroundImage.setPosition(0f, 0f);

		tableA = new Table();
		tableA.setFillParent(true);

		String sentence = "1 2 3 4 5 6 7 8 9 10 11 12";
		String[] temp = sentence.split(" ");
		Array<String> words = new Array<String>(temp);
		tables = new Array<StarsImage>();
		for (int i = 1; i < words.size + 1; i++) {
			// make i final here so you can reference it inside
			// the anonymous class
			index = i;
			StarsImage si = new StarsImage();

			tables.add(si);
			tableA.add(si).width(anchoBoton).space(espacio).height(altoBoton)
					.space(espacio);
			colsperrow++;
			if (colsperrow >= 4) {
				colsperrow = 0;
				tableA.row();
			}

		}
		back.setSize(back.getWidth(), back.getHeight());
		back.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
				return false;
			}
		});
		// fill: rellena la celda

		// table.add(_newgame).width(anchoBoton).height(altoBoton).space(espacio)
		// .row();

		// table.debug();

		back.setPosition(144, 55);
		// _menu.setPosition(BeachAdventure.R_WIDTH * .5f - _menu.getMinWidth()
		// / 2f, BeachAdventure.R_HEIGHT * .5f - _menu.getMaxHeight()
		// / 2f);
		// tableA.setPosition(
		// BeachAdventure.R_WIDTH * .5f - tableA.getMinWidth() / 2f,
		// BeachAdventure.R_HEIGHT * .5f - tableA.getMaxHeight() / 2f);
		tableA.padTop(30f);
		back.setScale(0.6f);
		addActor(backgroundImage);
		addActor(tableA);
		addActor(back);
		backgroundImage.debug();
		tableA.debug();
		back.debug();
		updateHS();
	}

	public void updateHS() {
		for (int i = 1; i < tables.size + 1; i++) {
			tables.get(i - 1).check(
					Integer.parseInt(ScreenManager.getInstance().getGame()
							.getLeaderboard().readWise(i)),
					Integer.parseInt(ScreenManager.getInstance().getGame()
							.getLeaderboard().readBad(i)));
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		super.dispose();
		backT.dispose();
		highScoresScreenT.dispose();
		for (int i = 1; i < tables.size + 1; i++) {
			tables.get(i - 1).dispose();
		}
	}
}
