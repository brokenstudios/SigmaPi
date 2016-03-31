package hevs.fragil.patapon.others;

import hevs.fragil.patapon.graphics.Frame;

public abstract class Timing{
	static int bar = 0;
	static long milliseconds = 0;
	static long timingFirstNote = 0;
	static long timingSecondNote = 0;
	static boolean isSecondCall = false;
	final static int DELTA_TIMING = 500;
	final static int TIMING_MARGIN = 100;
	final static int GOOD = 25;
	final static int EXCELLENT = 50;
	final static int PERFECT = 75;
	static Frame f;
	
	// return a value depending of the user rythm precision
	public static int inTime(){
		long dTiming = Math.abs(timingSecondNote - timingFirstNote);
		int minMargin = DELTA_TIMING - TIMING_MARGIN;
		int maxMargin = DELTA_TIMING + TIMING_MARGIN;
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
		
		return feverScore;
	}
	
	public static void checkTime(){
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
	
	public static boolean isFrameVisible(){
		long a = System.currentTimeMillis();
		
		
		
		return true;
	}
	
	public static int rythm(){
		if(Data.rythmEnable){
			if(milliseconds % 500 == 0){
				if(bar == 8){
					bar = 0;
				}
				else{	
					bar++;
				}				
			}
		}
		return bar;
	}

}
