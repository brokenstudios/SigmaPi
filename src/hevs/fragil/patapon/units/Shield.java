package hevs.fragil.patapon.units;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.CurrentLevel;

public class Shield extends Unit {
	static double modLife = +1.0;
	static Skills modifier;	
	static SpriteSheet arms;
	
	public Shield(){
		this(1,Species.random(), false);
	}
	public Shield(int lvl, Species species, boolean isEnnemi){
		super(lvl, species, 10, 10, 10, 100, 50, 500, isEnnemi);
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
		for (Unit u : getUnitsInRange()) {
			if(u.isFatal(skills.getAttack())){
				//this will be fatal !
				u.receive(skills.getAttack());
			}
			else{
				u.receive(skills.getAttack());
				u.applyImpulse(4000);
			}
		}
	}
	@Override
	protected int findBestPosition() {
		// TODO find the best position to shoot ennemies
		return getPosition();
	}
	
}