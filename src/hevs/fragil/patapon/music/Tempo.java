package hevs.fragil.patapon.music;
import java.util.TimerTask;

import hevs.fragil.patapon.graphics.Frame;
import hevs.fragil.patapon.graphics.Map;
import hevs.fragil.patapon.others.Data;

public class Tempo extends TimerTask {
	Frame f = new Frame();
	@Override
	public void run() {
		Frame.blinkEnable = true;
		Data.lastTempoTime = System.currentTimeMillis();
		//Change sound loop only in time
		int index = Data.soundFlag % Map.getNbTracks();
		if(index != Data.soundEnable){
			Data.soundEnable = index;
			Data.soundChange = true;
		}
		//Applies new sound loop only in time
		if(Data.snapFlag != Data.snapEnable){
			Data.snapEnable = Data.snapFlag;
			Data.snapChange = true;
		}
	}
}
