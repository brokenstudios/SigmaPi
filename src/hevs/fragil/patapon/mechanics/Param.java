package hevs.fragil.patapon.mechanics;

import com.badlogic.gdx.graphics.Color;

public abstract class Param {
	//Game parameters only
	//TODO all of customizable elements should be here !
	
	//Units related variables
	public static final int LIFE_BASE = 10;
	
	//TODO These should be gotten from sprite width **********
	public static final int UNIT_WIDTH = 48;
	public static final int SECTION_KEEPOUT = 64;
	//********************************************************
	
	//Style
	//Default values of game window (size of menu?)
	public static final int WIN_HEIGHT = 900;
	public static final int WIN_WIDTH = 1500;
	public static final int FLOOR_DEPTH = 30;
	public static final int FRAME_DEGRADE_STEPS = 10;
	//Break the color in case of emergency #SoColorful
	public static final Color Type1 = rgbToFloat(243, 146, 0);
	public static final Color Type2 = rgbToFloat(227, 6, 19);
	public static final Color Type3 = rgbToFloat(230, 0, 126);
	public static final Color Type4 = rgbToFloat(0, 159, 227);
	public static final Color Type5 = rgbToFloat(58, 170, 53);
	
	//Timer periods
	public static final int MUSIC_BAR = 500;
	public static final int ACTIONS_BAR = 10;
	
	//Shifting width
	public static final int WALK_WIDTH = 200;
	public static final int RETREAT_WIDTH = 100;
	
	//Shifting time
	public static final int WALK_TIME = 2000;
	public static final int RETREAT_TIME = 2000;
	
	//Shifting time bonus (value at max fever score)
	public static final int WALK_TIME_BONUS = 500;
	public static final int RETREAT_TIME_BONUS = 100;

	public static final int COOLDOWN_BASE = 500;
	public static final int ATTACK_BASE = 5;
	public static final int RANGE_BASE = 0;
	
	//Colors
	public static Color BACKGROUND = rgbToFloat(243,100,0);
	private static Color rgbToFloat(int r, int g, int b){
		Color temp = new Color();
		temp.r = (float)(r/255.0);
		temp.g = (float)(g/255.0);
		temp.b = (float)(b/255.0);
		return temp;
	}
}
