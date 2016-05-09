package hevs.fragil.patapon.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

public interface FlyingObject extends DrawableObject {
	public abstract Vector2 getSpike();
	public abstract Body getBody();
	public abstract void updatePhysics(GdxGraphics g);
}