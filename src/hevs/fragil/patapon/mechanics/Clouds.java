package hevs.fragil.patapon.mechanics;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.drawables.SpriteSheet;

public class Clouds implements DrawableObject {
	private static SpriteSheet clouds;
	private int x,z;
	
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

}
