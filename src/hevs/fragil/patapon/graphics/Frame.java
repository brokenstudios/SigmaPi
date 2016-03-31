package hevs.fragil.patapon.graphics;

import hevs.fragil.patapon.others.Data;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Frame implements DrawableObject{
	static boolean display = true;
	
	private boolean displayRythm(){
		
		return display;
	}
	
	@Override
	public void draw(GdxGraphics g) {
		int width = g.getScreenWidth();
		int heigth = 10;
		int x = g.getScreenWidth()/2;
		int y = 0 + heigth/2;
		
		if(displayRythm()){
		g.setColor(Color.WHITE);
			g.drawFilledRectangle(x, y, width, heigth, 0, Color.OLIVE);
		}
	}

}
