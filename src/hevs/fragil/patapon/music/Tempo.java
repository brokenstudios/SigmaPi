package hevs.fragil.patapon.music;
import java.util.TimerTask;

import hevs.fragil.patapon.graphics.Frame;
import hevs.fragil.patapon.graphics.Map;

public class Tempo extends TimerTask {
	Frame f = new Frame();
	static long lastTime;
	public static int soundFlag = 1;
	public static int soundEnable = 0;
	public static boolean snapFlag = false;
	public static boolean snapEnable = false;	
	
	@Override
	public void run() {
		Frame.blinkEnable = true;
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
