package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.math.Vector2;

import hevs.fragil.patapon.others.Map;
import hevs.gdx2d.components.physics.PhysicsPolygon;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public abstract class PointedObject implements DrawableObject {
	protected PhysicsPolygon box;
	protected Vector2 dimensions = new Vector2(10,50);
	
	protected PointedObject(Vector2 pos, Vector2 speed){
		// The front of the rocket is up by default
		Vector2[] p = {new Vector2(-5,20),
				new Vector2(0,30),
				new Vector2(5,20),
				new Vector2(0,-20)
				};
		box = new PhysicsPolygon("pointedObject", p, true);
		box.setBodyAngularDamping(0.4f);
		box.setBodyLinearDamping(0.2f);
		box.applyBodyForceToCenter(	(float) Math.cos(box.getBodyAngle())* 100, 
									(float) Math.sin(box.getBodyAngle())* 60, true);
		Map.add(this);
	}
}
