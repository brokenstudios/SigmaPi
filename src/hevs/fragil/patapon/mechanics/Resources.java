package hevs.fragil.patapon.mechanics;

import java.util.Vector;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon;

/**
 * Singleton pattern implementation in Java
 * @author loicg
 *
 */
public class Resources {
	static Vector<PhysicsPolygon> items = new Vector<PhysicsPolygon>();
	private static Resources inst = null;
	
	public static  Resources getInstance() {
		if (inst == null) {
			inst = new Resources();
		}
		return inst;
	}
	private Resources(){
	}
	public void addItem(PhysicsPolygon p){
		items.add(p);
	}
	public Vector<PhysicsPolygon> getItems(){
		return items;
	}

}
