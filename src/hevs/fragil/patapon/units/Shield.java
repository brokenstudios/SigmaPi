package hevs.fragil.patapon.units;

import java.util.Vector;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.CurrentLevel;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.mechanics.PlayerCompany;

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
	private void drawArms(float time){
//		arms.drawKeyFrames(time, super.position);
	}
	@Override
	public void draw(GdxGraphics g) {
		float time = CurrentLevel.getLevel().getStateTime();
		super.draw(time);
		drawArms(time);
	}
	@Override
	public void attack(){
		//TODO appliquer les dégats dans la zone correspondante à skills
		//créer un objet "impact"
	}
}