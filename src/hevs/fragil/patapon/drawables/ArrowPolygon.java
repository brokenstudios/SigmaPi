package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.math.Vector2;

import hevs.fragil.patapon.others.Map;
import hevs.gdx2d.components.physics.PhysicsPolygon;
import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

public class ArrowPolygon extends PhysicsPolygon {
	static Vector2 dimensions =  new Vector2(3,80);
	static int nArrows;

	public ArrowPolygon(Vector2[] vertices) {
		super("arrow"+nArrows, vertices,  5f, 0f, 0.5f, true);
		this.getBody().setBullet(true);
		nArrows++;
	}
	
	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		if(energy > 0)
			System.out.println(theOtherObject.name + " collided with " + this.name + " with " + energy + " energy" );
		if(energy > 5){
			System.out.println(this.name + " is now stuck in the floor !");
			Map.createWeldJoint(new StickyInfo(this.getBody(), theOtherObject.getBody(),getSpike()));
//			Map.disable(this);
		}
	}
	public Vector2 getSpike() {
		Vector2 temp = getBodyWorldCenter();
		double angle = getBodyAngle()+Math.PI/3;
		temp.add((float)(Math.cos(angle)*28), (float)(Math.sin(angle)*28));
		return temp;
	}
}
