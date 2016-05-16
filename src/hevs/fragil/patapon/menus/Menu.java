package hevs.fragil.patapon.menus;

import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.mechanics.Decor;
import hevs.fragil.patapon.mechanics.Param;

public class Menu extends RenderingScreen{
	Decor decor;

	@Override
	public void onInit() {
		decor = new Decor(Param.Type1);
	}

	@Override
	protected void onGraphicRender(GdxGraphics g) {
		g.clear(decor.getBackground());
		g.drawStringCentered(g.getScreenHeight() / 2, "Wow! What a menu!");
		
	}

}
