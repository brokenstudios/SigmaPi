package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.others.Game;

public class Arrow extends FlyingObject{
	ArrowPolygon box;
	static BitmapImage img;
	int startAngle;
	/**
	 * Creates an new arrow
	 * @param position		: the position of the departure
	 * @param startAngle	: the angle 
	 * @param startForce	:
	 */
	public Arrow(Vector2 position, int startAngle, int startForce) {
		this.startAngle = startAngle;
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
		//arrows with better penetration depending of the collision angle
		int distance = 8 + (int)(3*Math.cos(angleRadians));
		Vector2 offset = new Vector2((float)Math.cos(angleRadians)*distance, (float)Math.sin(angleRadians)*distance );
		
		Vector2 pos = box.getBodyWorldCenter();
		pos = pos.add(offset);
		g.drawTransformedPicture(pos.x, pos.y, angleDegrees, .3f, img);
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
	public void updatePhysics(GdxGraphics g) {
		Vector2 v = box.getBodyLinearVelocity();
		double velocity = Math.sqrt(v.x*v.x + v.y*v.y);
		//process lift force relative to the angle and the velocity
		float lift = (float)( -Math.cos(box.getBodyAngle()+ Math.PI/3)*velocity/5);
		
		box.applyBodyTorque(lift, true);
	}
	
}