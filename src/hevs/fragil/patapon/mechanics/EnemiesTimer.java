package hevs.fragil.patapon.mechanics;

import com.badlogic.gdx.Gdx;

import hevs.fragil.patapon.units.Company;
import hevs.fragil.patapon.units.Section;
import hevs.fragil.patapon.units.State;
import hevs.fragil.patapon.units.Unit;

/**
 * This class manages the enemies movements. Identical to SequenceTimer class, 
 * with less options.
 */
public abstract class EnemiesTimer{
	private static float deltaTime;
	private static float progression;
	
	public static void run(Company c) {
		float dt = Gdx.graphics.getRawDeltaTime();
		deltaTime = dt;
		switchAction(c.getAction(), c);
	}
	private static void switchAction(State a, Company c){
		// process moves
		c.aiMove();
		attack(c);
	}
	private static boolean attack(Company c){
		progression += deltaTime;
		
		// Enable automatic unit placement
		c.freeUnits();
		
		for (Section s : c.sections) {
			for (Unit u : s.units) {
				if(!u.getUnitsInRange().isEmpty()){
					u.attackRoutine();
				}
			}
		}
		
		//action ended
		if(progression >= Param.ATTACK_TIME) {
			for (Section s : c.sections) {
				for (Unit u : s.units) {
					u.resetGesture();
				}
			}
			progression = 0f;
			return true;
		}
		return false;
	}
}
