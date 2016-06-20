package hevs.fragil.patapon.accessories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;

public class SpritesVisualizer extends RenderingScreen {
	SpriteSheet armsBows, armsSwords, armsSpears, legs, body, eye;
	int armsIndex, bodyIndex, eyeIndex, armsType;
	float stateTime ;
	
	@Override 
	public void onInit() {
		//load files for the spritesheets
		body = new SpriteSheet("images/bodies.png", 5, 5, 0.2f, false, PlayMode.NORMAL);
		eye = new SpriteSheet("images/eyes.png", 5, 2, 0.2f, false, PlayMode.NORMAL);
		armsBows = new SpriteSheet("images/arms_bows.png", 4, 6, 0.2f, false, PlayMode.LOOP);
		armsSwords = new SpriteSheet("images/arms_swords.png", 4, 6, 0.2f, false, PlayMode.LOOP);
		armsSpears = new SpriteSheet("images/arms_spears.png", 4, 6, 0.2f, false, PlayMode.LOOP);
		legs = new SpriteSheet("images/legs.png", 4, 1, 0.2f, false, PlayMode.LOOP);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		stateTime += Gdx.graphics.getDeltaTime();
		g.clear(Color.WHITE);
		g.drawFilledRectangle(100, 100, 10, 10, 0);
		legs.drawAllFrames(stateTime, 100,100);
		body.drawFrame(bodyIndex % 25, 100, 100);
		eye.drawFrame(eyeIndex % 7, 100, 100);
		switch(armsType % 3){
			case 0: 	armsBows.drawFrames(stateTime, armsIndex % 6 * 4, 4, 100, 100);
			break;
			case 1 : 	armsSpears.drawFrames(stateTime, armsIndex % 6 * 4, 4, 100, 100);
			break;
			case 2 : 	armsSwords.drawFrames(stateTime, armsIndex % 6* 4, 4, 100, 100);
			break;
		}
	}
	
	@Override
	public void onKeyDown(int keycode) {
		switch(keycode){
			case Keys.A : 	armsIndex ++;
							break;
			case Keys.B : 	bodyIndex ++;
							break;
			case Keys.E : 	eyeIndex ++;
							break;
			case Keys.S : 	armsType ++;
							break;
		}
	}

		
}
