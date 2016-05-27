package hevs.fragil.patapon.physics;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon;
import ch.hevs.gdx2d.components.physics.utils.PhysicsConstants;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import hevs.fragil.patapon.mechanics.CurrentLevel;

public class Arrow extends PhysicsPolygon implements Projectile {
	int startAngle;
	// TODO maybe implement a new method getCollisionGroup()
	// currently done with an interface
	int collisionGroup;
	int newCollisionGroup;
	boolean stuck = false;
	// when life is zero, arrow is deleted
	float life = 1.0f;
	float damage;
	String collidedObjectName;

	// for every arrow
	static BitmapImage img;
	static Vector2 dimensions = new Vector2(3, 80);
	static int nArrows;
	static float[] v1 = { -5, 60, -4, 70, 0, 80, 4, 70, 5, 60, 0, 0 };

	public Arrow(Vector2 startPos, int startAngle, int distance, int collisionGroup, int damage) {
		super("arrow" + nArrows, startPos, getArrowVertices(startAngle), 1f, 0f, 1f, true);
		this.startAngle = startAngle;
		this.collisionGroup = collisionGroup;
		this.newCollisionGroup = collisionGroup;
		this.damage = damage;

		// air resistance
		setBodyAngularDamping(15f);

		// same negative index to disable collisions between arrows
		getBody().setBullet(true);
		setCollisionGroup(collisionGroup);
		enableCollisionListener();

		applyBodyForceToCenter(processForce(startPos, distance), true);
		CurrentLevel.getLevel().add(this);
		nArrows++;
	}

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

	public Arrow(Vector2 position, int startAngle, int startForce) {
		this(position, startAngle, startForce, -1, 1);
	}

	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		
		//Change collision group to avoid undesired new connections
		if(theOtherObject instanceof BodyPolygon){
			//if alive
			if(!(((BodyPolygon)theOtherObject).getLife() <= 0)){
				boolean fatal = ((BodyPolygon)theOtherObject).applyDamage(damage);
				if(fatal){
					setCollisionGroup(((BodyPolygon) theOtherObject).getCollisionGroup());
					theOtherObject.applyBodyAngularImpulse(-1300, true);
				}
			}
		}
		if(!(theOtherObject instanceof Arrow) && !stuck){
			//Create a joint to stick to the other object if not already stuck nor hit an arrow
			CurrentLevel.getLevel().createWeldJoint(new StickyInfo(this.getBody(), theOtherObject.getBody(), getSpike()));
			stuck = true;
		}
	}

	public Vector2 getSpike() {
		Vector2 temp = getBodyWorldCenter();
		double angle = getBodyAngle() + Math.PI / 3;
		temp.add((float) (Math.cos(angle) * 28), (float) (Math.sin(angle) * 28));
		return temp;
	}

	private static Vector2[] getArrowVertices(int angle) {
		Polygon poly = new Polygon(v1);
		poly.setOrigin(0, 40);
		poly.rotate(angle - 90);
		return verticesToVector2(poly.getTransformedVertices());
	}

	/**
	 * Convert vertices to Vector2's
	 * 
	 * @param vertices
	 *            array to convert
	 * @return Vector2[] converted array
	 */
	private static Vector2[] verticesToVector2(float[] vertices) {
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

	@Override
	public void draw(GdxGraphics g) {
		float angleDegrees = getBodyAngleDeg() + startAngle;
		double angleRadians = Math.toRadians(angleDegrees);
		// display arrow with better penetration depending of the collision
		// angle
		int distance = 8 + (int) (5 * Math.cos(angleRadians));
		Vector2 offset = new Vector2((float) Math.cos(angleRadians) * distance,
				(float) Math.sin(angleRadians) * distance);

		Vector2 pos = getBodyWorldCenter();
		pos = pos.add(offset);
		g.drawAlphaPicture(pos.x, pos.y, angleDegrees, .35f, life, img);
	}

	@Override
	public void step(float dt) {
		Vector2 v = getBodyLinearVelocity();
		float angle = getBodyAngle();
		double vNorm = Math.sqrt(v.x * v.x + v.y * v.y) * getBodyMass();

		// process lift force relative to the angle and the velocity
		float lift = (float) (-Math.cos(angle + Math.toRadians(startAngle - 15)) * vNorm / 3 * 60 * dt);

		// apply air damping
		applyBodyTorque(lift, true);

		// if this arrow is stuck, it start degrading itself
		if (stuck)
			this.life = Math.max(0, life - 0.005f);
	}

	@Override
	public boolean shouldBeDestroyed() {
		if (life <= 0)
			return true;
		return false;
	}

	public static void setImgPath(String url) {
		img = new BitmapImage(url);
	}
}