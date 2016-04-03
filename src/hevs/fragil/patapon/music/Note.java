package hevs.fragil.patapon.music;
public class Note{
	//delay between tempo and note
	long delay;
	int length;
	int mark;
	Drum id;
	static int feverScore = 0;
	
	//too late or too early of 250ms
	final static int PASS = 100;
	final static int GOOD = 60;
	final static int EXCELLENT = 45;
	final static int PERFECT = 30;
	boolean tooLate = false;
	
	public Note(Drum d){
		id = d;
		long late = System.currentTimeMillis() - Tempo.lastTime;
		long early =  500 - late;
		delay = Math.min(late, early);
		if(delay == late)tooLate = true;
		else tooLate = false;
		classify();
	}
	// return a value depending of the user rythm precision
	public void classify(){
			//bonus score if well done
			if(delay < PERFECT){
				System.out.print("PERFECT : " + delay);
				feverScore += 15;
			}
			else if(delay < EXCELLENT){
				System.out.print("EXCELLENT : " + delay);
				feverScore += 10;
			}
			else if(delay < GOOD){
				System.out.print("GOOD : " + delay);
				feverScore += 5;
			}
			else if (delay < PASS){
				System.out.print("PASS : " + delay);
				feverScore += 1;
			}
			else {
				System.out.print("BAD : " + delay);
				feverScore = 0;
			}
			if(tooLate)System.out.println(" ms too late");
			else System.out.println(" ms too early");
	}
}
