package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hevs.fragil.patapon.mechanics.Param;

public class SpriteSheet {
	Animation animation;
	Texture sheet;
	Sprite[] sprites;
	SpriteBatch spriteBatch;
	TextureRegion currentFrame;
	
	public SpriteSheet(String url, int cols, int rows, float frameDuration){
		sheet = new Texture(Gdx.files.internal(url));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/cols, sheet.getHeight()/rows);
        sprites = new Sprite[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sprites[index++] = new Sprite(tmp[i][j]);
            }
        }
        animation = new Animation(frameDuration, sprites);
        spriteBatch = new SpriteBatch();
	}
	public void drawFrame(int frameIndex, int posX, int posY){
		spriteBatch.begin();
		spriteBatch.draw(sprites[frameIndex], posX, posY);
		spriteBatch.end();
	}
	public void drawWalkAnimation(int walkIndex, int spriteNumber, int posX, int posY){
		spriteBatch.begin();
		Sprite tmp = sprites[spriteNumber];
		float angle = 0f;
		switch(walkIndex){
			case 0 : 	angle = -1;  
						posY -=3;
						break;
			case 1 : 	angle = 0;
						break;
			case 2 : 	angle = 1;
						posY -=3;
						break;
			case 3 : 	angle = 0;
						break;
		}
		tmp.setOrigin(32, 38);
		tmp.setRotation(angle);
		tmp.setPosition(posX, posY);
		tmp.draw(spriteBatch);
		spriteBatch.end();
	}
	public int drawKeyFrames(float time, int posX){
		currentFrame = animation.getKeyFrame(time, true);
		TextureRegion[] a = animation.getKeyFrames();
		int index = java.util.Arrays.asList(a).indexOf(currentFrame);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, posX, Param.FLOOR_DEPTH);
		spriteBatch.end();
		return index;
	}
}
