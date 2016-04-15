package hevs.fragil.patapon.units;

import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.gdx2d.lib.GdxGraphics;

public class Archer extends Unit {
	static double modLife = -0.2;
	static FightFactor modAttack;
	static FightFactor modDefense;	
	static SpriteSheet arms;
	
	public Archer(){
		super();
		super.setLife(super.life*(1.0+modLife));	
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	@Override
	public void draw(GdxGraphics g){}
	public void attack(){
		
	}
	public void draw(GdxGraphics g, float time) {
		super.drawLegs(time);
		super.drawBody();
	}
}
