package hevs.fragil.patapon.units;

import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.gdx2d.lib.GdxGraphics;

public class Shield extends Unit {
	static double modLife = +1.0;
	static Skills modAttack;	
	static Skills modDefense;	
	static SpriteSheet arms;
	
	public Shield(){
		this(1,1);
	}
	public Shield(int lvl, int species){
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