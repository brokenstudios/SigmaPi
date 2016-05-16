package hevs.fragil.patapon.mechanics;

import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.math.Interpolation;

import hevs.fragil.patapon.music.Note;
import hevs.fragil.patapon.units.Company;
import hevs.fragil.patapon.units.Section;
import hevs.fragil.patapon.units.Unit;

public abstract class ActionTimer{
	private static int shiftDestination = 0;
	private static int shiftIncrement = 0;
	private static int attackStep = 0;
	private static int attackDelay = 0;
	private static int waitIndex = 0;
	private static float deltaTime;
	
	private static float start, progression, end;
	
	private static Vector<Action> toRemove = new Vector<Action>();
	//TODO avoid double command at same time
	//for example 2 times walk command causes large bug of speed
	
	public static void run(float dt, Company c) {
		deltaTime = dt;
		for (Action a : c.getActions()) {
			switchAction(a, dt, c);
		}
		for (Action a : toRemove) {
			c.remove(a);
		}
		toRemove.removeAllElements();
	}
	private static void switchAction(Action a, float dt, Company c){
		boolean finished = false;
		if(a != null){
			switch(a){
				case WALK : 	finished = walk(dt, c);
								break;
				case ATTACK : 	finished = attack(dt, c);
								break;
				case DEFEND : 	
								break;
				case MIRACLE : 	
								break;
				case RETREAT : 	finished = retreat(dt, c);
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
	private static boolean shift(float dt, float totalTime, int distance, Company c){
		/*
		int minLeftOffset = c.getWidth()/2 +10 ;
		double speed = distance / time ;
		
		//first time we get in
		if(shiftDestination == 0){
			//process where to go
			shiftDestination = c.globalPosition + distance;
			System.out.println("**Shift routine : " + distance + " pixels to " + shiftDestination);
		}
		
		//time to distance : d = t*v 
		int increment = (int)(deltaTime * speed);
		c.moveRelative(increment);
	
		//arrived at last position
		if((c.globalPosition >= shiftDestination) && distance > 0 				//right limit
				||(c.globalPosition <= shiftDestination) && distance < 0 		//left limit
				|| c.globalPosition == minLeftOffset){						//window limit
			
			shiftDestination = 0;
			System.out.println("**->Shift routine finished");
			return true;
		}
		return false;
		*/
		if(progression == 0){
			start = c.globalPosition;
			end = start + distance;
			System.out.println("company will shift from/to : " + start + " / "+ end);
		}
		progression += dt/totalTime;
		c.moveAbsolute( (int) Interpolation.linear.apply(start, end, progression), totalTime);
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
	private static boolean walk(float dt, Company c){
		float time = Param.WALK_TIME;
		
		//add bonus time (faster move with fever)
		time -= Param.WALK_TIME_BONUS/100.0 * Note.getFeverCoefficient();
		
		return shift(dt, time, Param.WALK_WIDTH, c);
	}
	private static boolean retreat(float dt, Company c){
		//FIXME WTF IS HAPPENING ?
		float time = Param.RETREAT_TIME;
		float bonus = (float) (Param.RETREAT_TIME_BONUS/100.0 * Note.getFeverCoefficient());
		
		if (shiftIncrement == 0) {
			if(shift(dt, time/4, -Param.RETREAT_WIDTH, c))
				shiftIncrement++;
		}
		
		else if(shiftIncrement == 1){
			if(wait(time/2, c))
				shiftIncrement++;
		}
		
		else if (shift(dt, time/4, Param.RETREAT_WIDTH, c)){
			shiftIncrement = 0;
			return true;
		}
		
		return false;
	}
	private static boolean attack(float dt, Company c){
		long seed = (long) (Math.random()*1000);
		//TODO use this random
		Random r = new Random(seed);
		attackDelay ++;

		//apply delays
		if (attackStep == 0) {
			if(wait(300 + Math.random() * 100, c)){
				for (Section s : c.sections) {
					for (Unit u : s.units) {
						if(u.getDelay() < attackDelay)
							u.attack();
					}
				}
				attackStep++;
			}
		}
		else if(attackStep == 1){
			if(wait(300 + Math.random() * 200, c)){
				c.attack();
				attackStep++;
			}
		}
		else {
			attackStep = 0;
			return true;
		}
		return false;
	}
}
