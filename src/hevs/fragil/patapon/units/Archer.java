package hevs.fragil.patapon.units;

import com.badlogic.gdx.Gdx;
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
	static public void setSpriteSheet(String url){
		walkSheet = new Texture(Gdx.files.internal(url));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.150f, walkFrames);
        spriteBatch = new SpriteBatch();
	}
	public void draw(GdxGraphics g, float time) {
		currentFrame = walkAnimation.getKeyFrame(time, true);
        //spritebatch call
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, position, Param.FLOOR_DEPTH - 17);
        spriteBatch.end();
	}
}
