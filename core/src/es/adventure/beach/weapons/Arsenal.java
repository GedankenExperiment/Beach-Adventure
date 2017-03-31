package es.adventure.beach.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import es.adventure.beach.main.BeachAdventure;
import es.adventure.beach.scenes.GameScreen;

public class Arsenal extends Table implements Disposable {
	protected Texture itemT;
	protected TextureRegion itemR;
	protected int bulletsPerCargador;
	protected int currentInCargador;
	protected int numberOfBulletsInCargadores;
	protected TextureRegionDrawable weaponDraw;
	protected Image weaponImage;
	protected Label cargadorBullets;
	protected Label infoBullets;
	protected int weaponCode;
	protected int spawned;
	protected int hits;
	public Arsenal(final GameScreen screen, final int weaponCode,
			int bulletPerCargador, int initialJsonNumberOfCargadores, Skin skin) {
		this.spawned=0;
		this.hits=0;
		this.bulletsPerCargador = bulletPerCargador;

		this.numberOfBulletsInCargadores = (initialJsonNumberOfCargadores - 1)
				* bulletPerCargador;
		if (numberOfBulletsInCargadores < 0) {
			numberOfBulletsInCargadores = 0;
		}
		if (initialJsonNumberOfCargadores > 0) {
			this.currentInCargador = bulletPerCargador;
		} else {
			this.currentInCargador = 0;
		}
		this.weaponCode = weaponCode;
		this.setName("ARSENAL " + Integer.toString(weaponCode));
		this.setTouchable(Touchable.enabled);
		itemT = new Texture("ARSENAL" + Integer.toString(weaponCode) + ".png");
		itemR = new TextureRegion(itemT);
		weaponDraw = new TextureRegionDrawable(itemR);

		weaponImage = new Image(weaponDraw);
		weaponImage.setTouchable(Touchable.disabled);
		cargadorBullets= new Label(Integer.toString(currentInCargador), skin);

		infoBullets = new Label(Integer.toString(numberOfBulletsInCargadores), skin);
		
		cargadorBullets.setAlignment(Align.bottomLeft);
		infoBullets.setAlignment(Align.topRight);
		
		cargadorBullets.setTouchable(Touchable.disabled);
		infoBullets.setTouchable(Touchable.disabled);
		cargadorBullets.setFontScale(BeachAdventure.LABELSCALE);
		infoBullets.setFontScale(BeachAdventure.LABELSCALE*.61f);

//		weaponImage.setOrigin(0, 0);
//		infoBullets.setOrigin(0, 0);
//
//		weaponImage.setPosition(0, 0);
//		
//		cargadorBullets.setPosition(-32, -0);
//		infoBullets.setPosition(0, -0);
		this.setTouchable(Touchable.enabled);
		this.addListener(new InputListener() {

			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				screen.setCurrentHudWeapon(weaponCode);

				return true;
			}

		});
	
		//weaponImage.setSize(128, 64);
		cargadorBullets.setSize(32,32);
		infoBullets.setSize(32,32);
		this.setSize(128 +64, 64);
///
		
		this.add(cargadorBullets);
		this.add(infoBullets);
		this.add(weaponImage);
	
//		this.add(addressLabel);
//		this.add(addressText);
		    ///
//		cargadorBullets.debug();
//		infoBullets.debug();
//		weaponImage.debug();
//		
//		
//		this.debugTable();
	}

	public void shot() {
		spawned++;
		currentInCargador -= 1;// detray bullet
		cargadorBullets.setText(Integer.toString(currentInCargador));
		infoBullets.setText(Integer.toString(numberOfBulletsInCargadores));
	}
	public void hits() {
		hits++;
		
	}
	public void reload() {

		while (currentInCargador < bulletsPerCargador
				&& numberOfBulletsInCargadores > 0) {
			numberOfBulletsInCargadores -= 1;// cargador taken
			currentInCargador += 1;// reloaded cargador

		}
		cargadorBullets.setText(Integer.toString(currentInCargador));
		infoBullets.setText(Integer.toString(numberOfBulletsInCargadores));
	}

	public void cargadorExtra() {
		numberOfBulletsInCargadores += bulletsPerCargador;

		cargadorBullets.setText(Integer.toString(currentInCargador));
		infoBullets.setText(Integer.toString(numberOfBulletsInCargadores));
	}

	public boolean available() {
		if (currentInCargador > 0) {
			return true;
		}

		return false;
	}

	public boolean dry() {

		if (numberOfBulletsInCargadores > 0) {
			return false;
		}

		return true;

	}

	@Override
	public void dispose() {
		itemT.dispose();

	}

}
