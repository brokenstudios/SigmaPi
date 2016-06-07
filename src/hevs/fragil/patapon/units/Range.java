package hevs.fragil.patapon.units;

public class Range {
	private float min, max;
	
	Range(float min, float max){
		this.setMin(min);
		this.setMax(max);
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}


}
