package hevs.fragil.patapon.menus;

import hevs.fragil.patapon.mechanics.Param;
import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;

public class LevelSelection extends RenderingScreen{

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onGraphicRender(GdxGraphics g) {
		g.clear(Param.Type2);
		g.drawStringCentered(g.getScreenHeight() / 2, "Wow! What a level selection!");
		
	}

}
