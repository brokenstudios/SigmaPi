package hevs.fragil.patapon.units;

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
		attackAnimation(ArmsLine.SHIELD);
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
	protected float findBestPosition() {
//		// Define victims according to the attacker
//		Company victims;
//		if(isEnnemi){
//			victims = PlayerCompany.getInstance().getHeroes();
//		}else{
//			victims = CurrentLevel.getLevel().getEnnemies(); 
//		}
//		
//		float distance = Math.abs(getPosition().x - victims.getPosition());
//		
//		// Enemies always attack (when in range)
//		if(isEnnemi){
//			if(distance < Param.SIGHT){
//				// If enemies company is in range, return actual position (so no move)
//				if(distance <= getSkills().getRangeMax() && distance >= getSkills().getRangeMin()){
//					return getPosition().x;			
//				}
//				// Else if enemies too far, move forward 
//				else if(distance >= getSkills().getRangeMax()){
//					return getPosition().x + distance - getSkills().getRange();
//				}
//				// Else if enemies are too close, retreat a little
//				else if(distance <= getSkills().getRangeMin()){
//					return getPosition().x - distance + getSkills().getRange();
//				}
//				else{
//					return getPosition().x;
//				}
//			}
//			else{
//				return getPosition().x;
//			}	
//		}
//		// Heroes must wait for player action
//		else{
//			if(distance < Param.SIGHT){
//				// If enemies company is in range, return actual position (so no move)
//				if(distance <= getSkills().getRangeMax() && distance >= getSkills().getRangeMin()){
//					return getPosition().x;			
//				}
//				// Else if enemies too far, move forward 
//				else if(distance >= getSkills().getRangeMax()){
//					return getPosition().x + distance - getSkills().getRange();
//				}
//				// Else if enemies are too close, retreat a little
//				else if(distance <= getSkills().getRangeMin()){
//					return getPosition().x - distance + getSkills().getRange();
//				}
//				else{
//					return getPosition().x;
//				}
//			}
//			else{
//				return getPosition().x;
//			}	
//		}
		return getPosition().x;
	}	
}