package hevs.fragil.patapon.mechanics;

import com.badlogic.gdx.graphics.Color;

import hevs.fragil.patapon.music.Drum;



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
	//Default values of maps dimensions (!= game window)
	public static final int MAP_HEIGHT = 1800;
	public static final int MAP_WIDTH = 3000;
	public static final int CAMERAOFFSET = 30;
	public static final int FLOOR_DEPTH = 30;
	public static final int FRAME_DEGRADE_STEPS = 10;
	//Break the color in case of emergency #SoColorful
	public static final Color Type1 = rgbToFloat(243, 146, 0);
	public static final Color Type2 = rgbToFloat(227, 6, 19);
	public static final Color Type3 = rgbToFloat(230, 0, 126);
	public static final Color Type4 = rgbToFloat(0, 159, 227);
	public static final Color Type5 = rgbToFloat(58, 170, 53);
	
	//Timer periods
	public static final float MUSIC_BAR = .5f;
	public static final float ACTIONS_BAR = .01f;
	
	//Shifting width
	public static final int WALK_WIDTH = 200;
	public static final int RETREAT_WIDTH = 100;
	
	//Shifting time
	public static final float WALK_TIME = 2f;
	public static final float RETREAT_TIME = 2f;
	
	//Shifting time bonus (value at max fever score)
	public static final float WALK_TIME_BONUS = .5f;
	public static final float RETREAT_TIME_BONUS = .1f;

	public static final float COOLDOWN_BASE = .5f;
	public static final int ATTACK_BASE = 5;
	public static final int RANGE_BASE = 0;

	public static final int HEROES_GROUP = -1;
	public static final int ENNEMIES_GROUP = -2;
	public static final int PROJECTILE_GROUP = -3;

	public static final float ATTACK_TIME = 2f;
	public static final float CHARGE_TIME = 2f;
	public static final float DEFEND_TIME = 2f;
	
	//Music tolerances 
	public static final float PASS = .1f;
	public static final float GOOD = .06f;
	public static final float EXCELLENT = .045f;
	public static final float PERFECT = 0.03f;

	public static final float NOTE_REMANENCE = 2;	
	
	//combo references
	public static final Drum[] 		WALK = {Drum.HE, Drum.HE, Drum.HE, Drum.S};
	public static final Drum[] 		ATTACK = {Drum.S, Drum.S, Drum.HE, Drum.S};
	public static final Drum[] 		DEFEND = {Drum.SO, Drum.SO, Drum.HE, Drum.S};
	public static final Drum[] 		MIRACLE = {Drum.YES, Drum.YES, Drum.YES, Drum.YES, Drum.YES};
	public static final Drum[] 		RETREAT = {Drum.S, Drum.HE, Drum.S, Drum.HE};
	public static final Drum[] 		CHARGE = {Drum.S, Drum.S, Drum.SO, Drum.SO};
	public static final Drum[][]	COMBOS = {WALK,ATTACK,DEFEND,MIRACLE,RETREAT,CHARGE};
	
	//Colors
	public static Color BACKGROUND = rgbToFloat(222,184,135);
	
	private static Color rgbToFloat(int r, int g, int b){
		Color temp = new Color();
		temp.r = (float)(r/255.0);
		temp.g = (float)(g/255.0);
		temp.b = (float)(b/255.0);
		return temp;
	}
}
