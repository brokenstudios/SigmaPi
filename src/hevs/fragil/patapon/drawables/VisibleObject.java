package hevs.fragil.patapon.drawables;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

/**
 * Must be implemented in every decor object to avoid useless drawing.
 * When {@code isVisible()} returns {@code true}, must be drawn by the Scenery class.
 */
public interface VisibleObject extends DrawableObject{
	public boolean isVisible(GdxGraphics g, float objectPos);
}
