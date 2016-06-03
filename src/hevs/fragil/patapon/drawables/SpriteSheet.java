package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteSheet {
	Animation animation;
	Texture sheet;
	Sprite[] sprites;
	SpriteBatch spriteBatch;
	TextureRegion currentFrame;
	float frameDuration;
	private boolean flipped = false;
	
	public SpriteSheet(String url, int cols, int rows, float frameDuration, boolean flipped){
		this.flipped = flipped;
		this.frameDuration = frameDuration;
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
		Sprite tmp = sprites[frameIndex];
		tmp.setPosition(posX - tmp.getWidth()/2, posY);
		if(flipped && tmp.isFlipX() == false)
			tmp.flip(true, false);
		tmp.draw(spriteBatch);
		spriteBatch.end();
	}
	public void drawWalkAnimation(int walkIndex, int spriteNumber, float posX, float posY, float originX, float originY){
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
		tmp.setOrigin(originX, originY);
		if(flipped && tmp.isFlipX() == false)
			tmp.flip(true, false);
		tmp.setRotation(angle);
		tmp.setPosition(posX - tmp.getWidth()/2, posY);
		tmp.draw(spriteBatch);
		spriteBatch.end();
	}
	public void drawRotatedFrame(int spriteNumber, float angle, float posX, float posY, float offsetX, float offsetY){
		spriteBatch.begin();
		Sprite tmp = sprites[spriteNumber];
		tmp.setOrigin(32, 38);
		if(flipped && tmp.isFlipX() == false)
			tmp.flip(true, false);
		tmp.setRotation((float)Math.toDegrees(angle));
		float x = posX + (float) (offsetX * Math.cos(angle) + offsetY * Math.abs(Math.sin(angle)));
		float y = posY + (float) (offsetY * Math.cos(angle) + (offsetX-10) * Math.abs(Math.sin(angle)));
		tmp.setPosition(x, y);
		tmp.draw(spriteBatch);
		spriteBatch.end();
	}
	public int drawAllFrames(float time, float posX, float posY){
		currentFrame = animation.getKeyFrame(time, true);
		TextureRegion[] a = animation.getKeyFrames();
		int index = java.util.Arrays.asList(a).indexOf(currentFrame);
		spriteBatch.begin();
		Sprite tmp = sprites[index];
		tmp.setPosition(posX - tmp.getWidth()/2, posY);
		if(flipped && tmp.isFlipX() == false)
			tmp.flip(true, false);
		tmp.draw(spriteBatch);
		spriteBatch.end();
		return index;
	}
	public int drawFrames(float time, int startIndex, int nbFrames, float posX, float posY){
		currentFrame = animation.getKeyFrame(time, true);
		TextureRegion[] a = animation.getKeyFrames();
		int index = java.util.Arrays.asList(a).indexOf(currentFrame);
		index = index % nbFrames;
		index += startIndex;
		spriteBatch.begin();
		Sprite tmp = sprites[index];
		tmp.setPosition(posX - tmp.getWidth()/2, posY);
		if(flipped && tmp.isFlipX() == false)
			tmp.flip(true, false);
		tmp.draw(spriteBatch);
		spriteBatch.end();
		return index;
	}
	public void drawFrameAlpha(int frameIndex, int posX, int posY, float alpha) {
		spriteBatch.begin();
		Sprite tmp = sprites[frameIndex];
		tmp.setPosition(posX - tmp.getWidth()/2, posY);
		if(flipped && tmp.isFlipX() == false)
			tmp.flip(true, false);
		tmp.draw(spriteBatch, alpha);
		spriteBatch.end();
	}
	public void drawRotatedFrameAlpha(int spriteNumber, float angle, float posX, float posY, float offsetX, float offsetY, float alpha) {
		spriteBatch.begin();
		Sprite tmp = sprites[spriteNumber];
		tmp.setOrigin(32, 38);
		tmp.setRotation((float)Math.toDegrees(angle));
		float x = posX + (float) (offsetX * Math.cos(angle) + offsetY * Math.abs(Math.sin(angle)));
		float y = posY + (float) (offsetY * Math.cos(angle) + (offsetX-10) * Math.abs(Math.sin(angle)));
		tmp.setPosition(x, y);
		if(flipped && tmp.isFlipX() == false)
			tmp.flip(true, false);
		tmp.draw(spriteBatch, alpha);
		spriteBatch.end();
	}
	public float getDelay() {
		return frameDuration;
	}
}
