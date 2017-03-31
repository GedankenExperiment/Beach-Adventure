package es.adventure.beach.serialization;

public class B2Data {
	public float sp;// transient skip selrializing this
	public float modulo;
	public float xi, yi;
	public float xf, yf;

	public B2Data() {
	}

	public B2Data(float sp, float modulo, float xi, float yi, float xf,
			float yf) {
		this.sp = sp;
		this.modulo = modulo;

		this.xi = xi;
		this.yi = yi;
		this.xf = xf;
		this.yf = yf;
	}
}
