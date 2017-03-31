package es.adventure.beach.serialization;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import es.adventure.beach.main.BeachAdventure;

public class LevelData {
	public MapArrayJsonReader majr = new MapArrayJsonReader();

	public int worldTimer;
	public int numberOfDataToUpload;
	public float initPlayer1X,initPlayer1Y,initDrone1X,initDrone1Y;
	public int initialVida;
	public int initialPistol8;
	public int initialEscopeta5;
	public int initialMP516;
	public int initialRocket1;
	public int initialLaser1;
	public Array<AirplaneData> airplanesArray = new Array<AirplaneData>();
	public Array<B2Data> b2sArray = new Array<B2Data>();
	public Array<DroneData> dronesArray = new Array<DroneData>();
	public Array<NubeData> nubesArray = new Array<NubeData>();
	public Array<IntedPowUpData> intedArray = new Array<IntedPowUpData>();

	public LevelData(int level) {
		// write
		// write(level);
		load(level);

	}

	public void write(int level) {
		// Write a new Level Structure code template
		majr.level = level;
		majr.worldTimer = 60;
		majr.numberOfDataToUpload = 6;
		majr.initPlayer1X=0f;
		majr.initPlayer1Y=0f;
		majr.initDrone1X=0f;
		majr.initDrone1Y=0f;
		majr.initialVida = 0;
		majr.initialPistol8 = 0;
		majr.initialEscopeta5 = 0;
		majr.initialMP516 = 0;
		majr.initialRocket1 = 0;
		majr.initialLaser1 = 0;
		majr.airplanesData.add(new AirplaneData(1.1f * 1, 50 * 5 / (+1)
				/ BeachAdventure.PPM, 1f, BeachAdventure.VW_HEIGHT / 2f,
				BeachAdventure.VW_WIDTH, BeachAdventure.VW_HEIGHT / 1.5f));
		majr.b2sData.add(new B2Data(1.1f * 1, 50 * 5 / (+1)
				/ BeachAdventure.PPM, 1f, BeachAdventure.VW_HEIGHT / 2f,
				BeachAdventure.VW_WIDTH, BeachAdventure.VW_HEIGHT / 1.5f));
		majr.airplanesData.add(new AirplaneData(1.1f * 1, 50 * 5 / (+1)
				/ BeachAdventure.PPM, .98f, BeachAdventure.VW_HEIGHT / 2f,
				BeachAdventure.VW_WIDTH, BeachAdventure.VW_HEIGHT / 1.5f));
		majr.dronesData.add(new DroneData(2.2f, true,
				50 * 5 / BeachAdventure.PPM, 0f, BeachAdventure.VW_HEIGHT / 2f,
				BeachAdventure.VW_WIDTH, BeachAdventure.VW_HEIGHT / 3f));
		majr.nubesData.add(new NubeData(3.3f, 0f, 100 / (+1)
				/ BeachAdventure.PPM, BeachAdventure.VW_HEIGHT / 3f,
				BeachAdventure.VW_HEIGHT / 2f));
		majr.intedArtifactsData.add(new IntedPowUpData(4.4f, 1,
				BeachAdventure.VW_WIDTH / 2, BeachAdventure.VW_HEIGHT / 1.2f));

		FileHandle listObjects = Gdx.files.internal("json/LevelData"
				+ Integer.toString(level) + ".json");
		listObjects.delete();
		Json json = new Json();
		listObjects.writeString(json.prettyPrint(majr), false);//
	}

	public void load(int level) {

		Json json = new Json();
		MapArrayJsonReader map = json.fromJson(
				MapArrayJsonReader.class,
				Gdx.files.internal("json/LevelData" + Integer.toString(level)
						+ ".json"));

		this.worldTimer = map.worldTimer;
		this.numberOfDataToUpload = map.numberOfDataToUpload;
		
		this.initPlayer1X = map.initPlayer1X;
		this.initPlayer1Y = map.initPlayer1Y;
		this.initDrone1X = map.initDrone1X;
		this.initDrone1Y = map.initDrone1Y;
		
		this.initialVida = map.initialVida;
		this.initialPistol8 = map.initialPistol8;
		this.initialEscopeta5 = map.initialEscopeta5;
		this.initialMP516 = map.initialMP516;
		this.initialRocket1 = map.initialRocket1;
		this.initialLaser1= map.initialLaser1;
		for (Object a : map.airplanesData) {
			AirplaneData m = (AirplaneData) a;
			airplanesArray.add(m);
		}
		for (Object a : map.b2sData) {
			B2Data m = (B2Data) a;
			b2sArray.add(m);
		}
		for (Object a : map.dronesData) {
			DroneData m = (DroneData) a;
			dronesArray.add(m);
		}
		for (Object a : map.nubesData) {
			NubeData m = (NubeData) a;
			nubesArray.add(m);
		}
		for (Object a : map.intedArtifactsData) {
			IntedPowUpData m = (IntedPowUpData) a;
			intedArray.add(m);
		}

	}

}
