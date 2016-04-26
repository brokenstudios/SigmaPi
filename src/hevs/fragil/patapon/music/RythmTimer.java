package hevs.fragil.patapon.music;
import java.util.TimerTask;

import hevs.fragil.patapon.drawables.BlinkingBorder;
import hevs.fragil.patapon.others.Map;

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
		int index = soundFlag % Map.getNbTracks();
		if(index != soundEnable){
			soundEnable = index;
			Map.nextTrack();
			System.out.println("Music changed at " + System.currentTimeMillis()%500);
		}
		//Applies new sound loop only in time
		if(snapFlag != snapEnable){
			snapEnable = snapFlag;
			Map.snapToggle();
			System.out.println("Snap changed at " + System.currentTimeMillis()%500);
		}
	}
}
