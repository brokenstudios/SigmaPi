package hevs.fragil.patapon.physics;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;

public class Arrow extends Projectile{
	// for every arrow
	private static BitmapImage img;
	private static float[] v1 = { -5, 60, -4, 70, 0, 80, 4, 70, 5, 60, 0, 0 };

	public Arrow(Vector2 startPos, int startAngle, int distance, int collisionGroup, int damage) {
		super(startPos,startAngle,collisionGroup,distance,damage,getArrowVertices(startAngle),"arrow");
	}

	public Vector2 getSpike() {
		Vector2 temp = getBodyWorldCenter();
		double angle = getBodyAngle() + startAngle;
		temp.add((float) (Math.cos(angle) * 28), (float) (Math.sin(angle) * 28));
		return temp;
	}

	private static Vector2[] getArrowVertices(int angle) {
		Polygon poly = new Polygon(v1);
		poly.setOrigin(0, 40);
		poly.rotate(angle - 90);
		return verticesToVector2(poly.getTransformedVertices());
	}

	@Override
	public void draw(GdxGraphics g) {
		float angleDegrees = getBodyAngleDeg() + startAngle;
		double angleRadians = Math.toRadians(angleDegrees);
		// better penetration depending of the impact angle
		int distance = 8 + (int) (5 * Math.cos(angleRadians));
		Vector2 offset = new Vector2((float) Math.cos(angleRadians) * distance,
				(float) Math.sin(angleRadians) * distance);

		Vector2 pos = getBodyWorldCenter();
		pos = pos.add(offset);
		g.drawAlphaPicture(pos.x, pos.y, angleDegrees, .35f, life, img);
	}

	public static void setImgPath(String url) {
		img = new BitmapImage(url);
	}
}