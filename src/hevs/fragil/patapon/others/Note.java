package hevs.fragil.patapon.others;

public class Note{
	long timing;
	int length;
	int mark;
	int id;
	
	static int lastSystemTempo = 0;
	
	Note(int noteID){
		id = noteID;
		timing = System.currentTimeMillis();
	}
}
