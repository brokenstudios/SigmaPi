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
	private Vector3 camera = new Vector3();
	// Contain the objects to draw (trees, etc..)
	private Vector<DrawableObject> toDraw = new Vector<DrawableObject>();

	// Dynamic floor width
	public Decor(int w, int h, Color b) {
		this.width = w;
		this.setBackground(b);
		Point<Float> origin = new Point<Float>(0f, (float) Param.FLOOR_DEPTH);
		processForest(8, origin, 5, 200f, 5);
	}

	/**
	 * The camera follow the given company
	 * @param c1
	 * @return camera x position
	 */
	public Vector3 cameraProcess(Company c1){
		camera.x = c1.getPosition();
		camera.y = 0;
		camera.z = 0;
		
		return camera;
	}
	
	/**
	 * The camera is placed depending of both companies positions with 
	 * priority to the player company.
	 * @param c1
	 * @param c2
	 * @return camera complete position (x, y, z)
	 */
	public Vector3 cameraProcess(Company c1, Company c2) {
		// Camera always stick on the floor
		camera.y = 0;

		float x1 = c1.getPosition();
		float x2 = c2.getPosition();

		// Here both companies are in window
		if (Math.abs(x2 - x1) < Param.CAM_WIDTH) {
			camera.x = x1 + Param.CAMERAOFFSET;
			camera.z = 0;
		
		// Will reduce zoom until both companies are visible
		} else if(Math.abs(x2 - x1) < Param.MAP_HEIGHT/Param.CAM_RATIO){
			for(int i = 0; i > -15; i--){
				camera.z = i;
				// TODO ask mui how camera zoom is linked with width/height, yet not found on the InternetS
			}
			
		} else {
			// Input invalid!
			camera.x = Param.CAMERAOFFSET;
			camera.z = 0;
			System.out.println("Camera cannot reach this position!");
		}
		
		return camera;
	}

	public void processTree(Point<Float> position) {
		toDraw.addElement(new Tree(position, 3, 200f, 5));
	}

	public void processForest(int density, Point<Float> origin, int complexity, float size, int penWidth) {
		float x = origin.x, y = origin.y;

		for (int i = 0; i < density; i++) {
			x += width/density;
			
			//TODO modifiy space, size, pen randomly
			
			toDraw.add(new Tree(new Point<Float>(x, y), complexity, size, penWidth));
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

	public void draw(GdxGraphics g) {
		for (DrawableObject d : toDraw) {
			d.draw(g);
		}
	}
}