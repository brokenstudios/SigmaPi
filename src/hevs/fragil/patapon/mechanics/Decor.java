package hevs.fragil.patapon.mechanics;

import hevs.fragil.patapon.units.Company;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class Decor {
	private int width;
	private int height;
	private Color background;
	private Vector3 cameraPos;
	//Contain the objects to draw (trees, etc..)
//	private Vector toDraw;
	
	public Decor(){
		this(Param.WIN_WIDTH, Param.WIN_HEIGHT, Param.BACKGROUND);
	}	
	public Decor(Color b) {
		this(Param.WIN_WIDTH, Param.WIN_HEIGHT, b);
	}	
	public Decor(int w){
		this(w, Param.WIN_HEIGHT, Param.BACKGROUND);
	}	
	public Decor(int w, int h){
		this(Param.WIN_WIDTH, Param.WIN_HEIGHT, Param.BACKGROUND);
	}	
	// Dynamic floor width
	public Decor(int w, int h, Color b){
		this.width = w;
		this.setBackground(b);
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
	
	

	//TODO calculer les décors
	//Créer un vecteur avec tous les objets à donner au draw
	//TODO gestion de la caméra
	/*On peut connaitre la position de deux éléments qui se suivent, du coup
	on peut calculer la distance qu'il y a entre les deux et ainsi déterminer la 
	position et le champs de vision de la caméra.
	Récupérer les positions dans une liste?*/
}
