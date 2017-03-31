package es.adventure.beach.gameutil;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import es.adventure.beach.main.Assets;

public class StarsImage extends Table implements Disposable{
	private Texture wiseT, badT;
	private TextureRegion wiseR, badR;
	private TextureRegionDrawable wiseStarDrawable, badStarDrawable;
	private Image w1, w2, w3, b1, b2, b3;
	private Array<Image> wiseArr, badArr;

	public StarsImage() {
		wiseT = new Texture("wiseStar.png");
		badT = new Texture("badStar.png");
		wiseR = new TextureRegion(wiseT);
		badR = new TextureRegion(badT);
		wiseStarDrawable = new TextureRegionDrawable(wiseR);

		badStarDrawable = new TextureRegionDrawable(badR);
		wiseArr = new Array<Image>(3);
		badArr = new Array<Image>(3);

		w1 = new Image(wiseStarDrawable);
		w2 = new Image(wiseStarDrawable);
		w3 = new Image(wiseStarDrawable);
		w1.setOrigin(0, 0);
		w2.setOrigin(0, 0);
		w3.setOrigin(0, 0);

		w1.setVisible(false);
		w2.setVisible(false);
		w3.setVisible(false);

		w1.setPosition(-32, 0);
		w2.setPosition(0, 0);
		w3.setPosition(+32, 0);

		w1.setSize(32f, 32f);
		w2.setSize(32f, 32f);
		w3.setSize(32f, 32f);

		this.add(w1).space(0);
		this.add(w2).space(0);
		this.add(w3).space(0);

		wiseArr.add(w1);
		wiseArr.add(w2);
		wiseArr.add(w3);
		this.row();

		b1 = new Image(badStarDrawable);
		b2 = new Image(badStarDrawable);
		b3 = new Image(badStarDrawable);
		b1.setOrigin(0, 0);
		b2.setOrigin(0, 0);
		b3.setOrigin(0, 0);

		b1.setVisible(false);
		b2.setVisible(false);
		b3.setVisible(false);

		b1.setPosition(-32, 0);
		b2.setPosition(0, 0);
		b3.setPosition(+32, 0);

		b1.setSize(32f, 32f);
		b2.setSize(32f, 32f);
		b3.setSize(32f, 32f);

		this.add(b1).space(0);
		this.add(b2).space(0);
		this.add(b3).space(0);

		badArr.add(b1);
		badArr.add(b2);
		badArr.add(b3);
		this.debugTable();
	}

	public void check(int wise, int bad) {
		for (int w = 0; w < wise; w++) {
			wiseArr.get(w).setVisible(true);
		}
		for (int b = 0; b < bad; b++) {
			badArr.get(b).setVisible(true);
		}
	}

	@Override
	public void dispose() {
		wiseT.dispose();
		badT.dispose();
		
	}
}
