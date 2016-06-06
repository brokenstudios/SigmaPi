package hevs.fragil.patapon.units;

import com.badlogic.gdx.math.Vector2;

import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.Arrow;

public class Archer extends Unit {
	//Skills modifiers
	static double modLife = -0.2;
	static Skills modifier;
	static int nArchers = 0;
	
	public Archer(){
		this((int)(4*Math.random()),Species.random(), false);
	}
	public Archer(int lvl, Species species, boolean isEnnemi){
		super(lvl, species, 10, 10, 10, 100, 500, 1000, .5f, isEnnemi);
		nArchers++;
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	public void attack(int distance){
		Vector2 position = new Vector2(getPosition().x, Param.FLOOR_DEPTH+30);
		new Arrow(position, (int)(Math.random()*20) + 45 , distance, collisionGroup, skills.getLevel() + 5);
	}
	@Override
	public void attack() {
		attack(skills.getRangeMax());
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
	@Override
	public Gesture getAttackAnimation() {
		return Gesture.ARCHER;
	}
	@Override
	protected float getPreAnimationDelay() {
		return 0.8f;
	}
}
