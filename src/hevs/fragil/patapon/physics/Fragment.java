package hevs.fragil.patapon.physics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

public class Fragment extends PhysicsBox implements DrawableObject {
	int w, h;
	float opacity = 1;
	
	public Fragment(int x, int y, int w, int h){
		super("brick",new Vector2(x,y), w, h, 10,0.1f,1f);
		this.w = w;
		this.h = h;
		this.setCollisionGroup(-4);
		Vector2 impulse = new Vector2(2.5f-(float)(Math.random()*5), (float)(Math.random()*3));
		this.setBodyAngularDamping(8);
		this.applyBodyLinearImpulse(impulse, getBodyWorldCenter(), true);
		
	}

	@Override
	public void draw(GdxGraphics g) {
		float x,y,angle;
		x = getBodyWorldCenter().x;
		y = getBodyWorldCenter().y;
		angle = getBodyAngleDeg();
		g.drawFilledRectangle(x, y, w, h, angle, Color.WHITE);
		g.setColor(Color.BLACK);
		g.drawRectangle(x, y, w, h, angle);
	}
	
	public boolean step(){
		opacity -=0.01f;
		
		if(opacity <= 0){
			return true;
		}
		return false;
	}
	
}
