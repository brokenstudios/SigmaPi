package hevs.fragil.patapon.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.gdx2d.components.graphics.GeomUtils;
import hevs.gdx2d.components.physics.PhysicsPolygon;
import hevs.gdx2d.lib.GdxGraphics;

public class Archer extends Unit {
	static double modLife = -0.2;
	static Skills modAttack;
	static Skills modDefense;	
	static SpriteSheet arms;
	PhysicsPolygon arrow = null;
	public Archer(){
		this(1,1);
	}
	public Archer(int lvl, int species){
		super(lvl, species, 10, 10, 10, 100, 50, 500);
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	@Override
	public void draw(GdxGraphics g){}
	public void attack(){
		fire();
	}
	public void draw(GdxGraphics g, float time) {
		super.drawLegs(time);
		super.drawBody(time);
		super.drawEye();
		drawArms(time);
		if(arrow != null)
			g.drawFilledPolygon(arrow.getPolygon(), Color.BLACK);
	}
	private void drawArms(float time){
		
	}
	private void fire(){
		if(arrow != null)
			arrow.destroy();
		Vector2[] p ={
				new Vector2(0,0),  
				new Vector2(0,50),
				new Vector2(5,50),
				new Vector2(5,0)
				};
		
		GeomUtils.translate(p, new Vector2(super.position, 60));
		GeomUtils.rotate(p,-20);
		arrow = new PhysicsPolygon("arrow", p ,true);
		arrow.setBodyLinearVelocity(2, 10);
	}
}
