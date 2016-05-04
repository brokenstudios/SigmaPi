package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon;
import hevs.fragil.patapon.music.Note;
import hevs.fragil.patapon.others.Param;

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
		this.getBody().setBullet(true);
		this.setCollisionGroup(-1);
		nArrows++;
	}
	public void setPosition(int position) {
		float distanceToTravel = position - this.getBodyPosition().x;
		float time = Param.WALK_TIME;
		//add bonus time (faster move with fever)
		time -= Param.WALK_TIME_BONUS/100.0 * Note.getFeverCoefficient();
		float speedToUse = distanceToTravel / time;

		// Check if this speed will cause overshoot in the next time step.
		// If so, we need to scale the speed down to just enough to reach
		// the target point. (Assuming here a step length based on 60 fps)
		float distancePerTimestep = speedToUse / 60.0f;
		if ( distancePerTimestep > distanceToTravel )
		    speedToUse *= ( distanceToTravel / distancePerTimestep );

		float desiredVelocity = speedToUse * distanceToTravel;
		float changeInVelocity = desiredVelocity - this.getBodyLinearVelocity().x;

		float force = this.getBodyMass() * 60.0f * changeInVelocity;
		this.applyBodyForceToCenter(force, 0, true);
	}
}