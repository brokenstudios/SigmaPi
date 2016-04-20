package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;

public class Spear extends PointedObject {
	protected static BitmapImage spear;
	
	public Spear(Vector2 pos, Vector2 speed) {
		super(pos, speed);
	}

	public void setImgPath(String url) {
		// TODO Auto-generated method stub
		spear = new BitmapImage(url);
	}

	@Override
	public void draw(GdxGraphics g) {
		Vector2 pos = box.getBodyPosition();
		g.drawTransformedPicture(pos.x, pos.y, box.getBodyAngleDeg()+90, .3f, spear);
		g.drawFilledPolygon(box.getPolygon(),Color.YELLOW);
	}

}
