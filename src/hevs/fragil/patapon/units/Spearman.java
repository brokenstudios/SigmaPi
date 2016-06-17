package hevs.fragil.patapon.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.Spear;
import hevs.fragil.patapon.physics.Tower;

public class Spearman extends Unit {
	static double modLife = +0.2;
	static Skills modifier;	
	static SpriteSheet arms;
	
	public Spearman(){
		this(1,Species.random(), false);
	}
	public Spearman(int lvl, Species species, boolean isEnnemi){
		super(lvl, species, 10, 10, 10, 100, 100, 300, 0.5f, isEnnemi);
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	public void attack(int distance){
		Vector2 position = new Vector2(getPosition().x, Param.FLOOR_DEPTH+30);
		new Spear(position, (int)(Math.random()*20) + 45 , distance, collisionGroup, skills.getLevel() + 5);
	}
	public void attack(){
		if(getTowersInRange().isEmpty()){
			Unit victim = getUnitsInRange().elementAt((int)(Math.random()*getUnitsInRange().size()));
			int distance = (int)(victim.getPosition().x - getPosition().x);
			attack(distance+32);
		}
		else{
			Tower victim = getTowersInRange().elementAt((int)(Math.random()*getTowersInRange().size()));
			int distance = (int)(victim.getLeftLimit() - getPosition().x);
			attack(distance+50);
		}
	}
	@Override
	public void draw(GdxGraphics g) {
		super.draw(g);
	}
	@Override
	protected float getAttackDelay() {
		return 0.6f;
	}
	@Override
	protected Color getColor() {
		return Color.GRAY;
	}
	@Override
	protected String getUrl() {
		return "data/images/arms_spears.png";
	}
}
