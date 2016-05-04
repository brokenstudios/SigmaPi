package hevs.fragil.patapon.others;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

public class Floor extends PhysicsStaticBox implements DrawableObject{
	float width;
	Vector2 pos;
	
	public Floor(float width) {
		super("floor", new Vector2(width/2,Param.FLOOR_DEPTH/2), width, Param.FLOOR_DEPTH);
		this.width = width;
		pos = new Vector2(width/2,Param.FLOOR_DEPTH/2);
	}

	@Override
	public void draw(GdxGraphics g) {
		g.drawFilledRectangle(pos.x, pos.y, width, Param.FLOOR_DEPTH, 0f, Color.BLACK);
	}

}
