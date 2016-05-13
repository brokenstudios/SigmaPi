package hevs.fragil.patapon.menus;

import hevs.fragil.patapon.mechanics.Decor;
import hevs.fragil.patapon.mechanics.Param;
import hevs.gdx2d.components.screen_management.RenderingScreen;
import hevs.gdx2d.lib.GdxGraphics;

public class Menu extends RenderingScreen{
	Decor decor;

	@Override
	public void onInit() {
		decor = new Decor(Param.Type1);
	}

	@Override
	protected void onGraphicRender(GdxGraphics g) {
		g.clear(Param.Type1);
		g.drawStringCentered(g.getScreenHeight() / 2, "Wow! What a menu!");
		
	}

}
