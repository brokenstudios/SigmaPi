package hevs.fragil.patapon.music;

import java.util.Arrays;
import java.util.Vector;

public class Sequence {
	//actual sequence
	private Vector<Note> notes = new Vector<Note>();
	//combo references
	private static final Drum[] 	WALK = {Drum.HE, Drum.HE, Drum.HE, Drum.S};
	private static final Drum[] 	ATTACK = {Drum.S, Drum.S, Drum.HE, Drum.S};
	private static final Drum[] 	DEFEND = {Drum.SO, Drum.SO, Drum.HE, Drum.S};
	private static final Drum[] 	MIRACLE = {Drum.YES, Drum.YES, Drum.YES, Drum.YES, Drum.YES};
	private static final Drum[] 	RETREAT = {Drum.S, Drum.HE, Drum.S, Drum.HE};
	private static final Drum[] 	CHARGE = {Drum.S, Drum.S, Drum.SO, Drum.SO};
	private static final Drum[][]	COMBOS = {WALK,ATTACK,DEFEND,MIRACLE,RETREAT,CHARGE};
	
	public Sequence(){
		
	}
	public void add(Note n){
		notes.add(n);
		int index;
		index = recognize();
		if(index != -1)
			System.out.println("Combo n° " + index);
	}
	public void remove(Note n){
		notes.remove(n);
	}
	public void removeAllElements(){
		notes.removeAllElements();
	}
	/**
	 * @author loicg
	 * @returns the index of the table in the COMBOS static table
	 * */
	private int recognize(){
		//combo only possible when length >= 4 
		if(notes.size() >= 4){
			//compare the last 6 ones
			int startIndex = Math.max(notes.size()-5, 0);
			int lastIndex = Math.min(5, notes.size());
			Drum[] lastNotes = new Drum[lastIndex];
			Drum[] tab4 = new Drum[4];
			for(int i = 0 ; i < lastIndex ; i++){
				lastNotes[i] = notes.elementAt(i + startIndex).drum;
			}
			if(lastNotes.length >= 5){
				tab4 = Arrays.copyOfRange(lastNotes,lastNotes.length-4, lastNotes.length);
			}
			for(int i = 0; i < COMBOS.length; i++){
				if(Arrays.equals(lastNotes,COMBOS[i]) || Arrays.equals(tab4,COMBOS[i])){
					//now these elements are useless
					notes.removeAllElements();
					return i ;				}
			}
			return -1;
		}
		else return -1;
	}
}
