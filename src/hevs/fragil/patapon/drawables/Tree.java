package hevs.fragil.patapon.drawables;

import java.util.Random;

import ch.hevs.gdx2d.components.geometry.Point;
import ch.hevs.gdx2d.components.graphics.Turtle;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.mechanics.Param;

public class Tree implements VisibleObject {

	private Random r;
	private long seed;
	private int time = 0;
	private int complexity;
	private Point<Float> location;
	private Turtle t;
	private float size;
	private int width;

	public Tree(Point<Float> pos, int complexity, float size, int width) {
		this.location = pos;
		this.seed = (long) (Math.random() * 1000);
		this.r = new Random(seed);
		this.complexity = complexity;
		this.size = size;
		this.width = width;
	}

	/**
	 * Draws a line {@code length} long
	 * 
	 * @param length
	 *            length of the branch
	 * @author loicg
	 */
	private void drawDoubleLine(double length, double width) {
		// draw extern line
		t.turn(-90);
		t.penUp();
		t.forward(width);
		t.turn(90);
		t.penDown();
		t.forward(length);
		double oldAngle = t.getTurtleAngle();
		t.penUp();
		t.turn(90);
		t.forward(width);
		Point<Float> oldPos = t.getPosition();
		t.forward(width);

		// draw intern line
		t.turn(90);
		t.penDown();
		t.forward(length);
		// return to old pos
		t.jump(oldPos.x, oldPos.y);
		t.setAngle(oldAngle);
		t.penUp();
	}

	private void drawLine(double length, double width) {
		if (r.nextDouble() > 0.5)
			drawDoubleLine(length, width);
		else {
			t.penDown();
			t.forward(length);
		}
		t.penUp();
	}

	private void drawHexaBranch(double length, double width) {
		// hexa node
		if (r.nextDouble() > 0.2) {
			drawLine(length / 3, width);
			drawHexagon(length / 6);
			drawLine(length / 3, width);
		} else {
			drawLine(length / 3, width);
		}
	}

	/**
	 * Verifies {@code value} is between {@code min} and {@code max}, then
	 * change it if out of range
	 * 
	 * @param value
	 *            the value to limit
	 * @param min
	 *            minimum value for {@code value}
	 * @param max
	 *            maximum value for {@code value}
	 * @author loicg
	 * @return
	 */
	private double ensureRange(double value, double min, double max) {
		return Math.min(Math.max(value, min), max);
	}

	private void drawHexaPart(double length) {
		// draw extern line
		t.forward(length);
		Point<Float> oldPos = t.getPosition();
		double oldAngle = t.getTurtleAngle();

		t.penUp();
		t.turn(120);
		t.forward(length / 3);

		// draw intern line
		t.turn(60);
		t.penDown();
		t.forward(length * 2 / 3);
		// return to old pos
		t.jump(oldPos.x, oldPos.y);
		t.setAngle(oldAngle);
	}

	private void drawSimpleHexagon(double length) {
		t.penDown();
		t.turn(-60);
		for (int i = 0; i < 6; i++) {
			t.forward(length);
			;
			t.turn(60);
		}
		t.penUp();
		t.turn(60);
		t.forward(2 * length);
	}

	private void drawDoubleHexagon(double length) {
		t.penDown();
		t.turn(-60);
		for (int i = 0; i < 6; i++) {
			if (i % 2 == 0)
				drawHexaPart(length);
			else
				t.forward(length);
			;
			t.turn(60);
		}
		t.penUp();
		t.turn(60);
		t.forward(2 * length);
	}

	private void drawHexagon(double length) {
		if (r.nextDouble() > 0.5)
			drawDoubleHexagon(length);
		else
			drawSimpleHexagon(length);
	}

	/**
	 * Draws a random tree
	 * 
	 * @param n
	 *            complexity of recursive function (n sub-calls)
	 * @param length
	 *            length of the first branch
	 * @param width
	 *            width of the first branch
	 * @param leavesColor
	 *            average color of the leaves
	 * @author loicg
	 */
	private void drawHexaTree(int n, double length, int width) {
		// basis width
		t.setWidth(width);
		int factor = (complexity - n) * 10;
		if (n > 1) {
			// draw basis
			drawHexaBranch(length, width);

			// save Y embranchement
			Point<Float> oldPos = t.getPosition();
			double oldAngle = t.getTurtleAngle();

			// next left tree (with new random factors)
			t.setAngle(oldAngle + 60 + (factor * Math.sin((time * 2 * Math.PI) / 360 + (r.nextDouble() * 10))));
			double newLength = length * 2 / 3;
			drawHexaTree(n - 1, newLength, (int) ensureRange(width * 2 / 3, 1, 10));

			// return to old Y embranchement
			t.jump(oldPos.x, oldPos.y);

			// next right tree (with new random factors)
			t.setAngle(oldAngle - 60 + (factor * Math.sin((time * 2 * Math.PI) / 360 + (r.nextDouble() * 10))));
			drawHexaTree(n - 1, newLength, (int) ensureRange(width * 2 / 3, 1, 10));

		} else
			drawHexaBranch(length, width);
	}

	@Override
	public void draw(GdxGraphics g) {		
		//Check if the trees are on screen
		if(isVisible(g, location.x)){
			if (t != null) {
				// for oscillation
				time += 4;
				t.jump(location.x, location.y);
				t.setAngle(90);
				// reset values (get the same tree as last one)
				r.setSeed(seed);
				drawHexaTree(complexity, size, width);
			} else
				t = new Turtle(g, Param.CAM_WIDTH, Param.CAM_HEIGHT);
	
		}
	}

	@Override
	public boolean isVisible(GdxGraphics g, float objectPos) {
		boolean visible;
		float camPosX = g.getCamera().position.x;
		
		if(objectPos < camPosX + Param.CAM_WIDTH && objectPos > camPosX - Param.CAM_WIDTH){
			visible = true;
		}
		else
			visible = false;
		
		return visible;
	}
}
