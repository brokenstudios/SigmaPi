package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import hevs.gdx2d.lib.interfaces.DrawableObject;

public abstract class FlyingObject implements DrawableObject {
	public abstract Vector2 getSpike();
	public abstract Body getBody();
}
