package hevs.fragil.patapon.menus;

import hevs.fragil.patapon.mechanics.Decor;
import hevs.fragil.patapon.mechanics.Param;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;

public class EndScreenVictory extends RenderingScreen{

	@Override
	public void onInit() {
	}

	@Override
	protected void onGraphicRender(GdxGraphics g) {
		g.clear(Param.Type4);
		g.drawStringCentered(g.getScreenHeight() / 2, "We got a winner!");
	}

}
