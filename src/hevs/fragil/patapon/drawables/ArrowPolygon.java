package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.math.Vector2;

import hevs.gdx2d.components.physics.PhysicsPolygon;
import hevs.gdx2d.lib.physics.AbstractPhysicsObject;

public class ArrowPolygon extends PhysicsPolygon {

	public ArrowPolygon(Vector2[] vertices) {
		super("arrow", vertices,  50f, 0f, 0.1f, true);
	}
	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		System.out.println("Such wow ! Swag !  : " + theOtherObject.name + " collided with " + this.name + " with " + energy + " energy" );
	}
}
