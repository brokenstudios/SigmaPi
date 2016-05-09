package hevs.fragil.patapon.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.mechanics.Game;

public class Arrow implements CollisionGroup, DrawableProjectile{
	//TODO these should be placed in a super class
	ArrowPolygon box;
	static BitmapImage img;
	int startAngle;
	int collisionGroup;
	//when opacity is zero, arrow is deleted
	float opacity = 1.0f;
	
	/**
	 * Creates an new arrow
	 * @param position		: the position of the departure
	 * @param startAngle	: the angle 
	 * @param startForce	:
	 */
	public Arrow(Vector2 position, int startAngle, int startForce, int collisionGroup) {
		this.startAngle = startAngle;
		this.collisionGroup = collisionGroup;
		box = new ArrowPolygon(position, startAngle);
		
		//air resistance
		box.setBodyAngularDamping(15f);
		
		//same negative index to disable collisions between arrows
		box.setCollisionGroup(-1);
		box.enableCollisionListener();
		
		double angleRadians = Math.toRadians(startAngle);
		box.applyBodyForceToCenter(new Vector2((float)Math.cos(angleRadians)*startForce, (float)Math.sin(angleRadians)*startForce), true);
		Game.add(this);
	}
	/**
	 * Must be called during the onInit routine
	 * @param url : image path
	 */
	public static void setImgPath(String url) {
		img = new BitmapImage(url);
	}
	@Override
	public void draw(GdxGraphics g) {	
		float angleDegrees = box.getBodyAngleDeg() + startAngle;
		double angleRadians = Math.toRadians(angleDegrees);
		//display arrow with better penetration depending of the collision angle
		int distance = 8 + (int)(5*Math.cos(angleRadians));
		Vector2 offset = new Vector2((float)Math.cos(angleRadians)*distance, (float)Math.sin(angleRadians)*distance );
		
		Vector2 pos = box.getBodyWorldCenter();
		pos = pos.add(offset);
		g.drawAlphaPicture(pos.x, pos.y, angleDegrees, .35f, opacity, img);
	}
	@Override
	public Vector2 getSpike() {
		return box.getSpike();
	}
	@Override
	public Body getBody() {
		return box.getBody();
	}
	@Override
	public void applyTorque(GdxGraphics g) {
		Vector2 v = box.getBodyLinearVelocity();
		float angle = box.getBodyAngle();
		double velocity = Math.sqrt(v.x*v.x + v.y*v.y);
		//process lift force relative to the angle and the velocity
		float lift = (float)( -Math.cos(angle+ Math.PI/3)*velocity/5);
		//apply air damping
		box.applyBodyForceToCenter(v.x/10f, v.y/10f, true);
		box.applyBodyTorque(lift, true);
	}
	@Override
	public int getCollisionGroup() {
		return this.collisionGroup;
	}
	@Override
	public void decreaseOpacity() {
		this.opacity -= 0.01f;
		if(opacity <= 0){
			box.destroy();
		}
	}
}