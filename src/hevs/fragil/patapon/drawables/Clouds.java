package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import ch.hevs.gdx2d.lib.GdxGraphics;

/**
 * Cloud image with parallax effect proportional to {@code zRatio}
 */
public class Clouds implements EditorObject {
	private static SpriteSheet clouds;
	private int x,z;
	
	public Clouds(){
		this(0,4);
		loadFiles();
	}
	public Clouds(int x, int zRatio){
		this.x = x;
		this.z = zRatio;
	}
	@Override
	public void draw(GdxGraphics g) {
		clouds.drawFrame(0, (int)(x - g.getCamera().position.x / z), 400);
	}
	public static void loadFiles(){
		clouds = new SpriteSheet("data/images/clouds.png", 1,1,1,false,PlayMode.NORMAL);
	}
	@Override
	public void place(int x) {
		this.x = x;
	}
	@Override
	public boolean isVisible(GdxGraphics g, float objectPos) {
		return true;
	}

}
