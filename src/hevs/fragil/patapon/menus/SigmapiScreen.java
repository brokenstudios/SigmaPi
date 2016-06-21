package hevs.fragil.patapon.menus;

import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.lib.ScreenManager;

public abstract class SigmapiScreen extends ScreenManager{
	private SoundSample loop;
	public boolean isAnimationFinished(){
		return transitioning;
	}
	public void playLoop(){
		loop.loop();
	}
	public void stopLoop(){
		loop.stop();
	}
	public void disposeLoop(){
		loop.dispose();
	}
}
