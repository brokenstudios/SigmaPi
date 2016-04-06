package hevs.fragil.patapon.units;

import java.util.TimerTask;
import java.util.Vector;

import hevs.fragil.patapon.graphics.Map;
import hevs.fragil.patapon.music.Note;
import hevs.fragil.patapon.others.Param;

public class GameDynamic extends TimerTask{
	private static int walkIndex = 0;
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
				case ATTACK : 	System.out.println("Units are attacking");
								break;
				case DEFEND : 	System.out.println("Units are defending");
								break;
				case MIRACLE : 	System.out.println("Units are *miracling*");
								break;
				case RETREAT : 	System.out.println("Units are retreating");
								break;
				case CHARGE : 	System.out.println("Units are charging");
								break;
				default : 		System.out.println("I don't know what units are doing !");
								break;
			}	
			if(finished)
				toRemove.addElement(a);
		}
	}
	private static boolean walk(Company c){
		//add bonus time (faster move with fever)
		double time = Param.TIME_WALK;
		time -= Param.TIME_BONUS_WALK/100.0 * Note.getFeverCoefficient();
		double speed = Param.PIXELS_WALK / time ;
		//first time we get in
		if(walkIndex==0){
			System.out.println("Walk routine launched\nFever coefficient : "+ Note.getFeverCoefficient()+" \nSpeed : " + speed*1000 + " pixels/seconds");
			walkIndex = c.globalPosition + Param.PIXELS_WALK;
		}
		//time to distance : d = t*v
		int increment = (int)(Param.ACTION_PERIOD * speed);
		c.moveRelative(increment, false);
		//arrived at last position
		if(c.globalPosition >= walkIndex){
			walkIndex = 0;
			System.out.println("Walk routine finished");
			return true;
		}
		return false;
	}
}
