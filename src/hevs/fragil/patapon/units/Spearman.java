package hevs.fragil.patapon.units;

import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.gdx2d.lib.GdxGraphics;

public class Spearman extends Unit {
	static double modLife = +0.2;
	static FightFactor modAttack;	
	static FightFactor modDefense;	
	static SpriteSheet arms;
	
	public Spearman(){
		super();
		super.setLife(super.life*(1.0+modLife));
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	@Override
	public void draw(GdxGraphics g) {
		
	}
	public void attack(){
		
	}
	//Just for onInit method
	public void draw(GdxGraphics g, float time) {
		super.legs.draw(time, super.position);
	}
}
