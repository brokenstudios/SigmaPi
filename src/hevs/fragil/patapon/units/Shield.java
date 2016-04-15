package hevs.fragil.patapon.units;

import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.gdx2d.lib.GdxGraphics;

public class Shield extends Unit {
	static double modLife = +1.0;
	static FightFactor modAttack;	
	static FightFactor modDefense;	
	static SpriteSheet arms;
	
	public Shield(){
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