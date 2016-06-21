package hevs.fragil.patapon.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.mechanics.Param;

public class Menu extends RenderingScreen{
	private BitmapFont font, bigFont;

	@Override
	public void onInit() {
		FileHandle woodstamp = Gdx.files.internal("data/font/Woodstamp.ttf");

		// See all parameters available in the FreeTypeFontParameter
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();

		/**
		 * Generates the fonts images from the TTF file
		 */
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(woodstamp);
		parameter.size = generator.scaleForPixelHeight(60);
		parameter.color = Color.BLACK;
		font = generator.generateFont(parameter);
		
		generator = new FreeTypeFontGenerator(woodstamp);
		parameter.size = generator.scaleForPixelHeight(100);
		parameter.color = Color.BLACK;
		bigFont = generator.generateFont(parameter);
	}

	@Override
	protected void onGraphicRender(GdxGraphics g) {
		g.clear(Param.BACKGROUND);
		g.drawStringCentered(600, "SIGMAPI", bigFont);
		g.drawStringCentered(400, "Press ENTER to play", font);
	}

}
