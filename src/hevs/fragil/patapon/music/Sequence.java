package hevs.fragil.patapon.music;

import java.util.Arrays;
import java.util.Vector;

import com.badlogic.gdx.Gdx;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.mechanics.Action;
import hevs.fragil.patapon.mechanics.Param;

public class Sequence implements DrawableObject {
	//actual sequence
	private Vector<Note> melody = new Vector<Note>();
	private Vector<Note> toDraw = new Vector<Note>();
	private boolean playing = false;
	private float drawTime;
	
	//combo references
	private static final Drum[] 	WALK = {Drum.HE, Drum.HE, Drum.HE, Drum.S};
	private static final Drum[] 	ATTACK = {Drum.S, Drum.S, Drum.HE, Drum.S};
	private static final Drum[] 	DEFEND = {Drum.SO, Drum.SO, Drum.HE, Drum.S};
	private static final Drum[] 	MIRACLE = {Drum.YES, Drum.YES, Drum.YES, Drum.YES, Drum.YES};
	private static final Drum[] 	RETREAT = {Drum.S, Drum.HE, Drum.S, Drum.HE};
	private static final Drum[] 	CHARGE = {Drum.S, Drum.S, Drum.SO, Drum.SO};
	private static final Drum[][]	COMBOS = {WALK,ATTACK,DEFEND,MIRACLE,RETREAT,CHARGE};

	public Action add(Note n){
		if(n.outOfRythm){
			return Action.STOP;
		}
		else {
			melody.add(n);
		}
		toDraw.add(n);
		Action a = getAction();
		return a;
	}
	/**
	 * @author loicg
	 * @return the corresponding action
	 * */
	private Action getAction(){
		if(melody.size() >= 4){
			//compare the last 5 ones
			int startIndex = Math.max(melody.size()-5, 0);
			int lastIndex = Math.min(5, melody.size());
			Drum[] last5Notes = new Drum[lastIndex];
			Drum[] last4Notes = new Drum[4];
			
			//get the last 4 or 5 notes in an array
			for(int i = 0 ; i < lastIndex ; i++){
				last5Notes[i] = melody.elementAt(i + startIndex).getDrum();
			}
			
			//check if we need another array of 4 elements (equals function)
			//when checking for the 5 and the 4 last notes
			if(last5Notes.length >= 5){
				last4Notes = Arrays.copyOfRange(last5Notes,last5Notes.length-4, last5Notes.length);
			}
			
			//go through all possible actions and compare the current sequence to them
			//when a match is found, return the corresponding action
			for(int i = 0; i < COMBOS.length; i++){
				if(Arrays.equals(last5Notes,COMBOS[i]) || Arrays.equals(last4Notes,Arrays.copyOfRange(COMBOS[i],0,3))){
					System.out.println("Sequence " + Action.values()[i] + " recognized !");
					reset();
					return Action.values()[i];				
				}
			}
			
			//indicates bad sequence
			System.out.println("No possible sequence found... Fever goes down !");
			abort();
			return Action.STOP;
		}
		
		//indicate sequence not terminated
		playing = true;
		return null;
	}
	@Override
	public void draw(GdxGraphics g) {
		float dt = Gdx.graphics.getRawDeltaTime();
		Vector<Note> toRemove = new Vector<Note>();
		
		//draw elements
		for (Note n : toDraw) {
			int x = (Param.WIN_WIDTH / 2 - 200) + (toDraw.indexOf(n) % 4 * 100);
			n.draw(g, x);
			
			if(!playing){
				drawTime -= dt;
				if(drawTime <= 0){
					toRemove.add(n);
				}
			}
			
		}
		//Remove elements
		for (Note n : toRemove) toDraw.remove(n);
		toRemove.removeAllElements();
	}
	private void abort(){
		Note.clearFever();
		melody.removeAllElements();
		playing = false;
		drawTime = Param.NOTE_REMANENCE/1000f;
	}
	private void reset(){
		melody.removeAllElements();
		//the next 4 bars are input forbidden..
		Note.startForbiddenTime();
		playing = false;
		drawTime = Param.NOTE_REMANENCE/1000f;
	}
}
