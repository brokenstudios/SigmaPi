package hevs.fragil.patapon.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.CurrentLevel;
import hevs.fragil.patapon.mechanics.State;

public class UnitRender {
	private Expression expression = Expression.DEFAULT;
	private Gesture gesture = Gesture.WALK;
	private State state;
	private float opacity = 1f;
	
	private int bodyIndex;
	
	private SpriteSheet body, eye, arms, legs;
	
	protected float counter = -1;
	
	private Stabilizer pos;
	
	public UnitRender (int bodyIndex){
		this.bodyIndex = bodyIndex;
		this.pos = new Stabilizer();
	}
	public Expression getExpression() {
		return expression;
	}
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	public Gesture getArmnimation() {
		return gesture;
	}
	public void setArmnimation(Gesture armnimation) {
		this.gesture = armnimation;
	}

	public void setState(State s) {
		state = s;
	}
	public State getState() {
		return state;
	}

	public void draw(GdxGraphics g, float x, float y, float angle) {
		if (state == State.DYING) 
			drawDead(g, pos.stabilized(x,y), angle);
		else 
			drawAlive(g, pos.stabilized(x,y));
	}
	private void drawAlive(GdxGraphics g, Vector2 position) {
		armsAnimation();
		
		float stateTime = CurrentLevel.getLevel().getStateTime();
		
		int legsIndex = legs.drawAllFrames(stateTime, pos.stabilized(position));
		body.drawWalkAnimation(legsIndex, bodyIndex, pos.stabilized(position).x, pos.stabilized(position).y + 10, 32, 38);
		eye.drawWalkAnimation(legsIndex, expression.ordinal(), pos.stabilized(position).x, pos.stabilized(position).y + 22, 32, 38);
		arms.drawFrames(stateTime, gesture.ordinal() * 4, 4, pos.stabilized(position));
	}

	private void drawDead(GdxGraphics g, Vector2 position, float angle) {
		armsAnimation();
		
		legs.drawRotatedFrameAlpha(0, angle, pos.stabilized(position), -32, -35, opacity);
		body.drawRotatedFrameAlpha(0, angle, pos.stabilized(position), -32, -25, opacity);
		eye.drawRotatedFrameAlpha(expression.ordinal(), angle, pos.stabilized(position), -32, -13, opacity);
	}
	private void armsAnimation() {
		float dt = Gdx.graphics.getDeltaTime();
		
		if(counter >= 0){
			counter += dt;
		}
		if(counter >= 4 * arms.getFrameDuration()){
			gesture = Gesture.WALK;
			counter = -1;
		}
	}
	protected void launch(Gesture a) {
		setArmnimation(a);
		if(counter == -1)
			counter = 0;
	}
	public boolean die() {
		opacity -= 0.005f;
		if (opacity <= 0) {
			return true;
		}
		return false;
	}
	/** This is only to load files in the PortableApplication onInit method */
	public void setLegsSprite(String url, int cols, int rows, boolean isEnnemi) {
		legs = new SpriteSheet(url, cols, rows, 0.2f, isEnnemi, true);
	}

	/** This is only to load files in the PortableApplication onInit method */
	public void setBodySprite(String url, int cols, int rows) {
		body = new SpriteSheet(url, cols, rows, 0.2f, false, true);
	}

	/** This is only to load files in the PortableApplication onInit method */
	public void setEyeSprite(String url, int cols, int rows) {
		eye = new SpriteSheet(url, cols, rows, 0.2f, false, true);
	}

	/** This is only to load files in the PortableApplication onInit method */
	public void setArmsSprite(String url, int cols, int rows) {
		arms = new SpriteSheet(url, cols, rows, 0.2f, false, false);
	}
}
