package hevs.fragil.patapon.drawables;

import hevs.fragil.patapon.mechanics.Param;

import java.awt.Color;
import java.util.Random;

import ch.hevs.gdx2d.components.geometry.Point;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

public class SigmaTrees extends Turtle implements DrawableObject{

	public SigmaTrees(int xPos, int complexity) {
		super(Param.WIN_WIDTH, Param.WIN_HEIGHT);
		seed = (long) (Math.random() * 1000);
		r = new Random(seed);
		this.complexity = complexity;
		this.position = xPos;
	}
	// TODO oscillate
	private Random r;
	private int time = 0;
	private int position = 0;
	private int complexity;
	private long seed;

//	public SigmaTrees(int complexity) {
//		seed = (long) (Math.random() * 1000);
//		r = new Random(seed);
//		this.complexity = complexity;
////		while (true) {
////			r.setSeed(seed);
////			jump(500, 600);
////			setAngle(-90);
////			// FIXME clear pen
////			// clear();
////			drawHexaTree(nmax, 200, 7);
////			time += 4;
////			// FIXME sync pen
////			// syncGameLogic(30);
////		}
//	}

	/**
	 * Draws a line {@code length} long
	 * 
	 * @param length
	 *            length of the branch
	 * @author loicg
	 */
	private void drawDoubleLine(GdxGraphics g, double length, double width) {
		// draw extern line
		turn(-90);
		penUp();
		forward(g, width);
		turn(90);
		penDown(g);
		forward(g, length);
		double oldAngle = getTurtleAngle();
		penUp();
		turn(90);
		forward(g, width);
		Point<Float> oldPos = getPosition();
		forward(g, width);

		// draw intern line
		turn(90);
		penDown(g);
		forward(g, length);
		// return to old pos
		jump(g, oldPos.x, oldPos.y);
		setAngle(oldAngle);
	}

	private void drawLine(GdxGraphics g, double length, double width) {
		if (r.nextDouble() > 0.5)
			drawDoubleLine(g, length, width);
		else
			forward(g, length);
	}

	private void drawHexaBranch(GdxGraphics g, double length, double width) {
		// hexa node
		if (r.nextDouble() > 0.2) {
			penDown(g);
			drawLine(g, length / 3, width);
			drawHexagon(g, length / 6);
			penDown(g);
			drawLine(g, length / 3, width);
			penUp();
		} else {
			penDown(g);
			drawLine(g, length / 3, width);
			penUp();
		}
	}

	/**
	 * Verifies {@code value} is between {@code min} and {@code max}, then
	 * change it if out of range
	 * 
	 * @param value the value to limit
	 * @param min minimum value for {@code value}
	 * @param max maximum value for {@code value}
	 * @author loicg
	 * @return
	 */
	private double ensureRange(double value, double min, double max) {
		return Math.min(Math.max(value, min), max);
	}

	private void drawHexaPart(GdxGraphics g, double length) {
		// draw extern line
		forward(g, length);
		Point<Float> oldPos = getPosition();
		double oldAngle = getTurtleAngle();

		penUp();
		turn(120);
		forward(g, length / 3);

		// draw intern line
		turn(60);
		penDown(g);
		forward(g, length * 2 / 3);
		// return to old pos
		jump(g, oldPos.x, oldPos.y);
		setAngle(oldAngle);
	}

	private void drawSimpleHexagon(GdxGraphics g, double length) {
		penDown(g);
		turn(-60);
		for (int i = 0; i < 6; i++) {
			forward(g, length);
			;
			turn(60);
		}
		penUp();
		turn(60);
		forward(g, 2 * length);
	}

	private void drawDoubleHexagon(GdxGraphics g, double length) {
		penDown(g);
		turn(-60);
		for (int i = 0; i < 6; i++) {
			if (i % 2 == 0)
				drawHexaPart(g, length);
			else
				forward(g, length);
			;
			turn(60);
		}
		penUp();
		turn(60);
		forward(g, 2 * length);
	}

	private void drawHexagon(GdxGraphics g, double length) {
		if (r.nextDouble() > 0.5)
			drawDoubleHexagon(g, length);
		else
			drawSimpleHexagon(g, length);
	}

	/**
	 * Draws a random tree
	 * 
	 * @param n complexity of recursive function (n sub-calls)
	 * @param length length of the first branch
	 * @param width width of the first branch
	 * @param leavesColor average color of the leaves
	 * @author loicg
	 */
	private void drawHexaTree(GdxGraphics g, int n, double length, int width) {
		// basis width
		setWidth(g, width);
		int factor = (complexity - n) * 10;
		if (n > 1) {
			// draw basis
			drawHexaBranch(g, length, width);

			// save Y embranchement
			Point<Float> oldPos = getPosition();
			double oldAngle = getTurtleAngle();

			// next left tree (with new random factors)
			// setAngle(oldAngle + 60 + (factor * Math.sin(
			// (time*2*Math.PI)/360)));
			setAngle(oldAngle
					+ 60
					+ (factor * Math.sin((time * 2 * Math.PI) / 360
							+ (r.nextDouble() * 10))));
			double newLength = length * 2 / 3;
			drawHexaTree(g, n - 1, newLength,
					(int) ensureRange(width * 2 / 3, 1, 10));

			// return to old Y embranchement
			jump(g, oldPos.x, oldPos.y);

			// next right tree (with new random factors)
			// setAngle(oldAngle - 60 + (factor * Math.sin(
			// (time*2*Math.PI)/360)));
			setAngle(oldAngle
					- 60
					+ (factor * Math.sin((time * 2 * Math.PI) / 360
							+ (r.nextDouble() * 10))));
			drawHexaTree(g, n - 1, newLength,
					(int) ensureRange(width * 2 / 3, 1, 10));

		} else
			drawHexaBranch(g, length, width);
	}

	/**
	 * Returns a color similar to the given one
	 * 
	 * @param c basis color
	 * @return a similar color
	 * @author loicg
	 */
	private Color getSimilarColor(Color c) {
		int r, g, b;

		// value between 0.7 and 1.3 * color (+- 30%)
		r = (int) (c.getRed() * ensureRange(2 * Math.random(), 0.7, 1.3));
		g = (int) (c.getGreen() * ensureRange(2 * Math.random(), 0.7, 1.3));
		b = (int) (c.getBlue() * ensureRange(2 * Math.random(), 0.7, 1.3));

		// value can't be higher than 255
		r = Math.min(255, r);
		g = Math.min(255, g);
		b = Math.min(255, b);

		return new Color(r, g, b);
	}

	@Override
	public void draw(GdxGraphics g) {
		
		r.setSeed(seed);
		jump(g, position, Param.FLOOR_DEPTH);
		setAngle(-90);
		drawHexaTree(g, complexity, 200, 7);
		
	}
}
