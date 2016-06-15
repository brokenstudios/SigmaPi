package hevs.fragil.patapon.drawables;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

public interface VisibleObject extends DrawableObject{
	
	public boolean isVisible(GdxGraphics g, float objectPos);

}
