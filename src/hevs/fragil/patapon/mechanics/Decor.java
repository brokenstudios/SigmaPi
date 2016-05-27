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
	// Contain the objects to draw (trees, etc..)
	private Vector<DrawableObject> toDraw = new Vector<DrawableObject>();

	// Dynamic floor width
	public Decor(int w, int h, Color b) {
		this.width = w;
		this.setBackground(b);
		// processTree();
		Point<Float> origin = new Point<Float>(0f, (float) Param.FLOOR_DEPTH);
		processForest(8, origin, 5, 200f, 5);
	}

	/**
	 * The camera follow the units
	 * @param c1
	 * @return camera x position
	 */
	public float cameraProcess(Company c1){
		float pos = c1.getPosition();		
		
		return pos;
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
		cameraPos.y = 0;

		// Get the companies positions
		float x1 = c1.getPosition();
		float x2 = c2.getPosition();

		// TODO verify if both companies are close enough to be shown
		if (Math.abs(x2 - x1) < Param.WIN_WIDTH) {
			// TODO caracteriser le zoom mathematiquement, c'est a dire f(w,h)
		}

		if (x1 >= 0 && x1 < Param.WIN_WIDTH && x1 >= 0 && x1 < Param.WIN_WIDTH) {
			if (x1 < x2) {
				cameraPos.x = x2 - x1 + Param.CAMERAOFFSET;
			} else {
				cameraPos.x = x1 - x2 + Param.CAMERAOFFSET;
			}
		} else {
			// Input invalid!
			cameraPos.x = Param.CAMERAOFFSET;
			cameraPos.z = 0;
		}

		// TODO process camera position depending of objects position AND POV
		// TODO ensure that zoom is in limits (dynamic, process with height to
		// not display more than the map (or with the width if width<height)

		return cameraPos;
	}

	public void processTree() {
		Point<Float> pos = new Point<Float>(500f, (float) Param.FLOOR_DEPTH);
		toDraw.addElement(new Tree(pos, 3, 200f, 5));
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