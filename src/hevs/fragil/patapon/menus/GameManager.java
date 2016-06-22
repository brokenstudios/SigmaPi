package hevs.fragil.patapon.menus;

import com.badlogic.gdx.Input;

import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.accessories.SpritesViewer;
import hevs.fragil.patapon.mechanics.Level;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.music.SigmaSoundSample;

public class GameManager extends PortableApplication {
	private SigmaSoundSample menuLoop;
	
	private SigmaScreenManager screenManager = new SigmaScreenManager();

	@Override
	public void onInit() {
		setTitle("SIGMAPI - 2016 - Broken Studios - Loïc Fracheboud, Loïc Gillioz, S2d");
		screenManager.registerScreen(Menu.class);
		screenManager.registerScreen(LevelSelection.class);
		screenManager.registerScreen(Level.class);
		screenManager.registerScreen(SpritesViewer.class);
//		screenManager.registerScreen(EndScreenVictory.class);
//		screenManager.registerScreen(EndScreenLoose.class);
		
		menuLoop = new SigmaSoundSample("music/menu1.wav");
		menuLoop.playBistable();

	}

	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);
		// Display the next screen with transition
		if (keycode == Input.Keys.ENTER){
			if(screenManager.getNextScreenIndex() == 2){
				menuLoop.stopBistable();
			}
			screenManager.smoothTransitionToNext();
		}
		//Otherwise this causes bugs
		
		//call keydown on the current screen
		else
			screenManager.getActiveScreen().onKeyDown(keycode);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		screenManager.render(g);
		menuLoop.sync();
	}

	public static void main(String[] args) {
		new GameManager();
	}

	public GameManager() {
		super(Param.CAM_WIDTH, Param.CAM_HEIGHT);
	}

}
