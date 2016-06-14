package hevs.fragil.patapon.units;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

public class Box extends PhysicsBox implements DrawableObject {

	public Box(Vector2 position, float width, float height) {
		super("box", position, 100, 50, 10, 0.5f, 0.8f);
	}

	@Override
	public void draw(GdxGraphics g) {
		g.drawFilledRectangle(getBodyWorldCenter().x, getBodyWorldCenter().y, 100, 50, 0);
	}

}
