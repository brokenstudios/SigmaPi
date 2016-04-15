package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hevs.fragil.patapon.others.Param;
import hevs.gdx2d.lib.GdxGraphics;

public class SpriteSheet {
	static Animation animation;
	static Texture sheet;
	static TextureRegion[] frames;
	//for GPU(se)
	static SpriteBatch spriteBatch;
	//drawed final frame
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
	public void draw(float time, int position){
		currentFrame = animation.getKeyFrame(time, true);
		//spritebatch call
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, position, Param.FLOOR_DEPTH - 17);
		spriteBatch.end();
	}
}
