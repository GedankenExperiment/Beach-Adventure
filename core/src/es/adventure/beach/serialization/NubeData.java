package es.adventure.beach.serialization;

public class NubeData {
	public float sp;// spawnPeriod
	public float modulo,fase;
	public float xi, yi;

	public NubeData() {

	}
	public NubeData(float sp, float modulo, float fase,float xi, float yi) {
		this.sp = sp;
		this.modulo = modulo;
		this.xi = xi;
		this.yi = yi;
	}
}
