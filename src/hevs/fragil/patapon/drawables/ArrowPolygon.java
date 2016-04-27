package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.math.Vector2;

import hevs.fragil.patapon.others.Map;
import hevs.gdx2d.components.physics.PhysicsPolygon;
import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

public class ArrowPolygon extends PhysicsPolygon {
	static Vector2 dimensions =  new Vector2(3,80);

	public ArrowPolygon(Vector2[] vertices) {
		super("arrow", vertices,  50f, 0f, 0.1f, true);
	}
	
	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		if(energy > 0)
			System.out.println("Such wow ! Swag !  : " + theOtherObject.name + " collided with " + this.name + " with " + energy + " energy" );
		if(energy > 30){
			System.out.println("Much power ! The polygon is now stuck in the floor !");
			Map.join(this, getSpike());
		}
	}
	public Vector2 getSpike() {
		Vector2 temp = getBodyWorldCenter();
		double angle = getBodyAngle()+Math.PI/3;
		temp.add((float)(Math.cos(angle)*30), (float)(Math.sin(angle)*30));
		return temp;
	}
}
