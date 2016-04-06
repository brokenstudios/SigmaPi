package hevs.fragil.patapon.others;

import com.badlogic.gdx.graphics.Color;

public class Param {
	//Game parameters only
	//TODO all of customizable elements should be here !
	
	//Units related variables
	public static final int LIFE_BASE = 10;
	
	//TODO These should be gotten from sprite width **********
	public static final int UNIT_WIDTH = 20;
	public static final int SECTION_KEEPOUT = 30;
	//********************************************************
	
	//Style 
	public static final int FLOOR_DEPTH = 30;
	public static final int FRAME_DEGRADE_STEPS = 10;
	
	//Timer periods
	public static final int MUSIC_BAR = 500;
	public static final int ACTIONS_BAR = 10;
	
	//Shifting width
	public static final int WALK_WIDTH = 200;
	public static final int RETREAT_WIDTH = 300;
	
	//Shifting time
	public static final int WALK_TIME = 2000;
	public static final int RETREAT_TIME = 1000;
	
	//Shifting time bonus (value at max fever score)
	public static final int WALK_TIME_BONUS = 500;
	public static final int RETREAT_TIME_BONUS = 500;
	
	//Colors
	public static Color BACKGROUND = Color.ORANGE;
	
}
