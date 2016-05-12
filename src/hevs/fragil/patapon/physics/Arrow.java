package hevs.fragil.patapon.physics;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsPolygon;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.physics.AbstractPhysicsObject;
import hevs.fragil.patapon.mechanics.Game;
import hevs.fragil.patapon.mechanics.Param;

public class Arrow extends PhysicsPolygon implements Projectile {
	int group;
	int startAngle;
	int collisionGroup;
	//when opacity is zero, arrow is deleted
	float opacity = 1.0f;
	
	//for every arrow
	static BitmapImage img;
	static Vector2 dimensions =  new Vector2(3,80);
	static int nArrows;
	static float[] v1 = {-5, 60, -4, 70, 0, 80, 4, 70, 5, 60, 0, 0};
	
	public Arrow(Vector2 position, int startAngle, int startForce, int collisionGroup) {
		super("arrow"+nArrows, position, getArrowVertices(startAngle),  8f, 0f, 1f, true);
		this.startAngle = startAngle;
		this.getBody().setBullet(true);
		this.group = collisionGroup;
		nArrows++;
		
		this.startAngle = startAngle;
		this.collisionGroup = collisionGroup;
		
		//air resistance
		setBodyAngularDamping(15f);
		
		//same negative index to disable collisions between arrows
		setCollisionGroup(collisionGroup);
		enableCollisionListener();
		
		double angleRadians = Math.toRadians(startAngle);
		applyBodyForceToCenter(new Vector2((float)Math.cos(angleRadians)*startForce, (float)Math.sin(angleRadians)*startForce), true);
		Game.add(this);
	}
	public Arrow(Vector2 position, int startAngle, int startForce) {
		this(position, startAngle, startForce, -1);
	}
	
	@Override
	public void collision(AbstractPhysicsObject theOtherObject, float energy) {
		//Create a joint to stick the arrow
		Game.createWeldJoint(new StickyInfo(this.getBody(), theOtherObject.getBody(),getSpike()));
		
		//TODO change collisiongroup to match the victim group
		if( theOtherObject.name.contains("floor") ){
			System.out.println("much sad, you missed !");
			setBodyActive(false);
		}
		else if( theOtherObject.name.contains("hero") ){
			System.out.println("Oh no ! you've got blessed !");
			//will not collide accidentally with heroes anymore
			setCollisionGroup(Param.HEROES_GROUP);
		}
		else if( theOtherObject.name.contains("ennemy") ){
			System.out.println("yahou ! you hit an ennemy !");
			//will not collide accidentally with ennemies anymore
			setCollisionGroup(Param.ENNEMIES_GROUP);
		}
		else{
			System.out.println("WTF is this object ?");
		}
	}
	public Vector2 getSpike() {
		Vector2 temp = getBodyWorldCenter();
		double angle = getBodyAngle() + Math.PI/3;
		temp.add((float)(Math.cos(angle)*28), (float)(Math.sin(angle)*28));
		return temp;
	}
	private static Vector2[] getArrowVertices(int angle){
		Polygon poly = new Polygon(v1);
		poly.setOrigin(0, 40);
		poly.rotate(angle - 90);
		return verticesToVector2(poly.getTransformedVertices());
	}
	/**
	 * Convert vertices to Vector2's
	 * @param vertices array to convert
	 * @return Vector2[] converted array
	 */
	private static Vector2[] verticesToVector2(float[] vertices){
		if(vertices.length % 2 == 0){
			Vector2[] temp = new Vector2[vertices.length/2];
			int j = 0;
			for(int i = 0 ; i < vertices.length/2 ; i++){
				temp[i] = new Vector2(vertices[j], vertices[++j]);
				j++;
			}
			return temp;
		}
		else{
			return null;
		}
	}
	@Override
	public void draw(GdxGraphics g) {
		float angleDegrees = getBodyAngleDeg() + startAngle;
		double angleRadians = Math.toRadians(angleDegrees);
		//display arrow with better penetration depending of the collision angle
		int distance = 8 + (int)(5*Math.cos(angleRadians));
		Vector2 offset = new Vector2((float)Math.cos(angleRadians)*distance, (float)Math.sin(angleRadians)*distance );
		
		Vector2 pos = getBodyWorldCenter();
		pos = pos.add(offset);
		g.drawAlphaPicture(pos.x, pos.y, angleDegrees, .35f, opacity, img);
	}
	@Override
	public void step(GdxGraphics g) {
		Vector2 v = getBodyLinearVelocity();
		float angle = getBodyAngle();
		double velocity = Math.sqrt(v.x*v.x + v.y*v.y);
		//process lift force relative to the angle and the velocity
		float lift = (float)( -Math.cos(angle+ Math.PI/3)*velocity/5);
		//apply air damping
		applyBodyForceToCenter(v.x/10f, v.y/10f, true);
		applyBodyTorque(lift, true);
		
		this.opacity = Math.max(0, opacity - 0.01f);
	}
	@Override
	public boolean shouldBeDestroyed() {
		if(opacity <= 0)
			return true;
		return false;
	}
	public static void setImgPath(String url) {
		img = new BitmapImage(url);
	}
}