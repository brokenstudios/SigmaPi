package hevs.fragil.patapon.mechanics;

import java.util.Vector;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.physics.Floor;

public class Map implements DrawableObject{
	private int width;
	private int height;
	private Floor floor;
	//Contain the objects to draw (trees, etc..)
//	private Vector toDraw;
	//Contain all the maps created, allow to "load" them
//	private static Vector<Map> maps;
	
	public Map(){
		this(Param.WIN_WIDTH, Param.WIN_HEIGHT);
	}
	
	public Map(int w){
		this(w, Param.WIN_WIDTH);
	}
	
	// Dynamic floor width
	public Map(int w, int h){
		this.width = w;
		setFloor(new Floor(width));
//		maps.add(this);
	} 
	
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
	@Override
	public void draw(GdxGraphics g) {
		floor.draw(g);
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
	public Floor getFloor() {
		return floor;
	}
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	
	

	//TODO calculer les décors
	//Créer un vecteur avec tous les objets à donner au draw
	//TODO gestion de la caméra
	/*On peut connaitre la position de deux éléments qui se suivent, du coup
	on peut calculer la distance qu'il y a entre les deux et ainsi déterminer la 
	position et le champs de vision de la caméra.
	Récupérer les positions dans une liste?*/
}
