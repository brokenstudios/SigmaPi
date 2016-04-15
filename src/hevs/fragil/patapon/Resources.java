package hevs.fragil.patapon;

/**
 * Singleton pattern implementation in Java
 * @author loicg
 *
 */
public class Resources {
	public int toto;
	public int titi;
	
	private static Resources inst = null;

	static public Resources getInstance() {
		if (inst == null) {
			inst = new Resources();
		}
		return inst;
	}

	private Resources() {
	}

}
