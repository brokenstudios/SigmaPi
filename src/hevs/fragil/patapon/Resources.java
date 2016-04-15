package hevs.fragil.patapon;

/**
 * Singleton pattern implementation in Java
 * @author loicg
 *
 */
public class Resources {
	
	private static Resources inst = null;
	public static  Resources getInstance() {
		if (inst == null) {
			inst = new Resources();
		}
		return inst;
	}
	private Resources(){
	}

}
