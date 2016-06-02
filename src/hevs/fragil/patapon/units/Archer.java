package hevs.fragil.patapon.units;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.mechanics.CurrentLevel;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.Arrow;

public class Archer extends Unit {
	//Skills modifiers
	static double modLife = -0.2;
	static Skills modifier;
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
		Vector2 position = new Vector2(getPosition().x, Param.FLOOR_DEPTH+30);
		new Arrow(position, (int)(Math.random()*20) + 45 , distance, collisionGroup, level+5);
	}
	@Override
	public void attack() {
		attack(skills.getRangeMax());
	}
	@Override
	protected float findBestPosition() {
		// TODO find the best position to shoot ennemies
		return getPosition().x;
	}
	@Override
	public void drawArms(GdxGraphics g) {
		float stateTime = CurrentLevel.getLevel().getStateTime();
		int startIndex =  ArmsLine.ARCHER.ordinal() * 4;
		arms.drawFrames(stateTime, startIndex, 4, getDrawPosition(g).x, getDrawPosition(g).y);
	}
}
