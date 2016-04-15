package hevs.fragil.patapon.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteLayout {
	static Animation animation;
	static Texture sheet;
	static TextureRegion[] frames;
	static SpriteBatch spriteBatch;
	TextureRegion currentFrame;
	int frameCols = 2;
	int frameRows = 1;
	
	SpriteLayout(String url){
		sheet = new Texture(Gdx.files.internal(url));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/frameCols, sheet.getHeight()/frameRows);
        frames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(0.150f, frames);
        spriteBatch = new SpriteBatch();
	}
}
