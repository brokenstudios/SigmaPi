package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.math.Vector2;

import hevs.gdx2d.components.physics.PhysicsPolygon;

public class BodyPolygon extends PhysicsPolygon {
	static Vector2 dimensions =  new Vector2(3,80);
	static int nArrows;
	static Vector2 body[] = {
			new Vector2(-100, 0),
			new Vector2(-100, 200),
			new Vector2(0, 300),
			new Vector2(100, 200),
			new Vector2(100, 0)
	};

	public BodyPolygon(Vector2 position) {
		//Ca c'est vraiment super !
//		super("arrow"+nArrows, position, vertices,  8f, 0f, 1f, true);
		super("arrow"+nArrows, body,  0.5f, 0f, 1f, true);
		this.getBody().setBullet(true);
		nArrows++;
	}
	public void move(int direction) {
		int force = 0;
		Vector2 vel = getBodyLinearVelocity();
		switch(direction){
			case -1:  if( vel.x > -1000 ) force = -500;  break;
			case 0:  force = (int)vel.x * -100; break;
			case 1: if( vel.x <  1000 ) force =  500; break;
		}
		applyBodyForceToCenter(new Vector2(force,0), true);
	}
}