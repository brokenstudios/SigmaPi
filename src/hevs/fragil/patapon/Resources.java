package hevs.fragil.patapon;

import java.util.Vector;

import hevs.fragil.patapon.drawables.Frame;
import hevs.fragil.patapon.units.BodyPart;

/**
 * Singleton pattern implementation in Java
 * @author loicg
 *
 */
public class Resources {
	private Vector<Frame> frames = new Vector<Frame>() ;
	
	//#LEPROF
	private static Resources inst = null;
	public static  Resources getInstance() {
		if (inst == null) {
			inst = new Resources();
		}
		return inst;
	}
	private Resources(){
	}

	public void loadEyes(){
		//TODO dynamic loop size
		for(int i = 0; i<5; i++){
			frames.add(new Frame("data/images/eyes.png", i, 0, 5, 1, BodyPart.values()[i]));
		}
	}
	public void loadBodies() {
		//TODO dynamic loop size
		for(int i = 0; i<5; i++){
			for(int j = 0; j<5; j++){
				frames.add(new Frame("data/images/bodies.png", i, j, 5, 5, BodyPart.values()[j+5]));
			}
		}
	}
	public Frame getFrame(BodyPart b) {
		for (Frame frame : frames) {
			if(frame.getBodyPart() == b)
				return frame;
		}
		System.out.println("pas trouvé !");
		return null;
	}
}
