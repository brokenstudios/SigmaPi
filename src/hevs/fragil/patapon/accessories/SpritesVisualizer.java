package hevs.fragil.patapon.accessories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import ch.hevs.gdx2d.components.screen_management.RenderingScreen;
import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.Param;

public class SpritesVisualizer extends RenderingScreen {
	SpriteSheet armsBows, armsSwords, armsSpears, legs, body, eye;
	int index, armsType;
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
		g.clear(Param.BACKGROUND);
		index = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++) {
				legs.drawAllFrames(stateTime, 120 + i * 200, 650 - j*150);
				body.drawFrame(index % 25, 120 + i *200, 650 - j*150);
				eye.drawFrame(i, 120 + i *200, 650 - j*150);
				switch(j % 3){
					case 0: 	armsBows.drawFrames(stateTime, i % 6 * 4, 4, 120 + i * 200,650 - j*150);
								break;
					case 1 : 	armsSpears.drawFrames(stateTime, i % 6 * 4, 4, 120 + i * 200,650 - j*150);
								break;
					case 2 : 	armsSwords.drawFrames(stateTime, i % 6 * 4, 4, 120 + i * 200,650 - j*150);
								break;
				}
				index ++ ;
			}
		}
		g.drawStringCentered(850, "SPRITESHEET VIEWER", Param.large);
	}
}
