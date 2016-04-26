package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import hevs.fragil.patapon.others.Map;
import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public class Arrow implements DrawableObject {
	static BitmapImage img ;
	static Vector2 dimensions =  new Vector2(3,80);
	ArrowPolygon body;
	
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
		
		Vector2[] v2 = verticesToVector2(poly.getTransformedVertices());
		body = new ArrowPolygon(v2);
		
		//air resistance
		body.setBodyAngularDamping(10f);
		
		//same negative index to disable collisions between arrows
		body.setCollisionGroup(-1);
		body.enableCollisionListener();
		//60 degrees
		body.applyBodyForceToCenter(new Vector2(1000,3000), true);
		Map.add(this);
	}
	public static void setImgPath(String url) {
		// TODO Auto-generated method stub
		img = new BitmapImage(url);
	}
	@Override 
	public void draw(GdxGraphics g) {
		Vector2 v = body.getBodyLinearVelocity();
		double velocity = Math.sqrt(v.x*v.x + v.y*v.y);
		float lift = (float)( -Math.cos(body.getBodyAngle()+ Math.PI/3) * velocity);
		body.applyBodyTorque(lift, true);
		
		Vector2 pos = body.getBodyWorldCenter();
		g.drawFilledPolygon(body.getPolygon(),Color.CYAN);
		g.drawTransformedPicture(pos.x, pos.y, body.getBodyAngleDeg()+60, .3f, img);
		g.drawFilledCircle(body.getBodyWorldCenter().x, body.getBodyWorldCenter().y, 5, Color.GREEN);
		g.drawFilledCircle(body.getBodyWorldCenter().x, body.getBodyWorldCenter().y, 5, Color.GREEN);
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
	
}