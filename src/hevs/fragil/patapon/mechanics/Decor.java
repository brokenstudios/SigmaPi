package hevs.fragil.patapon.mechanics;

import java.util.Vector;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import ch.hevs.gdx2d.components.geometry.Point;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.drawables.Tree;
import hevs.fragil.patapon.units.Company;

public class Decor {
	private int width;
	private int height;
	private Color background;
	private Vector3 cameraPos;
	//Contain the objects to draw (trees, etc..)
	private Vector<DrawableObject> toDraw = new Vector<DrawableObject>();
	
	// Dynamic floor width
	public Decor(int w, int h, Color b){
		this.width = w;
		this.setBackground(b);
//		drawForest(3, 4, 120f);
	}
	
	public Vector3 cameraProcess(Company c1, Company c2){
		//Camera always stick on the floor
		cameraPos.y = 0;
		
		//Get the companies positions
		int x1 = c1.globalPosition;
		int x2 = c2.globalPosition;
		
		//TODO verify if both companies are close enough to be shown
		if(Math.abs(x2-x1) < Param.WIN_WIDTH){
			//TODO caracteriser le zoom mathematiquement, c'est a dire f(w,h)
		}
		
		if(x1 >= 0 && x1 < Param.WIN_WIDTH && x1 >= 0 && x1 < Param.WIN_WIDTH){
			if(x1 < x2){
				cameraPos.x = x2-x1 + Param.CAMERAOFFSET;
			}
			else{
				cameraPos.x = x1-x2 + Param.CAMERAOFFSET;
			}
		}
		else{
			//Input invalid!
			cameraPos.x = Param.CAMERAOFFSET;
			cameraPos.z = 0;
		}
		
		//TODO process camera position depending of objects position AND POV
		//TODO ensure that zoom is in limits (dynamic, process with height to not
		//display more than the map (or with the width if width<height)
		
		return cameraPos;
	}
	
	public void drawATree(){
		Point<Float> pos = new Point<Float>(500f,(float)Param.FLOOR_DEPTH);
		toDraw.addElement(new Tree(pos,5,200f,5));
	}
	
	public void drawForest(int density, int medianComplexity, float medianHeight){
		Point<Float> pos;
		int medComplex = medianComplexity;
		float medHeight = medianHeight;
		
		for(int i = 0; i < Param.MAP_WIDTH; i += Param.MAP_WIDTH/density){
			pos = new Point<Float>(200f+i, (float)Param.FLOOR_DEPTH);
			medComplex += (int)medianComplexity*Math.random()/2;
			medHeight += medianHeight*Math.random();
			
			toDraw.addElement(new Tree(pos, medComplex, medHeight, 4));
			
			medComplex = medianComplexity;
			medHeight = medianHeight;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeigth() {
		return height;
	}
	
	public void setHeigth(int heigth) {
		this.height = heigth;
	}
	
	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}	
	
	public void draw(GdxGraphics g){
		//TODO draw only trees that are in camera
		float camPosX = g.getCamera().position.x;
		float objPosX = 0;
		
		for (DrawableObject d : toDraw) {
//			if(objPosX > (camPosX - Param.WIN_WIDTH) && objPosX < (camPosX + Param.WIN_WIDTH)){"draw"}
			if(true){
				d.draw(g);				
			}
		}
	}
}