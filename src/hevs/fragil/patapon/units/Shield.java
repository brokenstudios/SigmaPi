package hevs.fragil.patapon.units;

import java.util.Vector;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.CurrentLevel;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.Spear;

public class Shield extends Unit {
	static double modLife = +1.0;
	static Skills modifier;	
	static SpriteSheet arms;
	
	public Shield(){
		this(1,Species.random(), Param.HEROES_GROUP);
	}
	public Shield(int lvl, Species species, int collisionGroup){
		super(lvl, species, 10, 10, 10, 100, 50, 500, collisionGroup);
	}
	public String toString(){
		return this.getClass().getSimpleName() + super.toString();
	}
	private void drawArms(float time){
//		arms.drawKeyFrames(time, super.position);
	}
	@Override
	public void draw(GdxGraphics g) {
		float time = CurrentLevel.getLevel().getStateTime();
		super.draw(time);
		drawArms(time);
	}
	public void attack(int distance){
		Vector2 position = new Vector2(getPosition(), Param.FLOOR_DEPTH+30);
		new Spear(position, (int)(Math.random()*20) + 45 , distance, collisionGroup, level+5);
	}
	@Override
	public void attack(){
		for (Unit u : getUnitsInRange()) {
			u.receive(skills.getAttack());
			u.applyImpulse(4000);
		}
	}
	private Vector<Unit> getUnitsInRange(){
		Vector<Unit> unitsInRange = new Vector<Unit>();
		Company ennemies = CurrentLevel.getLevel().getEnnemies();
		for (Section s : ennemies.sections) {
			for (Unit u : s.units) {
				int distance = u.getPosition() - this.getPosition();
				distance = Math.abs(distance) - 64;
				if(distance < this.skills.getRange()){
					unitsInRange.add(u);
				}
			}
		}
		return unitsInRange;
	}
}