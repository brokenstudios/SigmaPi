package hevs.fragil.patapon.menus;

import hevs.fragil.patapon.mechanics.Decor;
import hevs.fragil.patapon.mechanics.Param;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;

public class LevelSelection extends RenderingScreen{
	Decor decor;

	@Override
	public void onInit() {
		decor = new Decor(Param.Type2);
		
	}

	@Override
	protected void onGraphicRender(GdxGraphics g) {
		g.clear(decor.getBackground());
		g.drawStringCentered(g.getScreenHeight() / 2, "Wow! What a level selection!");
		
	}

}
