package hevs.fragil.patapon.units;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.Arrow;

public class Archer extends Unit {
	//Skills modifiers
	static double modLife = -0.2;
	static Skills modifier;
	static SpriteSheet arms;
	static int nArchers = 0;
	
	public Archer(){
		this((int)(4*Math.random()),Species.random());
	}
	public Archer(int lvl, Species species){
		super(lvl, species, 10, 10, 10, 100, 50, 500);
		nArchers++;
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	@Override
	public void draw(GdxGraphics g){}
	public void attack(){
		Vector2 position = new Vector2(getPosition(), Param.FLOOR_DEPTH+30);
		new Arrow(position, 60, 400, -1);
	}
	public void draw(GdxGraphics g, float time) {
		super.drawLegs(time);
		super.drawBody(time);
		
		super.drawEye();
		drawArms(time);
	}
	private void drawArms(float time){
		
	}
}
