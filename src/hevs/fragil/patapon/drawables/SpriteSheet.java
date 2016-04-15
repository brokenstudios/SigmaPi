package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hevs.fragil.patapon.others.Param;

public class SpriteSheet {
	Animation animation;
	Texture sheet;
	TextureRegion[] frames;
	SpriteBatch spriteBatch;
	TextureRegion currentFrame;
	
	public SpriteSheet(String url, int cols, int rows){
		sheet = new Texture(Gdx.files.internal(url));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/cols, sheet.getHeight()/rows);
        frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(0.150f, frames);
        spriteBatch = new SpriteBatch();
	}
	public void drawFrame(int frameIndex, int posX, int posY){
		spriteBatch.begin();
		spriteBatch.draw(frames[frameIndex], posX, posY);
		spriteBatch.end();
	}
	public void drawKeyFrames(float time, int posX){
		currentFrame = animation.getKeyFrame(time, true);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, posX, Param.FLOOR_DEPTH - 17);
		spriteBatch.end();
	}
}
