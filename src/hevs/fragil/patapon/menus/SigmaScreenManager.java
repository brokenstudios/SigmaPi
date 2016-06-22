package hevs.fragil.patapon.menus;

import ch.hevs.gdx2d.lib.ScreenManager;

public class SigmaScreenManager extends ScreenManager{
	
	public boolean isAnimationFinished(){
		return transitioning;
	}
	public int getActiveScreenIndex(){
		return activeScreen;
	}
	public int getNextScreenIndex() {
		if (nextScreenIdx < 0)
			return (activeScreen + 1) % screens.size();

		return nextScreenIdx;
	}
}
