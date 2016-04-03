package hevs.fragil.patapon.music;

import java.util.Vector;

public class Sequence {
	Vector<Note> notes = new Vector<Note>();
	public Sequence(){
		
	}
	public void add(Note n){
		notes.add(n);
	}
	public void remove(Note n){
		notes.remove(n);
	}
}
