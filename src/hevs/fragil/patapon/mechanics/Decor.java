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
		processTree();
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
	public void processTree(){
		Point<Float> pos = new Point<Float>(500f,(float)Param.FLOOR_DEPTH);
		toDraw.addElement(new Tree(pos,3,200f,5));
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
		for (DrawableObject d : toDraw) {
			d.draw(g);
		}
	}

	//TODO calculer les décors
	//Créer un vecteur avec tous les objets à donner au draw
	//TODO gestion de la caméra
	/*On peut connaitre la position de deux éléments qui se suivent, du coup
	on peut calculer la distance qu'il y a entre les deux et ainsi déterminer la 
	position et le champs de vision de la caméra.
	Récupérer les positions dans une liste?*/
}