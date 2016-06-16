package hevs.fragil.patapon.physics;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.Param;

public class Tower extends PhysicsStaticBox implements DrawableObject, CollidedObject{
	private int x;
	private int h;
	private float life = 10;
	private static SpriteSheet basis1, basis2, head;
	
	public Tower(int x, int h){
		super("tower", new Vector2(x, Param.FLOOR_DEPTH + h/2*20), 100, h*20);
		this.h = h;
		this.x = x;
		this.setCollisionGroup(-4);
	}

	@Override
	public void draw(GdxGraphics g) {
		for(int i = 0 ; i < h ; i++){
			if(i%2 == 0)
				basis1.drawFrame(0, (int)(x - g.getCamera().position.x + Param.CAM_WIDTH / 2)-50, Param.FLOOR_DEPTH + i*20);
			else
				basis2.drawFrame(0, (int)(x - g.getCamera().position.x + Param.CAM_WIDTH / 2)-50, Param.FLOOR_DEPTH + i*20);
		}
		head.drawFrame(0, (int)(x - g.getCamera().position.x + Param.CAM_WIDTH / 2)-50, Param.FLOOR_DEPTH + h*20);
		
	}
	@Override
	public int getCollisionGroup() {
		return 0;
	}

	@Override
	public boolean applyDamage(float damage) {
		if(life <= 0){
			return true;
		}
		else{
			life -= damage;
		}
		return false;
	}
	public boolean isExploded(){
		if(life<=0){
			return true;
		}
		else return false;
	}
	public int getPos(){
		return x;
	}
	public int getHeight(){
		return h;
	}
	public static void loadFiles(){
		basis1 = new SpriteSheet("data/images/tower_basis1.png", 1, 1, 1, false, PlayMode.NORMAL);
		basis2 = new SpriteSheet("data/images/tower_basis2.png", 1, 1, 1, false, PlayMode.NORMAL);
		head = new SpriteSheet("data/images/tower_head.png", 1, 1, 1, false, PlayMode.NORMAL);
	}

	public boolean isOccuped(int posToTry) {
		if(posToTry < x+50 && posToTry > x-50)
			return true;
		return false;
	}
	public int getLeftLimit(){
		return x-50;
	}
}
