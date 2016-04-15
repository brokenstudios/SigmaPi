package hevs.fragil.patapon.drawables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import hevs.fragil.patapon.others.Param;
import hevs.fragil.patapon.units.BodyPart;
import hevs.gdx2d.lib.GdxGraphics;

public class Frame {
	private BodyPart b;
	//image containing multiple frames
	static Texture sheet;
	//part of the sheet to draw
	static TextureRegion frame;
	//for GPU acceleration
	static SpriteBatch spriteBatch;
	//indexes to get the right frame
	//x for levels
	int x;
	//y for breeds
	int y;
	
	public Frame(String url, int x, int y, int cols, int rows, BodyPart b){
		this.b = b;
		sheet = new Texture(Gdx.files.internal(url));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/cols, sheet.getHeight()/rows);
        //number of frames
        frame = tmp[y][x];
        spriteBatch = new SpriteBatch();
	}
	public void draw(int position){
		//spritebatch call
		spriteBatch.begin();
		spriteBatch.draw(frame, position, Param.FLOOR_DEPTH - 17);
		spriteBatch.end();
	}
	public BodyPart getBodyPart() {
		// TODO Auto-generated method stub
		return b;
	}
}
