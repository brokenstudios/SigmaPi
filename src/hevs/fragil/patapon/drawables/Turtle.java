package hevs.fragil.patapon.drawables;

import ch.hevs.gdx2d.components.geometry.Point;
import ch.hevs.gdx2d.lib.GdxGraphics;

import com.badlogic.gdx.graphics.Color;

/**
 * Graphics class that emulates the tortoise in the Logo programming language
 * The turtle starts looking up with a black color, pen up
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Turtle_graphics">Wikipedia
 *      description of Turtle Graphics</a>
 * @author <a href='mailto:pandre.mudry&#64;hevs.ch'> Pierre-Andre Mudry</a>
 */
public class Turtle {
	private float x;
	private float y;
	private boolean penDown = false;
	private double angle = -Math.PI / 2.0; // Current rotation
	private Color color = Color.BLACK;

	public Turtle(float fWidth, float fHeight) {
		x = fWidth / 2;
		y = fHeight / 2;
	}

	/**
	 * Start the writing
	 */
	public void penDown(GdxGraphics g) {
		penDown = true;
		// Write the pixel corresponding to the position
		Color oldColor = g.sbGetColor();
		g.setColor(color);
		g.setPixel((float) x, (float) y);
		g.setColor(oldColor);
	}

	/**
	 * Change the color of drawing
	 * 
	 * @param color
	 */
	public void changeColor(Color color) {
		this.color = color;
	}

	/**
	 * Stop the drawing
	 */
	public void penUp() {
		penDown = false;
	}

	/**
	 * Go forward the specified distance (writing if the pen is down)
	 * 
	 * @param distance The distance to move
	 */
	public void forward(GdxGraphics g, double distance) {
		// Compute new position
		float newX = (float)(x + Math.cos(angle) * distance);
		float newY = (float)(y + Math.sin(angle) * distance);

		// Write if the pen is down
		if (penDown) {
			Color oldColor = g.sbGetColor();
			g.setColor(color);
			g.drawLine(x, y, newX, newY);
			g.setColor(oldColor);
		}
		
		x = newX;
		y = newY;
	}

	/**
	 * Jump to a specific location (without drawing)
	 * 
	 * @param x X coordinate of the destination
	 * @param y Y coordinate of the destination
	 */
	public void jump(GdxGraphics g, float x, float y) {
		this.x = x;
		this.y = y;

		// If the pen is down, draw a point at destination
		if (penDown) {
			Color oldColor = g.sbGetColor();
			g.setColor(color);
			g.setPixel(x, y);
			g.setColor(oldColor);
		}
	}

	/**
	 * @return The location of the turtle
	 */
	public Point<Float> getPosition() {
		return new Point<Float>(x, y);
	}

	/**
	 * Sets the width of the pen
	 * 
	 * @param w
	 */
	public void setWidth(GdxGraphics g, float w) {
		g.setPenWidth(w);
	}

	/**
	 * @return The current turtle angle (in degrees)
	 */
	public double getTurtleAngle() {
		return (this.angle * 180.0 / Math.PI);
	}

	/**
	 * Turn the direction of writing with the specified angle
	 * 
	 * @param angle specified angle in degrees
	 */
	public void turn(double angle) {
		this.angle += angle * Math.PI / 180;
	}

	/**
	 * Set the direction of writing to the specified angle
	 * 
	 * @param angle specified angle in degrees
	 */
	public void setAngle(double angle) {
		this.angle = angle * Math.PI / 180;
	}

	/**
	 * Turn the direction of writing with the specified angle
	 * 
	 * @param angle specified angle in radians
	 */
	public void turnRad(double angle) {
		this.angle += angle;
	}

	/**
	 * Set the direction of writing to the specified angle
	 * 
	 * @param angle specified angle in radians
	 */
	public void setAngleRad(double angle) {
		this.angle = angle;
	}

	public double getTurtleAngleRad() {
		return this.angle;
	}

}
