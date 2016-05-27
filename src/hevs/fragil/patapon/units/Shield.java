package hevs.fragil.patapon.units;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.CurrentLevel;
import hevs.fragil.patapon.mechanics.Param;

public class Shield extends Unit {
	static double modLife = +1.0;
	static Skills modifier;	
	static SpriteSheet arms;
	
	public Shield(){
		this(1,Species.random(), Param.HEROES_GROUP);
	}
	public Shield(int lvl, Species species, int collisionGroup){
		super(lvl, species, 10, 10, 10, 100, 50, 500, collisionGroup);
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	public void attack(){
		
	}
	@Override
	public void draw(GdxGraphics g) {
		float time = CurrentLevel.getLevel().getStateTime();
		super.drawLegs(time);
		super.drawBody(time);
		super.drawEye();
		drawArms(time);
	}
	private void drawArms(float time){
//		arms.drawKeyFrames(time, super.position);
	}
}