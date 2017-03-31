package es.adventure.beach.serialization;

public class IntedPowUpData {
	public float sp;// spawnPeriod
	public int intcode;
	public float xi, yi;

	public IntedPowUpData() {

	}

	public IntedPowUpData(float sp, int intcode, float xi, float yi) {
		this.sp = sp;
		this.intcode = intcode;
		this.xi = xi;
		this.yi = yi;
	}
}
