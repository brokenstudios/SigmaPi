package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import hevs.fragil.patapon.others.Map;
import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;

public class Arrow extends FlyingObject{
	ArrowPolygon box;
	static BitmapImage img;
	
	public Arrow(Vector2 pos, Vector2 speed) {
		//With a polygon
		float[] v1 = {	pos.x - 5, pos.y + 60,
						pos.x - 4, pos.y + 70,
						pos.x, pos.y + 80,
						pos.x + 4, pos.y + 70,
						pos.x + 5, pos.y + 60,
						pos.x, pos.y};
		
		Polygon poly = new Polygon(v1);
		poly.setOrigin(pos.x, pos.y + 40);
		//60 degrees
		poly.rotate(-30);
		
		box = new ArrowPolygon(verticesToVector2(poly.getTransformedVertices()));
		//TODO destroy old poly ?
		
		//air resistance
		box.setBodyAngularDamping(10f);
		
		//same negative index to disable collisions between arrows
		box.setCollisionGroup(-1);
		box.enableCollisionListener();
		
		//60 degrees
		box.applyBodyForceToCenter(new Vector2(50,300), true);
		Map.add(this);
	}
	public static void setImgPath(String url) {
		// TODO Auto-generated method stub
		img = new BitmapImage(url);
	}
	private Vector2[] verticesToVector2(float[] vertices){
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
//		Vector2 pos = box.getBodyWorldCenter();
//		g.drawFilledPolygon(box.getPolygon(),Color.CYAN);
//		g.drawTransformedPicture(pos.x, pos.y, box.getBodyAngleDeg()+60, .3f, img);
		g.drawFilledCircle(box.getBodyWorldCenter().x, box.getBodyWorldCenter().y, 1, Color.GREEN);
		g.drawFilledCircle(box.getSpike().x, box.getSpike().y, 1, Color.YELLOW);
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
		float lift = (float)( -Math.cos(box.getBodyAngle()+ Math.PI/3) * velocity);
		box.applyBodyTorque(lift, true);
	}
	
}