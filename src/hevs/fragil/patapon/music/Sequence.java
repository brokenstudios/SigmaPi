package hevs.fragil.patapon.music;

import java.util.Arrays;
import java.util.Vector;

import hevs.fragil.patapon.mechanics.Action;

public abstract class Sequence {
	//actual sequence
	private static Vector<Note> melody = new Vector<Note>();
	
	//combo references
	private static final Drum[] 	WALK = {Drum.HE, Drum.HE, Drum.HE, Drum.S};
	private static final Drum[] 	ATTACK = {Drum.S, Drum.S, Drum.HE, Drum.S};
	private static final Drum[] 	DEFEND = {Drum.SO, Drum.SO, Drum.HE, Drum.S};
	private static final Drum[] 	MIRACLE = {Drum.YES, Drum.YES, Drum.YES, Drum.YES, Drum.YES};
	private static final Drum[] 	RETREAT = {Drum.S, Drum.HE, Drum.S, Drum.HE};
	private static final Drum[] 	CHARGE = {Drum.S, Drum.S, Drum.SO, Drum.SO};
	private static final Drum[][]	COMBOS = {WALK,ATTACK,DEFEND,MIRACLE,RETREAT,CHARGE};

	public static Action add(Note n){
		melody.add(n);
		Action a = getAction();
		return a;
	}
	public static void remove(Note n){
		melody.remove(n);
	}
	public static void removeAllElements(){
		melody.removeAllElements();
	}
	/**
	 * @author loicg
	 * @return the corresponding action
	 * */
	private static Action getAction(){
		if(melody.size() >= 4){
			//compare the last 5 ones
			int startIndex = Math.max(melody.size()-5, 0);
			int lastIndex = Math.min(5, melody.size());
			Drum[] lastNotes = new Drum[lastIndex];
			Drum[] tab4 = new Drum[4];
			
			//get the last 4 or 5 notes in an array
			for(int i = 0 ; i < lastIndex ; i++){
				lastNotes[i] = melody.elementAt(i + startIndex).drum;
			}
			
			//check if we need another array of 4 elements (equals function)
			//when checking for the 5 and the 4 last notes
			if(lastNotes.length >= 5){
				tab4 = Arrays.copyOfRange(lastNotes,lastNotes.length-4, lastNotes.length);
			}
			
			//go through all possible actions and compare the current sequence to them
			//when a match is found, return the corresponding action
			for(int i = 0; i < COMBOS.length; i++){
				if(Arrays.equals(lastNotes,COMBOS[i]) || Arrays.equals(tab4,COMBOS[i])){
					//now these elements are useless
					melody.removeAllElements();
					System.out.println("Sequence " + Action.values()[i] + " recognized !");
					return Action.values()[i];				
				}
			}
			
			//indicates bad sequence
			Note.clearFever();
			System.out.println("No possible sequence found... Fever goes down !");
			return Action.STOP;
		}
		
		//indicate sequence not terminated
		else return null;
	}
}
