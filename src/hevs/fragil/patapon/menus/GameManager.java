package hevs.fragil.patapon.menus;

import com.badlogic.gdx.Input;

import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.ScreenManager;
import hevs.fragil.patapon.mechanics.Param;

public class GameManager extends PortableApplication {

	private ScreenManager screenManager = new ScreenManager();

	@Override
	public void onInit() {
		setTitle("Ce jeu sera vôtre jeu !");
		screenManager.registerScreen(Menu.class);
		// screenManager.registerScreen(LevelSelection.class);
		screenManager.registerScreen(hevs.fragil.patapon.mechanics.Level.class);
		// screenManager.registerScreen(EndScreenVictory.class);
		// screenManager.registerScreen(EndScreenLoose.class);

	}

	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);
		// Display the next screen without transition
		if (keycode == Input.Keys.ENTER)
			screenManager.sliceTransitionToNext();
		//call keydown on the current screen
		//TODO wait until animation finished
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
		super(Param.WIN_WIDTH, Param.WIN_HEIGHT);
	}

}
