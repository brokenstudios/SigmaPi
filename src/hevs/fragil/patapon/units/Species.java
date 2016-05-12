package hevs.fragil.patapon.units;

import java.util.Random;

public enum Species {
	TAPI, FLAPI, KEPI, TAMPI, RAYPI;
	
	private static final Species[] VALUES = values();
	private static final int SIZE = VALUES.length;
	private static final Random RANDOM = new Random();

	public static Species random()  {
		return VALUES[RANDOM.nextInt(SIZE)];
	}
}
