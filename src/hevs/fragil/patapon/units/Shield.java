package hevs.fragil.patapon.units;

import com.badlogic.gdx.graphics.Color;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;

public class Shield extends Unit {
	static double modLife = +1.0;
	static Skills modifier;	
	static SpriteSheet arms;
	
	public Shield(){
		this(1,Species.random(), false);
	}
	public Shield(int lvl, Species species, boolean isEnnemi){
		super(lvl, species, 10, 10, 10, 100, 0, 50, 0.5f, isEnnemi);
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	@Override
	public void draw(GdxGraphics g) {
		super.draw(g);
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
	protected float getAttackDelay() {
		return 0.6f;
	}
	@Override
	protected Color getColor() {
		return Color.YELLOW;
	}
	@Override
	protected String getUrl() {
		return "data/images/arms_swords.png";
	}
}