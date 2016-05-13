package hevs.fragil.patapon.mechanics;

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
	
	public Decor(int w, int h){
		this(Param.WIN_WIDTH, Param.WIN_HEIGHT, Param.BACKGROUND);
	}
	
	public Decor(int w){
		this(w, Param.WIN_HEIGHT, Param.BACKGROUND);
	}
	
	// Dynamic floor width
	public Decor(int w, int h, Color b){
		this.width = w;
		this.setBackground(b);
	} 
	
	//Gestion de la camera par level en fonction des parametres de map?
	//This method center the camera on the given point
	public float cameraProcess(int x1){
		return cameraProcess(x1, 0);
	}
	
	public float cameraProcess(int x1, int x2){
		float camPos = x1;
		
		//TODO process camera position depending of objects position AND POV
		//TODO ensure that camera isn't out of screen, in this case default position
		
		return camPos;
	}
	
	public float cameraZoom(){
		float zoom = 0f;
		
		//TODO ensure that zoom is in limits (dynamic, process with height to not
		//display more than the map (or with the width if width<height)
		
		return zoom;
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
