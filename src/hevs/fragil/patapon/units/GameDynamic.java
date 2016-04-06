package hevs.fragil.patapon.units;

import java.util.TimerTask;
import java.util.Vector;

import hevs.fragil.patapon.graphics.Map;
import hevs.fragil.patapon.music.Note;
import hevs.fragil.patapon.others.Param;

public class GameDynamic extends TimerTask{
	private static int shiftDestination = 0;
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
		int direction = (int)(shift/Math.abs(shift));
		int leftLimit = c.getWidth()/2 +10 ;
		double speed = Param.WALK_WIDTH / time * direction ;
		//first time we get in
		if(shiftDestination == 0){
			shiftDestination = c.globalPosition + shift;
			System.out.println("**Shift routine : " + shift + " pixels to " + shiftDestination);
		}
		//time to distance : d = t*v
		int increment = (int)(Param.ACTIONS_BAR * speed);
		c.moveRelative(increment);
		//arrived at last position
		if(c.globalPosition == shiftDestination || c.globalPosition == leftLimit){
			shiftDestination = 0;
			System.out.println("**->Shift routine finished");
			return true;
		}
		return false;
	}
	private static boolean walk(Company c){
		double time = Param.WALK_TIME;
		time -= Param.WALK_TIME_BONUS/100.0 * Note.getFeverCoefficient();
		return shift(time, Param.WALK_WIDTH, c);
	}
	private static boolean retreat(Company c){
		double time = Param.RETREAT_TIME;
		time -= Param.RETREAT_TIME_BONUS/100.0 * Note.getFeverCoefficient();
		boolean firstShiftEnded = false;
		//FIXME Does not work correctly, little bug here i suppose !
		if (!firstShiftEnded) {
			firstShiftEnded = shift(time/2, -Param.RETREAT_WIDTH, c);
			return false;
		}
		else return shift(time/2, Param.RETREAT_WIDTH, c);
	}
}
