package hevs.fragil.patapon.units;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.mechanics.CurrentLevel;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.mechanics.PlayerCompany;
import hevs.fragil.patapon.mechanics.State;
import hevs.fragil.patapon.physics.BodyPolygon;

public abstract class Unit implements DrawableObject {
	protected boolean isEnemy;
	private Species species = Species.TAPI;
	
	protected Skills skills ;
	protected UnitRender render ;
	
	private float counter;
	
	private int attackStep;
	private int nAttacks;
	
	protected int collisionGroup;
	private BodyPolygon hitBox;
	

	Unit(int lvl, Species s, int attack, int defense, int life, int distance, int rangeMin, int rangeMax, float cooldown, boolean isEnnemi) {
		skills = new Skills(lvl, life, attack, rangeMin, rangeMax, defense, cooldown);
		this.isEnemy = isEnnemi;
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
	protected abstract Color getColor();

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
			render.setLook(Look.ANGRY);
		else if(unitsInSight() && !isEnemy)
			render.setLook(Look.RIGHT);
		else 
			render.setLook(Look.DEFAULT);
	
		render.draw(g,x,y,angle);
	
		// Some debug info (display unit range)
//		if(!isEnemy){
//			g.drawFilledRectangle(x + skills.getRangeMin(), y, 10, 10, 0, getColor());
//			g.drawFilledRectangle(x + skills.getRangeMax(), y, 10, 10, 0, getColor());
//		}
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
			render.setLook(Look.DYING);
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
		counter += dt;

		//Sort of state machine (PATATE MACHINE)
		switch(attackStep){
			case 0 :
				if(counter >= getCooldown()){
					//is remaining time sufficient for another shoot ?
					if(nAttacks < (int)(2f / (getCooldown()+0.8f))){
						//end of cooldown, launch animation
						render.launch(getAttackGesture());
						attackStep++;
						counter = 0;
					}
					//stuck in cooldown state until the end, when time insufficient
				}
				break;
			
			case 1 :
				if(counter >= getAttackDelay()){
					//animation pre shoot ended, shoot
					counter = 0;
					attack();
					nAttacks++;
					attackStep++;
				}
				break;
			
			case 2 :
				if(counter >= 0.8f - getAttackDelay()){
					//animation ended, retun to cooldown state
					counter = 0;
					attackStep = 0;
				}
				break;
		}
	}

	public void applyImpulse(int intensity) {
		Vector2 pos = hitBox.getBodyPosition();
		Vector2 force = new Vector2(intensity, 0);
		hitBox.applyBodyLinearImpulse(force, pos, true);
	}

	public void setExpression(Look exp) {
		render.setLook(exp);
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
				// Subtraction of two half-sprite to find center2center distance
				distance = Math.abs(distance) - 64;
				if (distance < skills.getRangeMax() && distance > skills.getRangeMin()) {
					unitsInRange.add(u);
				}
			}
		}
		return unitsInRange;
	}

	protected boolean unitsInRange() {
		if (getUnitsInRange().isEmpty()){
			return false;
		}
		return true;
	}
	
	protected boolean unitsInSight() {
		if (unitToEnemiDistance() < Param.SIGHT) {
			return true;
		}
		return false;
	}

	protected boolean unitsTooClose() {
		if (unitToEnemiDistance() < skills.getRangeMin()) {
			return true;
		}
		return false;
	}
	
	protected boolean unitsTooFar() {
		if (unitToEnemiDistance() > skills.getRangeMax()) {
			return true;
		}
		return false;
	}

	protected float unitToEnemiDistance() {
		Company enemies;
		if(isEnemy)enemies = PlayerCompany.getInstance().getHeroes();
		else enemies = CurrentLevel.getLevel().getEnnemies();
		
		float distance = -1;
		if(!enemies.isEmpty()){
			for (Section s : enemies.sections) {
				for (Unit u : s.units) {
					if(distance > Math.abs(u.getPosition().x - getPosition().x) || distance == -1)
						distance = Math.abs(u.getPosition().x - getPosition().x);
				}
			}
			// Subtract 64 (2 half-sprite) to match range (0 to x, ...)
			distance -= 64;
			return distance;			
		}
		
		return 0;
	}
	
	protected float unitToUnitDistance(Unit u1, Unit u2){
		return Math.abs(u1.getPosition().x - u2.getPosition().x);
	}
	
	/**
	 * Check if new position is available in company range
	 * @return true when allright
	 */
	protected boolean unitInCompanyRange(){
		float dt = Gdx.graphics.getDeltaTime();
		int newPos;
		Range companyRange = new Range(getPosition().x - Param.COMPANY_WIDTH, getPosition().x + Param.COMPANY_WIDTH);
		
		// First, process new position
		// Else if enemies are too close, set move to left
		if(unitsTooClose() && !isEnemy || unitsTooFar() && isEnemy){
			newPos = (int)(getPosition().x - Param.UNIT_SPEED * dt);			
		}
		// Else if enemies too far, set move to right 
		else {
			newPos = (int)(getPosition().x + Param.UNIT_SPEED * dt);
		}
	
		// Then if destination is in company range, do not move anymore
		if(newPos > companyRange.getMax() && newPos < companyRange.getMin()){
			return false;
		}
		// If unit is a NPC, it can move, else must wait until player action 
		else{
			// Problem, company center follow unit, so how block player company and not enemies
			return isEnemy? true : false;
		}
	}

	public void freeMove() {
		float dt = Gdx.graphics.getDeltaTime();
		
		if(unitsInSight()){
			// If enemies company is not in range, unit must move
			if(!unitsInRange()){
				// Else if enemies are too close, move to the left
				if(unitsTooClose() && !isEnemy || unitsTooFar() && isEnemy){
					int newPos = (int)(getPosition().x - Param.UNIT_SPEED * dt);
					//If units are in range, check if they're reachable in company range
					if(unitInCompanyRange()){
						setPosition(newPos, dt/5);						
					}
				}
				// Else if enemies too far, move to right 
				else {
					int newPos = (int)(getPosition().x + Param.UNIT_SPEED * dt);
					//If units are in range, check if they're reachable in company range
					// TODO fix Player company moving, only enemies are allowed to
					if(unitInCompanyRange()){
						setPosition(newPos, dt/5);						
					}
				}
			}
		}
	}

	public float getCooldown() {
		return skills.getCooldown();
	}

	public float getCounter() {
		return counter;
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

	public void resetGesture() {
		counter = 0;
		nAttacks = 0;
	}
}
