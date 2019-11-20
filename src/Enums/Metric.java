package Enums;

public enum Metric {

	LOC(80), CYCLO(10), ATFD(4), LAA(0.42f);

	private float max;

	private Metric(float max) {
		this.max = max;
	}

	public float getMax() {
		return max;
	}

}
