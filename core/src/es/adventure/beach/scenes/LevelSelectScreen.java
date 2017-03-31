package es.adventure.beach.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

import es.adventure.beach.main.Assets;
import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.singleton.ScreenEnum;
import es.adventure.beach.singleton.ScreenManager;
import es.adventure.beach.singleton.UIFactory;

public class LevelSelectScreen extends AbstractScreen {
	private Skin skin;
	private Texture levelT;
	private String levelLoad;
	private Image backI;
	private Image backgroundImage;
	private Table table, tableB;
	private int colsperrow;
	final int anchoBoton = 144;
	final int altoBoton = 55;
	final int espacio = 55;

	public LevelSelectScreen() {
		super();

	}

	@Override
	public void buildStage() {
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		levelT = new Texture("LEVEL.png");
		backgroundImage = new Image(levelT);
		backI = new Image();
		table = new Table();
		table.setFillParent(true);
		table.center();

		tableB = new Table();
		tableB.setFillParent(true);
		tableB.bottom();

		backI.setPosition(0, 0);
		levelLoad = "1";

		colsperrow = 0;
		String sentence = "1 2 3 4 5 6 7 8 9 10";
		String[] temp = sentence.split(" ");
		Array<String> words = new Array<String>(temp);

		final Label info = new Label("", skin);
		for (int i = 1; i < words.size + 1; i++) {
			// make i final here so you can reference it inside
			// the anonymous class
			final int index = i;

			Image button = new Image();

			;
			if (index <= ScreenManager.getInstance().getGame().getLeaderboard()
					.readLevel()) {
				button.addListener(UIFactory.createListener(ScreenEnum.GAME,
						index));
			}

			table.add(button).width(anchoBoton).space(espacio)
					.height(altoBoton).space(espacio);
			colsperrow++;
			if (colsperrow >= 5) {
				colsperrow = 0;
				table.row();
			}

		}
		backI.setSize(340, 130);
		backI.addListener(UIFactory.createListener(ScreenEnum.MAIN_MENU));
		backI.setPosition(203, 93);
		backgroundImage
				.setSize(BeachAdventure.V_WIDTH, BeachAdventure.V_HEIGHT);
		addActor(backgroundImage);
		addActor(info);
		addActor(backI);
		addActor(table);
		backgroundImage.debug();
		table.debug();
		backI.debug();
		info.debug();

	}

	@Override
	public void dispose() {
		super.dispose();
		levelT.dispose();
		skin.dispose();

	}

	public String getLevelLoad() {
		return levelLoad;
	}
}
