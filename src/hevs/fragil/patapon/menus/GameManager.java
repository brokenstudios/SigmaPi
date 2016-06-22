package hevs.fragil.patapon.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import ch.hevs.gdx2d.desktop.PortableApplication;
import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.accessories.SpritesVisualizer;
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
		screenManager.registerScreen(SpritesVisualizer.class);
//		screenManager.registerScreen(EndScreenVictory.class);
//		screenManager.registerScreen(EndScreenLoose.class);
		
		initFonts();
		
		menuLoop = new SigmaSoundSample("music/menu1.wav");
		menuLoop.playBistable();

	}

	private void initFonts() {
		FileHandle woodstamp = Gdx.files.internal("data/font/Woodstamp.ttf");

		// See all parameters available in the FreeTypeFontParameter
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();

		/**
		 * Generates the fonts images from the TTF file
		 */
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(woodstamp);
		parameter.size = generator.scaleForPixelHeight(40);
		parameter.color = Color.BLACK;
		Param.small = generator.generateFont(parameter);
		
		generator = new FreeTypeFontGenerator(woodstamp);
		parameter.size = generator.scaleForPixelHeight(70);
		parameter.color = Color.BLACK;
		Param.medium = generator.generateFont(parameter);
		
		generator = new FreeTypeFontGenerator(woodstamp);
		parameter.size = generator.scaleForPixelHeight(100);
		parameter.color = Color.BLACK;
		Param.large = generator.generateFont(parameter);
		
		generator = new FreeTypeFontGenerator(woodstamp);
		parameter.size = generator.scaleForPixelHeight(160);
		parameter.color = Color.BLACK;
		Param.xlarge = generator.generateFont(parameter);
	}

	@Override
	public void onKeyDown(int keycode) {
		super.onKeyDown(keycode);
		// Display the next screen with transition
		if(screenManager.getNextScreenIndex() == 2){
			menuLoop.stopBistable();
		}
		if (keycode == Input.Keys.ENTER)
			screenManager.smoothTransitionToNext();
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
