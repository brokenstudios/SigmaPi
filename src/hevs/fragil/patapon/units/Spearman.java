package hevs.fragil.patapon.units;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;

public class Spearman extends Unit {
	static double modLife = +0.2;
	static Skills modifier;	
	static SpriteSheet arms;
	
	public Spearman(){
		this(1,1);
	}
	public Spearman(int lvl, int species){
		super(lvl, species, 10, 10, 10, 100, 50, 500);
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	@Override
	public void draw(GdxGraphics g) {
		
	}
	public void attack(){
		
	}
	public void draw(GdxGraphics g, float time) {
		super.drawLegs(time);
		super.drawBody(time);
		super.drawEye();
		drawArms(time);
	}
	private void drawArms(float time){
//		arms.drawKeyFrames(time, super.position);
	}
}
