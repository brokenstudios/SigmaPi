package hevs.fragil.patapon.units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hevs.fragil.patapon.others.Param;
import hevs.gdx2d.lib.GdxGraphics;

public class Archer extends Unit {
	static double modLife = -0.2;
	static FightFactor modAttack;
	static FightFactor modDefense;	
	
	static Animation walkAnimation;
	static Texture walkSheet;
	static TextureRegion[] walkFrames;
	static SpriteBatch spriteBatch;
	TextureRegion currentFrame;
	
	public Archer(){
		super();
		super.setLife(super.life*(1.0+modLife));	
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	@Override
	public void draw(GdxGraphics g) {
		g.draw(walkFrames[0], position, Param.FLOOR_DEPTH - 17 );
	}
	public void attack(){
		
	}
	//Just for onInit method
	static public void setSpriteLayouts(String url){
		
	}
	public void draw(GdxGraphics g, float time) {
		currentFrame = walkAnimation.getKeyFrame(time, true);
        //spritebatch call
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, position, Param.FLOOR_DEPTH - 17);
        spriteBatch.end();
	}
}
