package hevs.fragil.patapon.menus;

import hevs.fragil.patapon.accessories.SpritesVisualizer;
import hevs.fragil.patapon.mechanics.Level;
import hevs.fragil.patapon.mechanics.Param;
import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.ScreenManager;

import com.badlogic.gdx.Input;

public class GameManager extends PortableApplication {

	private ScreenManager screenManager = new ScreenManager();

	@Override
	public void onInit() {
		setTitle("SIGMAPI - 2016 - Broken Studios - Loïc Fracheboud, Loïc Gillioz, S2d");
//		screenManager.registerScreen(Menu.class);
//		screenManager.registerScreen(LevelSelection.class);
		screenManager.registerScreen(Level.class);
		screenManager.registerScreen(SpritesVisualizer.class);
//		screenManager.registerScreen(EndScreenVictory.class);
//		screenManager.registerScreen(EndScreenLoose.class);

	}

	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);
		
		// Display the next screen with transition
		if (keycode == Input.Keys.TAB)
			screenManager.sliceTransitionToNext();
		
		//TODO must wait until screen transition finished ! 
		//Otherwise this causes bugs
		
		//call keydown on the current screen
		else
			screenManager.getActiveScreen().onKeyDown(keycode);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		screenManager.render(g);
	}

	public static void main(String[] args) {
		new GameManager();

	}

	public GameManager() {
		super(Param.CAM_WIDTH, Param.CAM_HEIGHT);
	}

}
