package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import hevs.fragil.patapon.others.Map;
import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.components.physics.PhysicsBox;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public class Spear implements DrawableObject {
	static BitmapImage spear ;
	PhysicsBox box;
	
	public Spear(Vector2 pos, Vector2 speed) {
		box = new PhysicsBox("arrow",pos,5,100);
		box.setBodyAngularDamping(0.4f);
		box.setBodyLinearDamping(0.2f);
		box.applyBodyForceToCenter(	(float) Math.cos(box.getBodyAngle())* 100, 
									(float) Math.sin(box.getBodyAngle())* 60, true);
		Map.add(this);
	}

	public static void setImgPath(String url) {
		// TODO Auto-generated method stub
		spear = new BitmapImage(url);
	}

	@Override
	public void draw(GdxGraphics g) {
		Vector2 pos = box.getBodyPosition();		
		g.drawTransformedPicture(pos.x, pos.y, box.getBodyAngleDeg()+90, .3f, spear);
	}
}