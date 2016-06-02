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
		new Spear(position, (int)(Math.random()*20) + 45 , distance, collisionGroup, level+5);
	}
	public void attack(){
		attack(600);
	}
	@Override
	public void draw(GdxGraphics g) {
		super.draw(g);
	}
	@Override
	protected float findBestPosition() {
		/*Vector<Unit> unitsInMap = new Vector<Unit>();
		Company enemies = CurrentLevel.getLevel().getEnnemies();
		
		//TODO get companies instead of units
		// Get enemies to place unit correctly
		for (Section s : enemies.sections) {
			for (Unit u : s.units) {
					unitsInMap.add(u);
			}
		}
		
//		if an enemy is in range, don't move, just shoot
//		else return destination
		float distance = Math.abs(getPosition().x - unitsInMap.get(1).getPosition().x);
		
		if(true);*/
		
		return getPosition().x;
	}
}
