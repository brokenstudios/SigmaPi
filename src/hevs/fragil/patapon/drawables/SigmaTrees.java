package hevs.fragil.patapon.drawables;

import java.util.Random;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

import com.badlogic.gdx.math.Vector2;

public class SigmaTrees extends Turtle implements DrawableObject {

	// TODO oscillate
	private Random r;
	private int time = 0;
	private Vector2 pos;
	private int complexity;
	private long seed;

	public SigmaTrees(Vector2 pos, int complexity) {
		super(pos);
		seed = (long) (Math.random() * 1000);
		r = new Random(seed);
		this.complexity = complexity;
		this.pos = pos;
	}

	
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
		forward(width);
		turn(90);
		penDown();
		forward(length);
		double oldAngle = getTurtleAngle();
		penUp();
		turn(90);
		forward(width);
		Vector2 oldPos = getPosition();
		forward(width);

		// draw intern line
		turn(90);
		penDown();
		forward(length);
		// return to old pos
		jump(oldPos);
		setAngle(oldAngle);
	}

	private void drawLine(GdxGraphics g, double length, double width) {
		if (r.nextDouble() > 0.5)
			drawDoubleLine(g, length, width);
		else
			forward(length);
	}

	private void drawHexaBranch(GdxGraphics g, double length, double width) {
		// hexa node
		if (r.nextDouble() > 0.2) {
			penDown();
			drawLine(g, length / 3, width);
			drawHexagon(g, length / 6);
			penDown();
			drawLine(g, length / 3, width);
			penUp();
		} else {
			penDown();
			drawLine(g, length / 3, width);
			penUp();
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

	private void drawHexaPart(GdxGraphics g, double length) {
		// draw extern line
		forward(length);
		Vector2 oldPos = getPosition();
		double oldAngle = getTurtleAngle();

		penUp();
		turn(120);
		forward(length / 3);

		// draw intern line
		turn(60);
		penDown();
		forward(length * 2 / 3);
		// return to old pos
		jump(oldPos);
		setAngle(oldAngle);
	}

	private void drawSimpleHexagon(GdxGraphics g, double length) {
		penDown();
		turn(-60);
		for (int i = 0; i < 6; i++) {
			forward(length);
			;
			turn(60);
		}
		penUp();
		turn(60);
		forward(2 * length);
	}

	private void drawDoubleHexagon(GdxGraphics g, double length) {
		penDown();
		turn(-60);
		for (int i = 0; i < 6; i++) {
			if (i % 2 == 0)
				drawHexaPart(g, length);
			else
				forward(length);
			;
			turn(60);
		}
		penUp();
		turn(60);
		forward(2 * length);
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
	private void drawHexaTree(GdxGraphics g, int n, double length, int width) {
		// basis width
		setWidth(width);
		int factor = (complexity - n) * 10;
		if (n > 1) {
			// draw basis
			drawHexaBranch(g, length, width);

			// save Y embranchement
			Vector2 oldPos = getPosition();
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
			jump(oldPos);

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

	@Override
	public void draw(GdxGraphics g) {
		if (super.g != null) {
			jump(pos);
			setAngle(-90);
			drawHexaTree(g, complexity, 100, 5);
		} else
			super.g = g;
	}
}
