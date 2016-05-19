package hevs.fragil.patapon.mechanics;

import java.util.Vector;

import com.badlogic.gdx.math.Interpolation;

import hevs.fragil.patapon.music.Note;
import hevs.fragil.patapon.units.Company;
import hevs.fragil.patapon.units.Section;
import hevs.fragil.patapon.units.Unit;

public abstract class ActionTimer{
	private static int step = 0;
	private static int totalDelay = 0;
	private static int waitIndex = 0;
	private static float deltaTime;
	
	private static float start, progression, end;
	
	private static Vector<Action> toRemove = new Vector<Action>();
	//TODO avoid double command at same time
	//for example 2 times walk command causes large bug of speed
	
	public static void run(float dt, Company c) {
		deltaTime = dt;
		for (Action a : c.getActions()) {
			switchAction(a, c);
		}
		for (Action a : toRemove) {
			c.remove(a);
		}
		toRemove.removeAllElements();
	}
	private static void switchAction(Action a, Company c){
		boolean finished = false;
		if(a != null){
			switch(a){
				case WALK : 	finished = walk(c);
								break;
				case ATTACK : 	finished = attack(c);
								break;
				case DEFEND : 	
								break;
				case MIRACLE : 	
								break;
				case RETREAT : 	finished = retreat(c);
								break;
				case CHARGE : 	
								break;
				default : 		
								break;
			}	
			if(finished)
				toRemove.addElement(a);
		}
	}
	private static boolean shift(float totalTime, int distance, Company c){
		if(progression == 0){
			start = c.globalPosition;
			end = start + distance;
			System.out.println("company will shift from/to : " + start + " / "+ end);
		}
		
		progression += deltaTime/totalTime;
		c.moveAbsolute( (int) Interpolation.fade.apply(start, end, progression), deltaTime);
		if(progression >= 1){
			progression = 0;
			return true;
		}
		else return false;
	}
	private static boolean wait(double time, Company c){
		if(waitIndex == 0){
			System.out.println("**Wait routine : wait " + time + " ms");
		}
		
		waitIndex += deltaTime;
		
		if(waitIndex >= time ){
			System.out.println("**->Wait routine finished");
			waitIndex = 0;
			return true ;
		}
		
		else return false ;
		
	}
	private static boolean walk(Company c){
		float time = Param.WALK_TIME;
		
		//add bonus time (faster move with fever)
		time -= Param.WALK_TIME_BONUS/100.0 * Note.getFever();
		
		return shift(time, Param.WALK_WIDTH, c);
	}
	private static boolean retreat(Company c){
		float time = Param.RETREAT_TIME;
		float bonus = (float) (Param.RETREAT_TIME_BONUS/100.0 * Note.getFever());
		if (step == 0) {
			if(shift(time/4-bonus, -Param.RETREAT_WIDTH, c))
				step++;
		}
		
		else if(step == 1){
			if(wait(time/2+bonus, c))
				step++;
		}
		
		else if (shift(time/4, Param.RETREAT_WIDTH, c)){
			step = 0;
			return true;
		}
		
		return false;
	}
	private static boolean attack(Company c){
		totalDelay += deltaTime;

		for (Section s : c.sections) {
			for (Unit u : s.units) {
					u.attack(deltaTime);
			}
		}
		
		//action ended
		if(totalDelay >= Param.ATTACK_TIME) {
			totalDelay = 0;
			return true;
		}
		return false;
	}
}
