package hevs.fragil.patapon.music;
import java.util.TimerTask;

import hevs.fragil.patapon.drawables.BlinkingBorder;
import hevs.fragil.patapon.others.Game;

public class RythmTimer extends TimerTask {
	BlinkingBorder f = new BlinkingBorder();
	static long lastTime;
	public static int soundFlag = 1;
	public static int soundEnable = 0;
	public static boolean snapFlag = false;
	public static boolean snapEnable = false;	
	
	@Override
	public void run() {
		BlinkingBorder.blinkEnable = true;
		lastTime = System.currentTimeMillis();
		//Change sound loop only in time
		int index = soundFlag % Game.getNbTracks();
		if(index != soundEnable){
			soundEnable = index;
			Game.nextTrack();
			System.out.println("Music changed at " + System.currentTimeMillis()%500);
		}
		//Applies new sound loop only in time
		if(snapFlag != snapEnable){
			snapEnable = snapFlag;
			Game.snapToggle();
			System.out.println("Snap changed at " + System.currentTimeMillis()%500);
		}
	}
}
