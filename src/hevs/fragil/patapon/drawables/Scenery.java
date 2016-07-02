package hevs.fragil.patapon.drawables;

import java.util.Vector;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.BasicTower;
import hevs.fragil.patapon.physics.HexaTower;
import hevs.fragil.patapon.units.Company;

/**
 * Scenery class to manage every item drawn in the game window. 
 * Principally manages camera moves and dynamic visible elements drawing.
 * For instance, this class creates its own elements given in the constructor.
 * TODO This class should be able to read some map files and instantiate them.
 */
public class Scenery {
	private int width;
	private int height;
	private int manualOffset;
	private Color background;
	private Vector3 camera = new Vector3();
	public Vector<DrawableObject> toDraw = new Vector<DrawableObject>();

	/**
	 * Scenery constructor
	 * @param w	define the map width
	 * @param h define the map height
	 * @param b define the map background color
	 */
	public Scenery(int w, int h, Color b) {
		this.width = w;
		this.setBackground(b);
		// Calculate a forest
		toDraw.add(new Clouds(100, 4));
		toDraw.add(new Mountains(0, 2));
		toDraw.add(new Mountains(3800, 2));
		processForest(10, 0, 5, 200f, 5);
		toDraw.add(new BasicTower(1500, 10));
		toDraw.add(new BasicTower(2000, 10));
		toDraw.add(new BasicTower(4000, 10));
		toDraw.add(new HexaTower(5500, 10));		
	}

	/**
	 * The camera follows the given company
	 * @param c1
	 * @return camera x position
	 */
	public Vector3 cameraProcess(Company c1){
		if(c1.isEmpty())
			return new Vector3(0,0,0);
		
		camera.x = c1.getPosition() + Param.CAM_OFFSET + manualOffset;
		camera.y = 0;
		camera.z = 0;
		
		return camera;
	}
	
	/**
	 * The camera is placed depending of both companies positions with 
	 * priority to the player company.
	 * @param c1
	 * @param c2
	 * @return camera's complete position (x, y, z)
	 */
	public Vector3 cameraProcess(Company c1, Company c2) {
		if(c1.isEmpty())
			return cameraProcess(c2);
		if(c2.isEmpty())
			return cameraProcess(c1);
		
		// Process absolute distance
		float x1 = c1.getPosition();
		float x2 = c2.getPosition();
		float absDistance = Math.abs(x2 - x1);
		
		// Camera always stick on the floor
		camera.x = x1 + Param.CAM_OFFSET + manualOffset;
		camera.y = 0;

		// When companies are not so far, camera will dezoom to show both
		if(absDistance < Param.CAM_RANGE){
			camera.z = 5;
		}
		
		// When companies are close enough OR if enemies too far camera follow heroes
		else if (absDistance < Param.CAM_WIDTH || absDistance > Param.CAM_RANGE) {
			camera.z = 1;
		}	
		
		// Input invalid!
		else {
			camera.x = Param.CAM_OFFSET + manualOffset;
			camera.z = 1;
		}
		
		// TODO work only with a return new Vector3?
		return camera;
	}

	/**
	 * Process a simple tree at given position
	 * @param position
	 */
	public void processTree(int position) {
		toDraw.add(new Tree(position, 3, 200f, 5));
	}

	/**
	 * Process a forest with following median parameters
	 * @param density		give the forest density
	 * @param origin		give the first tree position
	 * @param complexity	give the trees complexity
	 * @param size			give the trees size
	 * @param penWidth		give the pen width, so the tree basic width
	 */
	public void processForest(int density, int x, int complexity, float size, int penWidth) {
		float ratio = width/density;
		
		for (int i = 0; i < density; i++) {
			x += ratio;
			
			// Add some "natural" rendering
			float randomOffset = (float)(ratio*Math.random());
			if(randomOffset < ratio/3 && randomOffset > 0){
				if(Math.random() > 0.5f)
					x += randomOffset;
				else
					x -= randomOffset;
			}
			
			toDraw.add(new Tree(x, complexity, size, penWidth));
			
			randomOffset = 0;
		}
	}

	/* Here are all the getters and setters */
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

	public void draw(GdxGraphics g) {
		for (DrawableObject d : toDraw) {
			d.draw(g);
		}
	}
	
	public int getManualOffset(){
		return manualOffset;
	}
	
	public void setManualOffset(int newValue){
		manualOffset = newValue;
	}
	
	/**
	 * Allow to move the camera manually.
	 * @param amountPixels : user given offset
	 */
	public void addManualOffset(int amountPixels){
		// Set a minimal value to camera (placed by center of window)
		manualOffset += amountPixels;;
	}
	
	/**
	 * Automatic camera centering on player company by setting the camera offset to zero
	 */
	public void centerCamera(){
		manualOffset = 0;
	}
}