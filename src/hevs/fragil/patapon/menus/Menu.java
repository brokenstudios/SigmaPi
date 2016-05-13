package hevs.fragil.patapon.menus;

import hevs.fragil.patapon.mechanics.Param;

import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;

public class Menu extends RenderingScreen{

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
		System.out.println("hello");
	}

	@Override
	protected void onGraphicRender(GdxGraphics g) {
		g.clear(Param.Type1);
		g.drawStringCentered(g.getScreenHeight() / 2, "Wow! What a menu!");
		
	}

}
