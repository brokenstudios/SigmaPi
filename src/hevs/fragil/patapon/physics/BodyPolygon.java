package hevs.fragil.patapon.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon;

public class BodyPolygon extends PhysicsPolygon {
	static Vector2 dimensions =  new Vector2(3,80);
	static int nArrows;
	static Vector2 body[] = {
			new Vector2(-30, 0),
			new Vector2(-30, 60),
			new Vector2(0, 80),
			new Vector2(30, 60),
			new Vector2(30, 0)
	};

	public BodyPolygon(Vector2 position) {
		//Ca c'est vraiment super !
		super("arrow"+nArrows, position, body,  10f, 1f, 1f, true);
		getBody().setBullet(true);
		setCollisionGroup(-1);
		nArrows++;
	}
	public void moveToLinear(int position, double travelTime) {
		double distanceToTravel = position - getBodyPosition().x;
		double globalSpeed = distanceToTravel / travelTime;
		int fps = Gdx.graphics.getFramesPerSecond();

		// Check if this speed will cause overshoot in the next time step.
		// If so, we need to scale the speed down to just enough to reach
		// the target point. (Assuming here a step length based on 60 fps)
		double stepDistance = globalSpeed * (1.0/fps);
		if ( Math.abs(stepDistance) > Math.abs(distanceToTravel) )
		    globalSpeed *= ( distanceToTravel / stepDistance );

		double desiredAcceleration = Math.abs(globalSpeed) * distanceToTravel;
		double changeInAcceleration = desiredAcceleration - this.getBodyLinearVelocity().x;
		
		double force = this.getBodyMass() * fps * changeInAcceleration;
		this.applyBodyForceToCenter((float)force, 0, true);
	}
}