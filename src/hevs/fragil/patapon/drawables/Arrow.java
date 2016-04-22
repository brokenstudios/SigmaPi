package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

import hevs.fragil.patapon.others.Map;
import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.physics.PhysicsBox;
import hevs.gdx2d.components.physics.PhysicsCircle;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public class Arrow implements DrawableObject {
	static BitmapImage arrow ;
	static Vector2 dimensions =  new Vector2(3,80);
	PhysicsBox stick;
	PhysicsCircle ball;
	
	public Arrow(Vector2 pos, Vector2 speed) {
		//TODO joint doesn't work..
		stick = new PhysicsBox("arrow", pos, 5, 80, -1f);
		Vector2 joint = new Vector2(dimensions.x/2f + pos.x, dimensions.y/9f + pos.y);
		
		ball = new PhysicsCircle("circle", joint , 5, 10, 0f, 0f);
		
		WeldJointDef jointDef = new WeldJointDef();
		jointDef.initialize(stick.getBody(), ball.getBody(), joint);
		
		stick.setBodyAngularDamping(3);
		stick.applyBodyForceToCenter(30, 30, true);
		Map.add(this);
	}

	public static void setImgPath(String url) {
		// TODO Auto-generated method stub
		arrow = new BitmapImage(url);
	}

	@Override
	public void draw(GdxGraphics g) {
		Vector2 pos = stick.getBodyPosition();
		Vector2 pos2 = ball.getBodyPosition();
		g.drawFilledRectangle(pos.x, pos.y, dimensions.x, dimensions.y, stick.getBodyAngleDeg(),Color.CYAN);
		g.drawTransformedPicture(pos.x, pos.y, stick.getBodyAngleDeg()+90, .3f, arrow);
		g.drawFilledCircle(pos2.x, pos2.y, 5, Color.BLUE);
	}
}