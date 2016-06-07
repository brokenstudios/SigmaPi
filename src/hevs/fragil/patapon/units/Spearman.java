package hevs.fragil.patapon.units;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.Spear;

public class Spearman extends Unit {
	static double modLife = +0.2;
	static Skills modifier;	
	static SpriteSheet arms;
	
	public Spearman(){
		this(1,Species.random(), false);
	}
	public Spearman(int lvl, Species species, boolean isEnnemi){
		super(lvl, species, 10, 10, 10, 100, 100, 100, 0.5f, isEnnemi);
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	public void attack(int distance){
		Vector2 position = new Vector2(getPosition().x, Param.FLOOR_DEPTH+30);
		new Spear(position, (int)(Math.random()*20) + 45 , distance, collisionGroup, skills.getLevel()+5);
	}
	public void attack(){
		attack(600);
	}
	@Override
	public void draw(GdxGraphics g) {
		super.draw(g);
	}
	@Override
	public Gesture getAttackGesture() {
		return Gesture.SPEARMAN;
	}
	@Override
	protected float getAttackDelay() {
		return 0.6f;
	}
}
