package hevs.fragil.patapon.units;

import com.badlogic.gdx.math.Vector2;

import hevs.fragil.patapon.drawables.Arrow;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.others.Param;
import hevs.gdx2d.lib.GdxGraphics;

public class Archer extends Unit {
	static double modLife = -0.2;
	static Skills modAttack;
	static Skills modDefense;	
	static SpriteSheet arms;
	boolean firstshot = true;
	static int nArchers = 0;
	
	public Archer(){
		this(1,1);
	}
	public Archer(int lvl, int species){
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
		new Arrow( new Vector2(getPosition(), Param.FLOOR_DEPTH+30),60, 400);
	}
}
