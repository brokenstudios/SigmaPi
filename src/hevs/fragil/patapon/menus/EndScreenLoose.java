package hevs.fragil.patapon.menus;

import hevs.fragil.patapon.mechanics.Param;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;

public class EndScreenLoose extends RenderingScreen{

	@Override
	public void onInit() {
	}

	@Override
	protected void onGraphicRender(GdxGraphics g) {
		g.clear(Param.Type5);
		g.drawStringCentered(g.getScreenHeight() / 2, "Such deception, much looser...");
	}

}
