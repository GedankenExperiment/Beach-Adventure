package es.adventure.beach.desktop;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
//import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

import es.adventure.beach.main.BeachAdventure;
public class DesktopLauncher {
	static String in="C:/TEXTUREPACKER/ATLAS/total2/";
	static String out="C:/proyectoLibGDX/Beach Adventure/android/assets/";
	public static void main (String[] arg) {
		 Settings settings = new Settings();
		 settings.pot=false;
		 settings.paddingX=2;
		 settings.paddingY=2;
		 settings.bleed=true;
		 settings.edgePadding=false;
		 settings.duplicatePadding=false;
		 settings.rotation=false;
		 settings. minWidth	 =16;
		 settings. minHeight=16;
	       
		 settings.maxWidth = 2048;
	        settings.maxHeight = 2048;
	        settings.pot=true;
	        settings. square=true;
	        settings. stripWhitespaceX=false;
	        settings. stripWhitespaceY=false;
	        settings.alphaThreshold=100;
	        settings.filterMin=Texture.TextureFilter.Nearest;
	        settings.filterMag=Texture.TextureFilter.Nearest;
	        
	        settings.outputFormat="png";
	        settings.jpegQuality=1;
	        settings.ignoreBlankImages=true;
	        settings.fast=false;
	        settings.debug=false;
	   settings.atlasExtension=".pack";
	  // settings.square=true;
	   
	   //1 eliminar from assets
	   //2 actualizar assets
	   //3 descomentar y run
	   //4 actualizar assets
	   //5 comentar
	   //doestn overwrite. DELETE total.pack and total.png before uncomment line below
//TexturePacker.process(settings,in, out, "total");
	   
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		DesktopLeaderboard dlb=new DesktopLeaderboard();
		
		//dlb.getPrefs("user", 113);
		BeachAdventure game=new BeachAdventure(dlb);
		
		new LwjglApplication(game, config);
	}
}
