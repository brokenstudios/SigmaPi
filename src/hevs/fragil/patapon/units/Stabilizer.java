package hevs.fragil.patapon.units;

import com.badlogic.gdx.math.Vector2;

public class Stabilizer {
	// Hysteresis pattern to avoid vibrations due to physics
	private Vector2 stabilizer;
	
	private int xDir, yDir;
	
	public Stabilizer(){
		this(new Vector2(0,0));
	}
	public Stabilizer(Vector2 startPosition){
		stabilizer = startPosition;
	}
	
	public void set(Vector2 v) {
		stabilizer = v;
	}
	
	public Vector2 stabilized(float xPos, float yPos) {
		// Hysteresis pattern to avoid vibrations due to physics
		// Wait for 2 units change to reverse direction
		
		float x = Math.round(xPos);
		float y = Math.round(yPos);
		
		//X----------------------------------------
		if (xDir > 0) {			
			if (stabilizer.x < x) stabilizer.x = x;
			else if (stabilizer.x > x+1) xDir = 0;
		} 
		else if (xDir < 0){
			if (stabilizer.x > x) stabilizer.x = x;
			else if (stabilizer.x < x-1) xDir = 0;
		}
		else {
			if (stabilizer.x > x) xDir = -1;
			else if(stabilizer.x < x) xDir = 1;
		}
		
		//Y----------------------------------------
		if (yDir > 0) {			
			if (stabilizer.y < y) stabilizer.y = y;
			else if (stabilizer.y > y+1) yDir = 0;
		} 
		else if (yDir < 0){
			if (stabilizer.y > y) stabilizer.y = y;
			else if (stabilizer.y < y-1) yDir = 0;
		}
		else {
			if (stabilizer.y > y) yDir = -1;
			else if(stabilizer.y < y) yDir = 1;
		}
		
		return new Vector2(stabilizer.x, stabilizer.y);
	}
	public Vector2 stabilized(Vector2 pos) {
		return stabilized(pos.x, pos.y);
	}
	
	public Vector2 getStabilizedPos(){
		return stabilizer;
	}
}
