package es.adventure.beach.serialization;

public class DroneData {
	public  float sp;// spawnPeriod
	public boolean patrol;
	public  float modulo;

	public  float xi, yi;
	public  float xf, yf;
	
	public DroneData() {

	}
	public DroneData(float sp, boolean patrol,float modulo, float xi, float yi, float xf,
			float yf) {
		this.sp = sp;
		this.patrol=patrol;
		this.modulo = modulo;

		this.xi = xi;
		this.yi = yi;
		this.xf = xf;
		this.yf = yf;
	}
}
