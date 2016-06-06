package hevs.fragil.patapon.units;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.mechanics.CurrentLevel;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.mechanics.State;
import hevs.fragil.patapon.physics.BodyPolygon;

public abstract class Unit implements DrawableObject {
	protected boolean isEnnemi;
	private Species species = Species.TAPI;
	
	protected Skills skills ;
	protected UnitRender render ;
	
	protected float cooldownCounter;
	
	protected int collisionGroup;
	private BodyPolygon hitBox;
	

	Unit(int lvl, Species s, int attack, int defense, int life, int distance, int rangeMin, int rangeMax, float cooldown, boolean isEnnemi) {
		skills = new Skills(lvl, life, attack, rangeMax, rangeMin, defense, (float) (1f + Math.random() / 2.0));
		this.isEnnemi = isEnnemi;
		this.species = s;
		
		render = new UnitRender(4 * species.ordinal() + lvl);
		
		if (isEnnemi)
			this.collisionGroup = Param.ENNEMIES_GROUP;
		else
			this.collisionGroup = Param.HEROES_GROUP;
	}

	public void setPosition(int newPos, double totalTime) {
		if (hitBox != null)
			hitBox.moveToLinear(newPos, totalTime);
		else {
			hitBox = new BodyPolygon(new Vector2(newPos, Param.FLOOR_DEPTH), collisionGroup, skills.getLife());
			hitBox.getBody().setFixedRotation(true);
		}
	}

	protected Vector2 getPosition() {
		if (hitBox != null)
			return hitBox.getBodyWorldCenter();
		else
			return new Vector2(0, 0);
	}

	protected void setLife(int life) {
		this.skills.setLife(life);
	}

	protected abstract Gesture getAttackGesture();
	protected abstract float getAttackDelay();

	public void setState(State s) {
		render.setState(s);
	}

	public void receive(float damage) {
		if (damage > getDefense()){
			if(this.hitBox.applyDamage(damage)){
				render.setState(State.DYING);
			}
		}
	}

	private float getDefense() {
		if (render.getState() == State.DEFEND)
			return skills.getDefense();
		else
			return 0;
	}

	

	@Override
	public void draw(GdxGraphics g) {
		float x = Math.round(getPosition().x - g.getCamera().position.x + Param.CAM_WIDTH / 2);
		float y = Math.round(getPosition().y - g.getCamera().position.y + Param.CAM_HEIGHT / 2 - 37);
		float angle = hitBox.getBodyAngle();
		
		if (unitsInRange()) 
			render.setExpression(Expression.ANGRY);
		else 
			render.setExpression(Expression.DEFAULT);
	
		render.draw(g,x,y,angle);
	}

	public void setDelay(int delay) {
		skills.setCooldown(delay);
	}

	public float getDelay() {
		return skills.getCooldown();
	}

	public void setCollisionGroup(int group) {
		collisionGroup = group;
		hitBox.setCollisionGroup(group);
	}

	public float getLife() {
		return hitBox.getLife();
	}

	public Skills getSkills() {
		return skills;
	}

	public boolean isDead() {
		if (getLife() <= 0) {
			render.setState(State.DYING);
			render.setExpression(Expression.DYING);
			//decrease opacity until total disappear
			return render.die();
		}
		else return false;
	}

	public void destroyBox() {
		hitBox.destroy();
	}

	
	protected abstract void attack();
	
	
	public void attackRoutine(){
		float dt = Gdx.graphics.getDeltaTime();
		cooldownCounter += dt;
		if(cooldownCounter >= getCooldown()){
			attack();
			cooldownCounter = 0;
		}
		if (cooldownCounter >= getCooldown() - getAttackDelay()){
			render.launch(getAttackGesture());
		}
	}

	public void applyImpulse(int intensity) {
		Vector2 pos = hitBox.getBodyPosition();
		Vector2 force = new Vector2(intensity, 0);
		hitBox.applyBodyLinearImpulse(force, pos, true);
	}

	public void setExpression(Expression exp) {
		render.setExpression(exp);
	}

	public int getEndurance() {
		int defense = 0;
		if (render.getState() == State.DEFEND)
			defense = skills.getDefense();
		return skills.getLife() + defense;
	}

	public boolean isFatal(int damage) {
		boolean fatal = false;
		if (damage >= getEndurance())
			fatal = true;
		return fatal;
	}

	protected Vector<Unit> getUnitsInRange() {
		Vector<Unit> unitsInRange = new Vector<Unit>();
		Company ennemies = CurrentLevel.getLevel().getEnnemies();
		for (Section s : ennemies.sections) {
			for (Unit u : s.units) {
				float distance = u.getPosition().x - this.getPosition().x;
				// Subtraction of a half-sprite to find center2center distance
				distance = Math.abs(distance) - 64;
				if (distance < this.skills.getRangeMax()) {
					unitsInRange.add(u);
				}
			}
		}
		return unitsInRange;
	}

	protected boolean unitsInRange() {
		Company ennemies = CurrentLevel.getLevel().getEnnemies();
		for (Section s : ennemies.sections) {
			for (Unit u : s.units) {
				float distance = u.getPosition().x - this.getPosition().x;
				// Subtraction of a half-sprite to find center2center distance
				distance = Math.abs(distance) - 64;
				if (distance < this.skills.getRangeMax()) {
					return true;
				}
			}
		}
		return false;
	}

	protected abstract float findBestPosition();

	public void move() {
		if (findBestPosition() != getPosition().x)
			setPosition((int) findBestPosition(), Math.abs(getPosition().x - findBestPosition()) / Param.UNIT_SPEED);
	}

	public float getCooldown() {
		return skills.getCooldown();
	}

	public float getCounter() {
		return cooldownCounter;
	}

	public String toString() {
		return ", Level : " + skills.getLevel() + ", Life : " + skills.getLife();
	}
	
	/** This is only to load files in the PortableApplication onInit method */
	public void setLegsSprite(String url, int cols, int rows, boolean isEnnemi) {
		render.setLegsSprite(url, cols, rows, isEnnemi);
	}

	/** This is only to load files in the PortableApplication onInit method */
	public void setBodySprite(String url, int cols, int rows) {
		render.setBodySprite(url, cols, rows);
	}

	/** This is only to load files in the PortableApplication onInit method */
	public void setEyeSprite(String url, int cols, int rows) {
		render.setEyeSprite(url, cols, rows);
	}

	/** This is only to load files in the PortableApplication onInit method */
	public void setArmsSprite(String url, int cols, int rows) {
		render.setArmsSprite(url, cols, rows);
	}
}
