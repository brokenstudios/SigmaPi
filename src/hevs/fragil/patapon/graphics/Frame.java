package hevs.fragil.patapon.graphics;

import hevs.fragil.patapon.others.Data;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.graphics.Color;

public class Frame implements DrawableObject{
	static boolean display = true;
	float alphaR = 1f;
	float alphaG = 1f;
	float alphaB = 1f;
	private int frames = 0;
	public static boolean blinkEnable = false;

	@Override
	public void draw(GdxGraphics g) {
		if(blinkEnable){
			int width = g.getScreenWidth();
			int height = g.getScreenHeight();
			int thickness = 10;
			//1:up, 2:right, 3:down, 4:left
			float[] xCenters = {width/2, width-thickness/2, width/2, thickness/2};
			float[] yCenters = {height-thickness/2, height/2, thickness/2, height/2};
			//TODO works only if frame is lighter than background
			alphaR -= (alphaR-Data.backColorR)/Data.FRAME_DURATION;
			alphaG -= (alphaG-Data.backColorG)/Data.FRAME_DURATION;
			alphaB -= (alphaB-Data.backColorB)/Data.FRAME_DURATION;
			
			for(int i = 0; i < xCenters.length; i++){
				//rotation in degrees = i*90 
				Color c = new Color(	Data.frameColorR * alphaR, 
										Data.frameColorG * alphaG, 
										Data.frameColorB * alphaB, 1);
				
				g.drawFilledRectangle(xCenters[i], yCenters[i], width, thickness, i*90, c);
			}
			frames++;
			
			if(frames == Data.FRAME_DURATION){
				blinkEnable = false;
				alphaR = 1f;
				alphaG = 1f;
				alphaB = 1f;
				frames = 0;
			}
		}
		
	}
}
