package es.adventure.beach.main;

import com.badlogic.gdx.scenes.scene2d.InputListener;

import es.adventure.beach.singleton.ScreenManager;

public interface Leaderboard {
	public void initPrefs(String user, int score);
	public void writeWise(int wise_stars,int level);
	public void writeBad(int bad_stars,int level);

	public void promoteLevel(int bad_stars,int level);
	public void writeVolumeMusic(float musicVolume);
	public void writeVolumeSound(float msoundVolume);

	public float getVolumeMusic();
	public float getVolumeSound();
	public String readWise(int i);
	public String readBad(int i);
	public int readLevel();
	public void borrar();
	public boolean readAllowedVolume();
	public void writeAllowedVolume(boolean allowed);
	
	
	public InputListener weaponSelectionListener();
	public boolean isArsenalVisible();
	public void setArsenalVisible(boolean bool);
}
