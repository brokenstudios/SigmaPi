package hevs.fragil.patapon.units;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.mechanics.CurrentLevel;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.BodyPolygon;

public abstract class Unit implements DrawableObject {
	static int nUnits;

	// Skills
	protected Skills skills;
	protected float cooldownCounter;
	protected float animationCounter = -1;
	protected int level = 1;
	protected Species species = Species.TAPI;
	protected Expression expression = Expression.DEFAULT;
	protected ArmsLine armsLine = ArmsLine.WALK;
	protected boolean isEnnemi;
	private boolean enableDeadAnimation = false;
	private boolean defend = false;
	private float opacity = 1f;
	protected int collisionGroup;
	private BodyPolygon hitBox;
	// Hysteresis pattern to avoid vibrations due to physics
	private Vector2 antiVibration;

	// Drawables
	private SpriteSheet legs;
	private int legsIndex;
	private int armsIndex;
	protected SpriteSheet body, eye, arms;

	Unit(int lvl, Species species, int attack, int defense, int life, int distance, int rangeMin, int rangeMax, float cooldown, boolean isEnnemi) {
		this.species = species;
		this.level = lvl;
		this.skills = new Skills(life + lvl * 5, attack, rangeMax, rangeMin, defense, (float) (1f + Math.random() / 2.0));
		this.isEnnemi = isEnnemi;
		if (isEnnemi)
			this.collisionGroup = Param.ENNEMIES_GROUP;
		else
			this.collisionGroup = Param.HEROES_GROUP;
		nUnits++;
	}

	public void setPosition(int newPos, double totalTime) {
		if (hitBox != null)
			hitBox.moveToLinear(newPos, totalTime);
		else {
			hitBox = new BodyPolygon(new Vector2(newPos, Param.FLOOR_DEPTH), collisionGroup, skills.getLife());
			hitBox.getBody().setFixedRotation(true);
			antiVibration = new Vector2(newPos, Param.FLOOR_DEPTH);
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

		protected abstract ArmsLine getAttackAnimation();
	protected abstract float getPreAnimationDelay();
	protected void launchAnimation(ArmsLine a) {
		armsLine = a;
		if(animationCounter == -1)
			animationCounter = 0;
	}

	public boolean isDefending() {
		return defend;
	}

	public void setDefending(boolean defend) {
		// TODO disable it after the defend routine
		this.defend = defend;
	}

	public void receive(float damage) {
		if (damage > getDefense())
			enableDeadAnimation = this.hitBox.applyDamage(damage);
	}

	private float getDefense() {
		if (defend)
			return skills.getDefense();
		else
			return 0;
	}

	protected Vector2 getDrawPosition(GdxGraphics g) {
		// Hysteresis pattern to avoid vibrations due to physics
		float x = Math.round(getPosition().x - g.getCamera().position.x + Param.CAM_WIDTH / 2);
		float y = Math.round(getPosition().y - g.getCamera().position.y + Param.CAM_HEIGHT / 2 - 37);
		
		if (hitBox.getBodyLinearVelocity().x > 0) {
			if (antiVibration.x < x) antiVibration.x = x;
			if (antiVibration.y < y) antiVibration.y = y;
		} 
		else {
			if (antiVibration.x > x) antiVibration.x = x;
			if (antiVibration.y > y) antiVibration.y = y;
		}
		
		return new Vector2(antiVibration.x, antiVibration.y);
	}

	@Override
	public void draw(GdxGraphics g) {
		if (unitsInRange()) expression = Expression.ANGRY;
		else expression = Expression.DEFAULT;
	
		if (enableDeadAnimation) drawDead(g);
		else drawAlive(g);
	}

	private void drawAlive(GdxGraphics g) {
		armsAnimation();
		
		float stateTime = CurrentLevel.getLevel().getStateTime();
		
		legsIndex = legs.drawAllFrames(stateTime, getDrawPosition(g).x, getDrawPosition(g).y);
		body.drawWalkAnimation(legsIndex, (4 * (species.ordinal())) + (level), getDrawPosition(g).x, getDrawPosition(g).y + 10, 32, 38);
		eye.drawWalkAnimation(legsIndex, expression.ordinal(), getDrawPosition(g).x, getDrawPosition(g).y + 22, 32, 38);
		armsIndex = arms.drawFrames(stateTime, armsLine.ordinal() * 4, 4, getDrawPosition(g).x, getDrawPosition(g).y);
	}

	private void drawDead(GdxGraphics g) {
		armsAnimation();
		
		float angle = hitBox.getBodyAngle();
		
		legs.drawRotatedFrameAlpha(0, angle, getDrawPosition(g).x, hitBox.getBodyPosition().y, -32, -35, opacity);
		body.drawRotatedFrameAlpha(0, angle, getDrawPosition(g).x, hitBox.getBodyPosition().y, -32, -25, opacity);
		eye.drawRotatedFrameAlpha(expression.ordinal(), angle, getDrawPosition(g).x, hitBox.getBodyPosition().y, -32, -13, opacity);
	}

	private void armsAnimation() {
		float dt = Gdx.graphics.getDeltaTime();
		
		if(animationCounter >= 0){
			animationCounter += dt;
		}
		if(animationCounter >= 4 * arms.getFrameDuration()){
			armsLine = ArmsLine.WALK;
			animationCounter = -1;
		}
	}
	protected int getArmsIndex() {
		return armsIndex;
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
			enableDeadAnimation = true;
			expression = Expression.DEAD;
			opacity -= 0.005f;
			if (opacity <= 0) {
				return true;
			}
		}
		return false;
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
		else if (cooldownCounter >= getCooldown() - getPreAnimationDelay())
			launchAnimation(getAttackAnimation());
	}

	public void applyImpulse(int intensity) {
		Vector2 pos = hitBox.getBodyPosition();
		Vector2 force = new Vector2(intensity, 0);
		hitBox.applyBodyLinearImpulse(force, pos, true);
	}

	public void setExpression(Expression exp) {
		expression = exp;
	}

	public int getEndurance() {
		int defense = 0;
		if (defend)
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


	/**
	 * This is only to load files in the PortableApplication onInit method
	 */
	public void setLegsSprite(String url, int cols, int rows) {
		legs = new SpriteSheet(url, cols, rows, 0.2f, isEnnemi, true);
	}

	/**
	 * This is only to load files in the PortableApplication onInit method
	 */
	public void setBodySprite(String url, int cols, int rows) {
		body = new SpriteSheet(url, cols, rows, 0.2f, false, true);
	}

	/**
	 * This is only to load files in the PortableApplication onInit method
	 */
	public void setEyeSprite(String url, int cols, int rows) {
		eye = new SpriteSheet(url, cols, rows, 0.2f, false, true);
	}

	/**
	 * This is only to load files in the PortableApplication onInit method
	 */
	public void setArmsSprite(String url, int cols, int rows) {
		arms = new SpriteSheet(url, cols, rows, 0.2f, false, false);
	}

	public String toString() {
		return ", Level : " + level + ", Life : " + skills.getLife();
	}
}
