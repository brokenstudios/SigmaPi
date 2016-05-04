package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon;

public abstract class FlyingPolygon extends PhysicsPolygon{
	public FlyingPolygon(String n, Vector2[] v, float d, float r, float f, boolean b) {
		super(n, v, d, r, f, b);
	}

	public abstract Vector2 getTopPosition();
}
