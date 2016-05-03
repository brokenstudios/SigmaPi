package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.math.Vector2;

import hevs.fragil.patapon.music.Note;
import hevs.fragil.patapon.others.Param;
import hevs.gdx2d.components.physics.PhysicsPolygon;

public class BodyPolygon extends PhysicsPolygon {
	static Vector2 dimensions =  new Vector2(3,80);
	static int nArrows;
	static Vector2 body[] = {
			new Vector2(-20, 0),
			new Vector2(-20, 40),
			new Vector2(0, 60),
			new Vector2(20, 40),
			new Vector2(20, 0)
	};

	public BodyPolygon(Vector2 position) {
		//Ca c'est vraiment super !
//		super("arrow"+nArrows, position, vertices,  8f, 0f, 1f, true);
		super("arrow"+nArrows, body,  0.5f, 0f, 1f, true);
		this.getBody().setBullet(true);
		nArrows++;
	}
	public void setPosition(int position) {
		float distanceToTravel = position - this.getBodyPosition().x;

		// For most of the movement, the target speed is ok
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

		// The rest is pretty much what you had already:
		float desiredVelocity = speedToUse * distanceToTravel;
		float changeInVelocity = desiredVelocity - this.getBodyLinearVelocity().x;

		float force = this.getBodyMass() * 60.0f * changeInVelocity;
		this.applyBodyForceToCenter(force, 0, true);
		
//		int force = 0;
//		Vector2 vel = getBodyLinearVelocity();
//		switch(direction){
//			case -1:  if( vel.x > -1000 ) force = -500;  break;
//			case 0:  force = (int)vel.x * -100; break;
//			case 1: if( vel.x <  1000 ) force =  500; break;
//		}
//		applyBodyForceToCenter(new Vector2(force,0), true);
	}
}