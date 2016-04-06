package hevs.fragil.patapon.music;

public class Note{
	//delay between tempo and note
	Drum drum;
	private static int feverScore = 0;
	
	//margins of acceptance
	final static int PASS = 100;
	final static int GOOD = 60;
	final static int EXCELLENT = 45;
	final static int PERFECT = 30;		
		
	public Note(Drum d){
		this.drum = d;
		long delayNext = System.currentTimeMillis() - Tempo.lastTime;
		long delayPrev =  500 - delayNext;
		long delay = Math.min(delayNext, delayPrev);
		
		boolean late;
		if(delay == delayNext)
			late = true;
		else 
			late = false;
		
		juge(delay, late);
	}
	/**
	 * @author loicg
	 * @return value between 0 and 100
	 */
	public static int getFeverCoefficient(){
		return Math.min(feverScore, 100);
	}
	public static void badFever(){
		feverScore = 0;
	}
	// return a value depending of the user rythm precision
	public void juge(long delay, boolean late){
			//bonus score if well done
			System.out.print(drum.toString() + " ");
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
			if(late)System.out.println(" ms too late");
			else System.out.println(" ms too early");
	}
}
