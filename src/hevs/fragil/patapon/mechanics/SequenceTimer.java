package hevs.fragil.patapon.mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;

import ch.hevs.gdx2d.components.audio.SoundSample;
import hevs.fragil.patapon.units.Company;
import hevs.fragil.patapon.units.Section;
import hevs.fragil.patapon.units.Unit;

public abstract class SequenceTimer{
	private static float step = 0;
	private static float deltaTime;
	private static float feverScore;
	private static float start, progression, end;
	private static SoundSample lalala;
	private static boolean playing = false;
	
	public static void run(Company c, int fever) {
		float dt = Gdx.graphics.getRawDeltaTime();
		deltaTime = dt;
		feverScore = fever;
		switchAction(c.getAction(), c);
	}
	private static void switchAction(State a, Company c){
		boolean finished = false;
		if(a != null){
			if(playing == false){
//				lalala.play();
				playing = true;
			}

			// process moves
			c.aiMove();
			
			switch(a){
				case WALK : 	finished = walk(c);
								break;
				case ATTACK : 	finished = attack(c);
								break;
				case DEFEND : 	finished = defend(c);
								break;
				case MIRACLE : 	finished = miracle(c);
								break;
				case RETREAT : 	finished = retreat(c);
								break;
				case CHARGE : 	finished = charge(c);
								break;
				case IDLE :		finished = stop(c);
								playing = false;
								lalala.stop();
								break;
				default : 		
								break;
			}
			if(finished){
				playing = false;
				c.actionFinished();
			}
		}
	}
	private static boolean charge(Company c) {
		// Disable automatic unit placement
		c.regroupUnits();
		
		//increase attack skills for this time
		return wait(Param.CHARGE_TIME, c);
	}
	private static boolean miracle(Company c) {
		// Disable automatic unit placement
		c.regroupUnits();
		
		//TODO new screen launch !
		return true;
	}
	private static boolean defend(Company c) {
		// Disable automatic unit placement
		c.regroupUnits();
		
		//TODO increase defend skills for this time
		return wait(Param.DEFEND_TIME, c);
	}
	private static boolean shift(float totalTime, int distance, Company c){
		if(progression == 0f){
			System.out.println("Company will shift from : " + c.getPosition() + " to : " + (c.getPosition() + distance));
			start = c.getPosition();
			end = start + distance;
		}
		
		progression += deltaTime/totalTime;
		c.setPosition((int)Interpolation.fade.apply(start, end, progression));
		
		if(progression >= 1f){
			progression = 0f;
			return true;
		}
		else return false;
	}
	private static boolean wait(double time, Company c){
		progression += deltaTime;
		
		if(progression >= time ){
			progression = 0f;
			return true ;
		}
		
		else return false ;
		
	}
	private static boolean walk(Company c){
		float time = Param.WALK_TIME;
		
		// Disable automatic unit placement
		c.regroupUnits();
		
		//add bonus time (faster move with fever)
		time -= Param.WALK_TIME_BONUS/100.0f * feverScore;
		
		return shift(time, Param.WALK_WIDTH, c);
	}
	private static boolean retreat(Company c){
		// Disable automatic unit placement
		c.regroupUnits();
		
		float time = Param.RETREAT_TIME;
		float bonus = (float) (Param.RETREAT_TIME_BONUS/100.0f * feverScore);
		if (step == 0f) {
			if(shift(time/4f - bonus, -Param.RETREAT_WIDTH, c))
				step++;
		}
		
		else if(step == 1f){
			if(wait(time/2f + bonus, c))
				step++;
		}
		
		else if (shift(time/4f, Param.RETREAT_WIDTH, c)){
			step = 0;
			return true;
		}
		
		return false;
	}
	private static boolean attack(Company c){
		progression += deltaTime;
		
		// Enable automatic unit placement
		c.freeUnits();
		
		for (Section s : c.sections) {
			for (Unit u : s.units) {
				u.attackRoutine();
			}
		}
		
		//action ended
		if(progression >= Param.ATTACK_TIME) {
			for (Section s : c.sections) {
				for (Unit u : s.units) {
					u.resetGesture();
				}
			}
			progression = 0f;
			return true;
		}
		return false;
	}
	private static boolean stop(Company c){
		end = start;
		progression = 0f;
		step = 0;
		deltaTime = 0f;
		return true;
	}
	public static void loadFiles(){
		lalala = new SoundSample("data/music/lalala.mp3");
	}
}
