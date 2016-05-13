package hevs.fragil.patapon.mechanics;

import java.util.Random;
import java.util.Vector;

import hevs.fragil.patapon.music.Note;
import hevs.fragil.patapon.units.Company;
import hevs.fragil.patapon.units.Section;
import hevs.fragil.patapon.units.Unit;

public class ActionTimer{
	private static int shiftDestination = 0;
	private static int shiftIncrement = 0;
	private static int attackStep = 0;
	private static int attackDelay = 0;
	private static int waitIndex = 0;
	
	private static Vector<Action> toRemove = new Vector<Action>();
	//TODO avoid double command at same time
	//for example 2 times walk command causes large bug of speed
	
	public static void run(float time, Company c) {
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
	private static boolean shift(double time, int shift, Company c){
		int minLeftOffset = c.getWidth()/2 +10 ;
		double speed = shift / time ;
		//first time we get in
		if(shiftDestination == 0){
			shiftDestination = c.globalPosition + shift;
			System.out.println("**Shift routine : " + shift + " pixels to " + shiftDestination);
		}
		//time to distance : d = t*v 
		int increment = (int)(Param.ACTIONS_BAR * speed);
		c.moveRelative(increment);
		//arrived at last position
		if((c.globalPosition >= shiftDestination) && shift > 0 			//right limit
				||(c.globalPosition <= shiftDestination) && shift < 0 	//left limit
				|| c.globalPosition == minLeftOffset){						//window limit
			shiftDestination = 0;
			System.out.println("**->Shift routine finished");
			return true;
		}
		return false;
	}
	private static boolean wait(double time, Company c){
		if(waitIndex == 0){
			System.out.println("**Wait routine : wait " + time + " ms");
		}
		waitIndex += Param.ACTIONS_BAR;
		if(waitIndex >= time ){
			System.out.println("**->Wait routine finished");
			waitIndex = 0;
			return true ;
		}
		else return false ;
		
	}
	private static boolean walk(Company c){
		double time = Param.WALK_TIME;
		//add bonus time (faster move with fever)
		time -= Param.WALK_TIME_BONUS/100.0 * Note.getFeverCoefficient();
		return shift(time, Param.WALK_WIDTH, c);
	}
	private static boolean retreat(Company c){
		double time = Param.RETREAT_TIME;
		double bonus = Param.RETREAT_TIME_BONUS/100.0 * Note.getFeverCoefficient();
		if (shiftIncrement == 0) {
			if(shift(time/4-bonus, -Param.RETREAT_WIDTH, c))
				shiftIncrement++;
		}
		else if(shiftIncrement == 1){
			if(wait(time/2.0, c))
				shiftIncrement++;
		}
		else if (shift(1*time/4, Param.RETREAT_WIDTH, c)){
			shiftIncrement = 0;
			return true;
		}
		return false;
	}
	private static boolean attack(Company c){
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
