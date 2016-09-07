package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import ch.hevs.gdx2d.lib.GdxGraphics;

/**
 * Mountains image with parallax effect proportional to {@code zRatio}
 */
public class Mountains implements EditorObject,VisibleObject {
	private static SpriteSheet mountains;
	private int x,z;
	
	public Mountains(){
		this(0,2);
		loadFiles();
	}
	public Mountains(int x, int zRatio){
		this.x = x;
		this.z = zRatio;
	}
	@Override
	public void draw(GdxGraphics g) {
		mountains.drawFrame(0, (int)(x - g.getCamera().position.x / z), -800);
	}
	/**
	 * Must be called in the onInit method of the level
	 */
	public static void loadFiles(){
		mountains = new SpriteSheet("data/images/mountains2.png", 1,1,1,false,PlayMode.NORMAL);
	}
	@Override
	public void place(int x) {
		this.x = x;
	}
	@Override
	public boolean isVisible(GdxGraphics g, float objectPos) {
		return true;
	}
	@Override
	public int getZ() {
		return z;
	}

	
}
