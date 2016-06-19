package hevs.fragil.patapon.menus;

import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.mechanics.Param;

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
