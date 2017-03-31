 package es.adventure.beach.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;


public class Hud implements Disposable {
	private Texture touchpadT, knobT;
	private TextureRegionDrawable touchpadTRD, knobTRD;
	private TouchpadStyle hudStyle;
	private Touchpad touchpad;

	public Touchpad getTouchpad() {
		return touchpad;
	}

	public Hud(final GameScreen screen) {
		touchpadT = new Texture("TOUCHPADDRONE.png");
		knobT = new Texture("DRONEKNOB.png");
		hudStyle = new TouchpadStyle(touchpadTRD, knobTRD);

		touchpad = new Touchpad(5f, hudStyle);

		touchpad.setOrigin(touchpad.getWidth() / 2, touchpad.getHeight() / 2);
		touchpad.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (screen.isWhoFires()) {
					if (Math.abs(touchpad.getKnobPercentX()) < 0.01
							&& Math.abs(touchpad.getKnobPercentY()) < 0.01) {
						screen.getDrone1().setJetting(false);
					} else {

						screen.getDrone1().setJetting(
								true,
								150f * Math.sqrt(Math.pow(
										touchpad.getKnobPercentX(), 2)
										+ Math.pow(touchpad.getKnobPercentY(),
												2)),
								Math.atan2(touchpad.getKnobPercentY(),
										touchpad.getKnobPercentX()));//
					}
				} else {

					if (Math.abs(touchpad.getKnobPercentX()) <= 0.01) {
						screen.getPlayer().stopMove();
					} else if (touchpad.getKnobPercentX() < -0.01) {
						screen.getPlayer().moveLeft();

					} else if (touchpad.getKnobPercentX() > 0.01) {

						screen.getPlayer().moveRight();

					}
					if (Math.abs(touchpad.getKnobPercentX()) <= 0.01) {
						screen.getPlayer().stopMove();

					}

					Gdx.app.log("WorldBodySize: ",
							Integer.toString(screen.getWorld().getBodyCount()));
				}

			}

		});
		touchpad.setName("DronePad");


	}

	@Override
	public void dispose() {
		touchpadT.dispose();
		knobT.dispose();

		
	}
}
