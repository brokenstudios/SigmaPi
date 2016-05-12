package hevs.fragil.patapon.units;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.ArrowPolygon;

public class Archer extends Unit {
	//Skills modifiers
	static double modLife = -0.2;
	static Skills modifier;
	static SpriteSheet arms;
	boolean firstshot = true;
	static int nArchers = 0;
	
	public Archer(){
		this(1,Species.random());
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
		fire();
	}
	public void draw(GdxGraphics g, float time) {
		super.drawLegs(time);
		super.drawBody(time);
		
		super.drawEye();
		drawArms(time);
	}
	private void drawArms(float time){
		
	}
	private void fire(){
		new ArrowPolygon( new Vector2(getPosition(), Param.FLOOR_DEPTH+30), 60, 400, -1);
	}
}
