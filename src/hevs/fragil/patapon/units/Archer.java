package hevs.fragil.patapon.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.Arrow;

public class Archer extends Unit {
	//Skills modifiers
	static double modLife = -0.2;
	static Skills modifier;
	static SpriteSheet arms;
	static int nArchers = 0;
	int force = level * 400;
	
	public Archer(){
		this((int)(4*Math.random()),Species.random(), false);
	}
	public Archer(int lvl, Species species, boolean isEnnemi){
		super(lvl, species, 10, 10, 10, 100, 500, 1000, .5f, isEnnemi);
		nArchers++;
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	public void attack(int distance){
		Vector2 position = new Vector2(getPosition(), Param.FLOOR_DEPTH+30);
		new Arrow(position, (int)(Math.random()*20) + 45 , distance, collisionGroup, level+5);
	}
	public void draw(GdxGraphics g, float time) {
		
	}
	@Override
	public void attack() {
		attack(skills.getRangeMax());
	}
	@Override
	public void draw(GdxGraphics g) {
		super.draw(g);
		g.drawFilledCircle(getPosition(), 40, 10, Color.BLUE);
		drawArms(g);
	}
	@Override
	protected int findBestPosition() {
		// TODO find the best position to shoot ennemies
		return getPosition();
	}
	@Override
	public void drawArms(GdxGraphics g) {
		// TODO Auto-generated method stub
		
	}
}
