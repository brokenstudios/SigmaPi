package hevs.fragil.patapon.others;

public abstract class Timing {
	static long timingFirstNote = 0;
	static long timingSecondNote = 0;
	static boolean isSecondCall = false;
	final static int DELTATIMING = 600;
	final static int TIMINGMARGE = 100;
	final static int GOOD = 25;
	final static int EXCELLENT = 50;
	final static int PERFECT = 75;
	
	// return a value depending of the user rythm precision
	public static int inTime(){
		long dTiming = Math.abs(timingSecondNote - timingFirstNote);
		int minMargin = DELTATIMING - TIMINGMARGE;
		int maxMargin = DELTATIMING + TIMINGMARGE;
		// Add this to fever to reach this state
		int feverScore = 0;
		
		if(dTiming > minMargin && dTiming < maxMargin){
			System.out.println("In time");
			feverScore = 1;
			if(dTiming > (minMargin + GOOD) && dTiming < (maxMargin - GOOD)){
				System.out.println("GOOD");
				feverScore += 5;
			}
			else if(dTiming > (minMargin + EXCELLENT) && dTiming < (maxMargin - EXCELLENT)){
				System.out.println("EXCELLENT");
				feverScore += 10;
			}
			else if(dTiming > (minMargin + PERFECT) && dTiming < (maxMargin - PERFECT)){
				System.out.println("PERFECT");
				feverScore += 15;
			}
		}
		
		// Oui mais je l'aime ce commentaire!
		System.out.println(feverScore);
		return feverScore;
	}
	
	public static void saveTime(){
		if(!isSecondCall){
			timingFirstNote = System.currentTimeMillis();
			isSecondCall = true;
		}
		else{
			timingSecondNote = System.currentTimeMillis();
			isSecondCall = false;
			inTime();
		}
	}

}
