package hevs.fragil.patapon.music;

import java.util.Arrays;
import java.util.Vector;

import hevs.fragil.patapon.units.Action;

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
		int index;
		index = recognize();
		if(index != -1){
			return Action.values()[index];
		}
		else return null;
	}
	public static void remove(Note n){
		melody.remove(n);
	}
	public static void removeAllElements(){
		melody.removeAllElements();
	}
	/**
	 * @author loicg
	 * @returns the index of the table in the COMBOS static table
	 * */
	private static int recognize(){
		//combo only possible when length >= 4 
		if(melody.size() >= 4){
			//compare the last 6 ones
			int startIndex = Math.max(melody.size()-5, 0);
			int lastIndex = Math.min(5, melody.size());
			Drum[] lastNotes = new Drum[lastIndex];
			Drum[] tab4 = new Drum[4];
			for(int i = 0 ; i < lastIndex ; i++){
				lastNotes[i] = melody.elementAt(i + startIndex).drum;
			}
			if(lastNotes.length >= 5){
				tab4 = Arrays.copyOfRange(lastNotes,lastNotes.length-4, lastNotes.length);
			}
			for(int i = 0; i < COMBOS.length; i++){
				if(Arrays.equals(lastNotes,COMBOS[i]) || Arrays.equals(tab4,COMBOS[i])){
					//now these elements are useless
					melody.removeAllElements();
					return i ;				}
			}
			return -1;
		}
		else return -1;
	}

}
