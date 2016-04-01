package hevs.fragil.patapon.others;
public class Data {
	
	//ID for Notes TODO change to enum !
	public static final int HE_ID = 11;
	public static final int S_ID = 12;
	public static final int SO_ID = 13;
	public static final int YES_ID = 14;
	
	//Command references
	public static final int[] WALK = {HE_ID, HE_ID, HE_ID, S_ID};
	
	//Game constants
	public static final int LIFE_BASE = 10;
	public static final int BAR = 500;
	public static final int UNIT_WIDTH = 20;
	public static final int SECTION_KEEPOUT = 30;
	public static final int FLOOR_DEPTH = 30;
	public static final int FRAME_DURATION = 10;
	
	//TODO should we do it with Color class ?
	public static float backColorR = (float) (Math.random());
	public static float backColorG = (float) (Math.random());
	public static float backColorB = (float) (Math.random());
	public static float frameColorR = 1;
	public static float frameColorG = 1;
	public static float frameColorB = 1;

	//Sound Flags TODO should we move it to the Map class ?
	public static int soundFlag = 0;
	public static int soundEnable = 0;
	public static boolean soundChange = false;
	public static boolean snapFlag = false;
	public static boolean snapEnable = false;
	public static boolean snapChange = false;
	
	//Sound variables
	public static long lastTempoTime = 0;
	public static int nbLoops = 1;
	
}
