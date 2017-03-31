package es.adventure.beach.scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

import es.adventure.beach.main.Assets;
import es.adventure.beach.main.BeachAdventure;

public class ArsenalVida extends Table implements Disposable{
private Texture texture;
private TextureRegion textureR;
	private int currentInCargador;

	private TextureRegionDrawable weaponDraw;
	private Image weaponImage;
	private Label infoBullets;

	public ArsenalVida(String textureName,int initialJsonNumberOfVidas,Skin skin) {
		texture=new Texture(textureName);
		textureR=new TextureRegion(texture);
		this.currentInCargador = initialJsonNumberOfVidas;

		weaponDraw = new TextureRegionDrawable(textureR);

		weaponImage = new Image(weaponDraw);
		infoBullets = new Label(Integer.toString(currentInCargador) ,skin);
		infoBullets.setFontScale(BeachAdventure.LABELSCALE);

		weaponImage.setOrigin(0, 0);
		infoBullets.setOrigin(0, 0);
		weaponImage.setSize(64, 64);
		infoBullets.setSize(64, 64);
		infoBullets.setPosition(0, 0);

		

		this.add(weaponImage);
		this.add(infoBullets);

		this.setSize(64 * 2, 64);
		this.debugTable();
	}

	public void shot() {
		if (available()) {// allowed shot
			currentInCargador -= 1;// detray bullet

		} else {

		}
		infoBullets.setText(""+currentInCargador );
	}

	public void reload() {

		currentInCargador += 1;// reloaded cargador

		infoBullets.setText(""+currentInCargador );
	}

	public void cargadorExtra() {
		currentInCargador += 1;// reloaded cargador

		infoBullets.setText(""+currentInCargador );
	}

	public boolean available() {
		if (currentInCargador > 0) {
			return true;
		}

		return false;
	}

	@Override
	public void dispose() {
		texture.dispose();
		
	}
}
