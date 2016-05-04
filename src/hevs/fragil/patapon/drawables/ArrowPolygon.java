package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import hevs.fragil.patapon.others.Game;

public class ArrowPolygon extends PhysicsPolygon {
	static Vector2 dimensions =  new Vector2(3,80);
	static int nArrows;
	
	static int startAngle = 60;
	static float[] v1 = {-5, 60, -4, 70, 0, 80, 4, 70, 5, 60, 0, 0};
	
	public ArrowPolygon(Vector2 position, int startAngle) {
		super("arrow"+nArrows, position, getArrowVertices(startAngle),  8f, 0f, 1f, true);
		this.getBody().setBullet(true);
		nArrows++;
	}
	
	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		if(energy > 0){
			System.out.println(theOtherObject.name + " collided with " + this.name + " with " + energy + " energy" );
		if(energy > 5)
			System.out.println(this.name + " is now stuck in the floor !");
			Game.createWeldJoint(new StickyInfo(this.getBody(), theOtherObject.getBody(),getSpike()));
		}
	}
	public Vector2 getSpike() {
		Vector2 temp = getBodyWorldCenter();
		double angle = getBodyAngle() + Math.PI/3;
		temp.add((float)(Math.cos(angle)*28), (float)(Math.sin(angle)*28));
		return temp;
	}
	private static Vector2[] getArrowVertices(int angle){
		Polygon poly = new Polygon(v1);
		poly.setOrigin(0, 40);
		poly.rotate(startAngle - 90);
		return verticesToVector2(poly.getTransformedVertices());
	}
	/**
	 * Convert vertices to Vector2's
	 * @param vertices array to convert
	 * @return Vector2[] converted array
	 */
	private static Vector2[] verticesToVector2(float[] vertices){
		if(vertices.length % 2 == 0){
			Vector2[] temp = new Vector2[vertices.length/2];
			int j = 0;
			for(int i = 0 ; i < vertices.length/2 ; i++){
				temp[i] = new Vector2(vertices[j], vertices[++j]);
				j++;
			}
			return temp;
		}
		else{
			return null;
		}
	}
}