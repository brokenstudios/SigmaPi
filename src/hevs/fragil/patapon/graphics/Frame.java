package hevs.fragil.patapon.graphics;

import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;
import com.badlogic.gdx.graphics.Color;

public class Frame implements DrawableObject{
	static boolean display = true;
	@Override
	public void draw(GdxGraphics g) {
		int width = g.getScreenWidth();
		int height = g.getScreenHeight();
		int thickness = 4;
		//1:up, 2:right, 3:down, 4:left
		float[] xCenters = {width/2, width-thickness/2, width/2, thickness/2};
		float[] yCenters = {height-thickness/2, height/2, thickness/2, height/2};
		
		for(int i = 0; i < xCenters.length; i++){
			//rotation in degrees = i*90 
			g.drawFilledRectangle(xCenters[i], yCenters[i], width, thickness, i*90, Color.WHITE);
		}
	}
}
