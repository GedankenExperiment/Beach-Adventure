package es.adventure.beach.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import es.adventure.beach.main.Leaderboard;

public class DesktopLeaderboard implements Leaderboard {
	private Preferences wisePref;// called from HighScores and Counter Classes
	private Preferences badPref;// called from HighScores and Counter Classes
	private Preferences volumePref;// called from GameScreen
	private Preferences topAvailableLevel;
	private static final String VOLUME = "es.adventure.beach.volume";
	private static final String WISE = "es.adventure.beach.wisescores";
	private static final String BAD = "es.adventure.beach.badscores";
	private static final String TOPLEVEL = "es.adventure.beach.level";
	private boolean arsenalVisible=false;
	@Override
	public void initPrefs(String user, int score) {

		volumePref = Gdx.app.getPreferences(VOLUME);
		topAvailableLevel = Gdx.app.getPreferences(TOPLEVEL);
		wisePref = Gdx.app.getPreferences(WISE);
		badPref = Gdx.app.getPreferences(BAD);
		if (volumePref.getString("VolumeMusic") == "") {

			volumePref.putString("VolumeMusic", Float.toString(0.4f));
			volumePref.putString("VolumeSound", Float.toString(0.7f));
			volumePref.putBoolean("Allowed", true);
			volumePref.flush();
		}
		// borrar();
		if (topAvailableLevel.getString("LevelKey") == "") {

			topAvailableLevel.putString("LevelKey", Integer.toString(1));
			topAvailableLevel.flush();
		}

		for (int i = 1; i < 25 + 1; i++) {
			if (wisePref.getString("WiseKey" + Integer.toString(i)) == "") {

				wisePref.putString("WiseKey" + Integer.toString(i),
						Integer.toString(0));
				wisePref.flush();

			}

			if (badPref.getString("BadKey" + Integer.toString(i)) == "") {

				badPref.putString("BadKey" + Integer.toString(i),
						Integer.toString(0));
				badPref.flush();

			}

		}

	}

	@Override
	public void writeWise(int wise_stars, int level) {
		if (wise_stars > Integer
				.parseInt(wisePref.getString("WiseKey" + level))) {
			wisePref.putString("WiseKey" + level, Integer.toString(wise_stars));
			wisePref.flush();
		}

	}

	@Override
	public void writeBad(int bad_stars, int level) {
		if (bad_stars > Integer.parseInt(badPref.getString("BadKey" + level))) {
			badPref.putString("BadKey" + level, Integer.toString(bad_stars));
			badPref.flush();
		}

	}

	@Override
	public void promoteLevel(int bad_stars, int level) {
		if (bad_stars > 0) {

			topAvailableLevel
					.putString("LevelKey", Integer.toString(level + 1));
			topAvailableLevel.flush();
		}

	}

	@Override
	public float getVolumeMusic() {

		return Float.parseFloat(volumePref.getString("VolumeMusic"));
	}

	@Override
	public float getVolumeSound() {

		return Float.parseFloat(volumePref.getString("VolumeSound"));
	}

	@Override
	public String readWise(int i) {
		return wisePref.getString("WiseKey" + Integer.toString(i));
	}

	@Override
	public String readBad(int i) {
		return badPref.getString("BadKey" + Integer.toString(i));
	}

	@Override
	public int readLevel() {

		return Integer.parseInt(topAvailableLevel.getString("LevelKey"));
	}

	@Override
	public void writeVolumeMusic(float musicVolume) {

		volumePref.putString("VolumeMusic", Float.toString(musicVolume));
		volumePref.flush();
	}

	@Override
	public void writeVolumeSound(float soundVolume) {
		volumePref.putString("VolumeSound", Float.toString(soundVolume));
		volumePref.flush();
	}

	@Override
	public void borrar() {

		wisePref.clear();
		badPref.clear();
		volumePref.clear();
		topAvailableLevel.clear();

	}

	@Override
	public boolean readAllowedVolume() {
		return volumePref.getBoolean("Allowed");
	}

	@Override
	public void writeAllowedVolume(boolean allowed) {
		volumePref.putBoolean("Allowed", allowed);
		volumePref.flush();

	}

	@Override
	public InputListener weaponSelectionListener() {
		InputListener weaponListener = new InputListener() {

			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (arsenalVisible) {
					arsenalVisible = false;
					
				} else {
					arsenalVisible = true;
				
				}
				return true;
				
			}
			
		};
		return weaponListener;
	}
	@Override
	public boolean isArsenalVisible() {
		return arsenalVisible;
	}
	@Override
	public void setArsenalVisible(boolean arsenalVisible) {
		this.arsenalVisible = arsenalVisible;
	}
}
