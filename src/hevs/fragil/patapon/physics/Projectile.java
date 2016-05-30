package hevs.fragil.patapon.physics;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon;
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import hevs.fragil.patapon.mechanics.CurrentLevel;

public abstract class Projectile extends PhysicsPolygon{
	protected int startAngle;
	protected boolean stuck = false;
	protected float life = 1.0f;
	protected float damage;
	
	protected Projectile(Vector2 startPos, int startAngle, int collisionGroup, int distance, int damage, Vector2[] vertices, String name) {
		super(name, startPos, vertices, 1f, 0f, 1f, true);
		this.startAngle = startAngle;
		this.damage = damage;
		
		// air resistance
		setBodyAngularDamping(15f);
		
		// same negative index to disable collisions between projectiles
		getBody().setBullet(true);
		setCollisionGroup(collisionGroup);
		enableCollisionListener();

		applyBodyForceToCenter(processForce(startPos, distance), true);
		CurrentLevel.getLevel().add(this);
	}
	/**
	 * Compute the necessary force for the arrow to travel the {@code distance} parameter
	 * @param startPos : start position from the arrow (give essentially the y coordinate)
	 * @param distance : the distance to travel
	 * @return the force to apply during 1 fps
	 */
	private Vector2 processForce(Vector2 startPos, double distance) {
		double startAngleRad = Math.toRadians(startAngle);
		Vector2 g = PhysicsWorld.getInstance().getGravity();

		double a = -g.y / 2 / Math.tan(startAngleRad);
		double b = 0;
		double c = -PhysicsConstants.PIXEL_TO_METERS * (startPos.y / Math.tan(startAngleRad) + distance);

		double t1 = (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
		double t2 = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
		double t = Math.max(t1, t2);

		double vx = (PhysicsConstants.PIXEL_TO_METERS * distance / t);
		double v = vx / Math.cos(startAngleRad);
		double a0 = v * 60;
		double f0 = a0 * getBodyMass();
		
		Vector2 force = new Vector2();
		force.x = (float) (f0 * Math.cos(startAngleRad));
		force.y = (float) (f0 * Math.sin(startAngleRad));
		return force;
	}

	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		
		//Change collision group to avoid undesired new connections
		if(theOtherObject instanceof BodyPolygon && !stuck){
			//if alive
			if(!(((BodyPolygon)theOtherObject).getLife() <= 0)){
				boolean fatal = ((BodyPolygon)theOtherObject).applyDamage(damage);
				if(fatal){
					setCollisionGroup(((BodyPolygon) theOtherObject).getCollisionGroup());
					theOtherObject.applyBodyAngularImpulse(-1300, true);
				}
			}
		}
		if(!(theOtherObject instanceof Projectile) && !stuck){
			//Create a joint to stick to the other object if not already stuck nor hit an arrow
			CurrentLevel.getLevel().createWeldJoint(new StickyInfo(this.getBody(), theOtherObject.getBody(), getSpike()));
			stuck = true;
		}
	}

	public Vector2 getSpike() {
		Vector2 temp = getBodyWorldCenter();
		double angle = getBodyAngle() + startAngle;
		temp.add((float) (Math.cos(angle) * 28), (float) (Math.sin(angle) * 28));
		return temp;
	}

	public void draw(GdxGraphics g){};

	public abstract void step(float dt);

	public boolean shouldBeDestroyed() {
		if (life <= 0)
			return true;
		return false;
	}
	/**
	 * Convert vertices to Vector2 array
	 * @param float[] array to convert
	 * @return Vector2[] converted array
	 */
	protected static Vector2[] verticesToVector2(float[] vertices) {
		if (vertices.length % 2 == 0) {
			Vector2[] temp = new Vector2[vertices.length / 2];
			int j = 0;
			for (int i = 0; i < vertices.length / 2; i++) {
				temp[i] = new Vector2(vertices[j], vertices[++j]);
				j++;
			}
			return temp;
		} else {
			return null;
		}
	}
}