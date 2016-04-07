package hevs.fragil.patapon.units;

import java.util.TimerTask;
import java.util.Vector;

import hevs.fragil.patapon.graphics.Map;
import hevs.fragil.patapon.music.Note;
import hevs.fragil.patapon.others.Param;

public class GameDynamic extends TimerTask{
	private static int shiftDestination = 0;
	private static int shiftIncrement = 0;
	private static int waitIndex = 0;
	private static Vector<Action> toRemove = new Vector<Action>();
	
	@Override
	public void run() {
		for (Company c : Map.getCompanies()) {
			for (Action a : c.getActions()) {
				switchAction(a, c);
			}
			for (Action a : toRemove) {
				c.remove(a);
			}
			toRemove.removeAllElements();
		}
	}
	private static void switchAction(Action a, Company c){
		boolean finished = false;
		if(a != null){
			switch(a){
				case WALK : 	finished = walk(c);
								break;
				case ATTACK : 	
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
		//add bonus time (faster move with fever)
		int leftLimit = c.getWidth()/2 +10 ;
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
		if((c.globalPosition >= shiftDestination)&& shift > 0
				||(c.globalPosition <= shiftDestination) && shift <0 
				|| c.globalPosition == leftLimit){
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
		System.out.println(time + " " + waitIndex);
		if(waitIndex >= time ){
			waitIndex = 0;
			return true ;
		}
		else return false ;
		
	}
	private static boolean walk(Company c){
		double time = Param.WALK_TIME;
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
}
