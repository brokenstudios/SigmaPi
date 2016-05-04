
import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import hevs.graphics.TurtleGraphics;

public class SigmaTrees {
	private static int windowWidth = 1000;
	private static int windowHeight = 800;
	private static TurtleGraphics pen = new TurtleGraphics(windowWidth,windowHeight);
	//TODO oscillate 
	private Random r ;
	private int time = 0;
	private int nmax;
	
	public SigmaTrees(){
		long seed = (long) (Math.random()*1000);
		r = new Random(seed);
		
		nmax = 6;
		
		while(true){
			r.setSeed(seed);
			pen.jump(500,600);
			pen.setAngle(-90);
			pen.clear();
			drawHexaTree(nmax, 200, 7);
			time += 4;
			pen.syncGameLogic(30);
		}
	}
	public static void main(String[] args) {
		SigmaTrees t1 = new SigmaTrees();
	}
	/**
	 * Draws a line {@code length} long
	 * @param 	length 		length of the branch
	 * @author 	loicg
	 */
	private void drawDoubleLine(double length, double width){
		//draw extern line
		pen.turn(-90);
		pen.penUp();
		pen.forward(width);
		pen.turn(90);
		pen.penDown();
		pen.forward(length);
		double oldAngle = pen.getTurtleAngle(); 
		pen.penUp();
		pen.turn(90);
		pen.forward(width);
		Point oldPos = pen.getPosition();
		pen.forward(width);
		
		//draw intern line
		pen.turn(90);
		pen.penDown();
		pen.forward(length);
		//return to old pos
		pen.jump(oldPos.x,oldPos.y);
		pen.setAngle(oldAngle);
	}
	private void drawLine(double length, double width){
		if(r.nextDouble() > 0.5)
			drawDoubleLine(length, width);
		else 
			pen.forward(length);
	}
	private void drawHexaBranch (double length, double width){
		//hexa node
		if(r.nextDouble() > 0.2){
			pen.penDown();
			drawLine(length/3, width);
			drawHexagon(length/6);
			pen.penDown();
			drawLine(length/3, width);
			pen.penUp();
		} 
		else{
			pen.penDown();
			drawLine(length/3, width);
			pen.penUp();
		}
	}
	/**
	 * Verifies {@code value} is between {@code min} and {@code max}, then change it if out of range
	 * @param value	the value to limit
	 * @param min	minimum value for {@code value} 
	 * @param max	maximum value for {@code value} 
	 * @author loicg
	 * @return
	 */
	private double ensureRange(double value, double min, double max) {
		   return Math.min(Math.max(value, min), max);
		}
	private void drawHexaPart(double length){
		//draw extern line
		pen.forward(length);
		Point oldPos = pen.getPosition();
		double oldAngle = pen.getTurtleAngle(); 
		
		pen.penUp();
		pen.turn(120);
		pen.forward(length/3);
		
		//draw intern line
		pen.turn(60);
		pen.penDown();
		pen.forward(length*2/3);
		//return to old pos
		pen.jump(oldPos.x,oldPos.y);
		pen.setAngle(oldAngle);
	}
	private void drawSimpleHexagon(double length){
		pen.penDown();
		pen.turn(-60);
		for(int i = 0; i<6; i++){			
			pen.forward(length);;
			pen.turn(60);
		}
		pen.penUp();
		pen.turn(60);
		pen.forward(2*length);
	}
	private void drawDoubleHexagon(double length){
		pen.penDown();
		pen.turn(-60);
		for(int i = 0; i<6; i++){			
			if(i%2==0)
				drawHexaPart(length);
			else 
				pen.forward(length);;
			pen.turn(60);
		}
		pen.penUp();
		pen.turn(60);
		pen.forward(2*length);
	}
	private void drawHexagon(double length){
		if(r.nextDouble() > 0.5)
			drawDoubleHexagon(length);
		else 
			drawSimpleHexagon(length);
	}
	/**
	 * Draws a random tree
	 * @param n				complexity of recursive function (n sub-calls)
	 * @param length 		length of the first branch
	 * @param width			width of the first branch
	 * @param leavesColor	average color of the leaves
	 * @author loicg
	 */
	private void drawHexaTree (int n, double length, int width){
		//basis width
		pen.setPenWidth(width);
		int factor = (nmax-n)*10;
		if(n > 1){
			//draw basis 
			drawHexaBranch(length, width);
			
			//save Y embranchement
			Point oldPos = pen.getPosition();
			double oldAngle = pen.getTurtleAngle(); 
			
			//next left tree (with new random factors)
//			 pen.setAngle(oldAngle + 60 + (factor * Math.sin( (time*2*Math.PI)/360)));
			pen.setAngle(oldAngle + 60 + (factor * Math.sin( (time*2*Math.PI)/360+(r.nextDouble()*10))));
			double newLength = length * 2/3;
			drawHexaTree(n-1, newLength, (int) ensureRange(width*2/3, 1, 10));
			
			//return to old Y embranchement
			pen.jump(oldPos.x,oldPos.y);
			
			//next right tree (with new random factors)
//			pen.setAngle(oldAngle - 60 + (factor * Math.sin( (time*2*Math.PI)/360)));
			pen.setAngle(oldAngle - 60 + (factor * Math.sin((time*2*Math.PI)/360+(r.nextDouble()*10))));
			drawHexaTree(n-1, newLength, (int) ensureRange(width*2/3, 1, 10));

		}
		else 
			drawHexaBranch(length, width);
	}
	/**
	 * Returns a color similar to the given one
	 * @param 	c 	basis color
	 * @return	a similar color
	 * @author loicg
	 */
	private Color getSimilarColor(Color c){
		int r,g,b;
			
		//value between 0.7 and 1.3 * color (+- 30%)
		r = (int)(c.getRed() * ensureRange(2*Math.random(), 0.7, 1.3));
		g = (int)(c.getGreen() * ensureRange(2*Math.random(), 0.7, 1.3));
		b = (int)(c.getBlue() * ensureRange(2*Math.random(), 0.7, 1.3));
		
		//value can't be higher than 255
		r = Math.min(255, r);
		g = Math.min(255, g);
		b = Math.min(255, b);
		
		return new Color(r, g, b);		
	}
}
