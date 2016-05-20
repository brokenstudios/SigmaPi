package hevs.fragil.patapon.music;

import hevs.fragil.patapon.mechanics.Param;

public class Note{
	//delay between tempo and note
	Drum drum;
	private static int feverScore = 0;
	private static float forbiddenTimeCounter = 0;
	//margins of acceptance
	final static int PASS = 100;
	final static int GOOD = 60;
	final static int EXCELLENT = 45;
	final static int PERFECT = 30;		
	public boolean outOfRythm = false;
		
	public Note(Drum d, float dt){
		this.drum = d;
		//to have values in ms
		dt *= 1000; 
		if(forbiddenTimeCounter <= 0){
			if(dt < 1){
				juge(0);
				System.out.println(" ms ! WOW ! LIKE A BOSS !");
			}
			else if(dt < Param.MUSIC_BAR/2){
				juge(dt);
				System.out.println(" ms too late");
			}
			else if(dt > Param.MUSIC_BAR/2){
				juge(Param.MUSIC_BAR - dt);
				System.out.println(" ms too early");
			}
		}
		else {
			System.out.println("Don't play until Sigmapis are ready !");
			clearFever();
			outOfRythm = true;
		}
	}
	/**
	 * 	 * @author loicg
	 * @return value between 0 and 100
	 */
	public static int getFever(){
		return Math.min(feverScore, 100);
	}
	public static void clearFever(){
		feverScore = 0;
	}
	// return a value depending of the user rythm precision
	public void juge(float delay){
			//bonus score if well done
			System.out.print(drum.toString() + " ");
			if(delay < PERFECT){
				System.out.print("PERFECT : " + Math.round(delay));
				feverScore += 15;
			}
			else if(delay < EXCELLENT){
				System.out.print("EXCELLENT : " + Math.round(delay));
				feverScore += 10;
			}
			else if(delay < GOOD){
				System.out.print("GOOD : " + Math.round(delay));
				feverScore += 5;
			}
			else if (delay < PASS){
				System.out.print("PASS : " + Math.round(delay));
				feverScore += 1;
			}
			else {
				System.out.print("BAD : " + Math.round(delay));
				clearFever();
			}			
	}
	public static void startForbiddenTime() {
		forbiddenTimeCounter = 2;
	}
	public static void updateForbiddenTime(float dt){
		if(forbiddenTimeCounter > 0)
			forbiddenTimeCounter -= dt;
		else 
			forbiddenTimeCounter = 0;
	}
}
