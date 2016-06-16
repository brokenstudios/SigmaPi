package hevs.fragil.patapon.units;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.Arrow;
import hevs.fragil.patapon.physics.Tower;

public class Archer extends Unit {
	//Skills modifiers
	static double modLife = -0.2;
	static Skills modifier;
	static int nArchers = 0;
	
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
		Vector2 position = new Vector2(getPosition().x, Param.FLOOR_DEPTH+30);
		if(distance > 0)
			new Arrow(position, (int)(Math.random()*20) + 45 , distance, collisionGroup, skills.getLevel() + 5);
		else
			new Arrow(position, 180-(int)(Math.random()*20) + 45 , -distance, collisionGroup, skills.getLevel() + 5);
	}
	@Override
	public void attack() {
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
	protected float getAttackDelay() {
		return 0.6f;
	}
	@Override
	protected Color getColor() {
		return Color.BLUE;
	}
	@Override
	protected String getUrl() {
		return "data/images/arms_bows.png";
	}
}
