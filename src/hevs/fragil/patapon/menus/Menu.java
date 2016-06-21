package hevs.fragil.patapon.menus;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.mechanics.Param;

public class Menu extends RenderingScreen{
	private BitmapImage brokenStudios;

	@Override
	public void onInit() {
		brokenStudios = new BitmapImage("images/broken.png");
	}

	@Override
	protected void onGraphicRender(GdxGraphics g) {
		g.clear(Param.BACKGROUND);
		g.drawStringCentered(600, "SIGMAPI", Param.xlarge);
		g.drawStringCentered(400, "Press ENTER to play", Param.medium);
		
		g.drawTransformedPicture(750, 150, 0, 0.5f, brokenStudios);
	}

}
