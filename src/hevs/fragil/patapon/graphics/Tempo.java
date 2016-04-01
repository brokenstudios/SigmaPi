package hevs.fragil.patapon.graphics;
import java.util.TimerTask;
import hevs.fragil.patapon.others.Data;

public class Tempo extends TimerTask {
	Frame f = new Frame();
	@Override
	public void run() {
		Data.rythmEnable = true;
		Data.lastTempo = System.currentTimeMillis();
		//Change sound loop only in time
		int index = Data.soundFlag % Data.nbLoops;
		if(index != Data.soundEnable){
			Data.soundEnable = index;
			Data.soundChange = true;
		}
		//Applies new sound loop only in time
		if(Data.snapFlag != Data.snapEnable){
			Data.snapEnable = Data.snapFlag;
			Data.snapChange = true;
		}
//		System.out.println("Tempo : " + System.currentTimeMillis()%500);
	}
}
