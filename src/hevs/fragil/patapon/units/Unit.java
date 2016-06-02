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

public abstract class Unit implements DrawableObject{
	static int nUnits;
	
	//Skills
	protected Skills skills;
	protected float timeCounter;
	protected int level = 1;
	protected Species species = Species.TAPI;
	protected Expression expression = Expression.DEFAULT;
	protected int collisionGroup;
	private boolean enableDeadAnimation = false;
	private boolean defend = false;
	private boolean isEnnemi;
	private float opacity = 1f;
	private BodyPolygon hitBox;
	
	//Drawables
	private SpriteSheet legs;
	private int frameIndex;
	private SpriteSheet body, eye;
	
	Unit(int lvl, Species species, int attack, int defense, int life, int distance, int rangeMin, int rangeMax, float cooldown, boolean isEnnemi){
		this.species = species;
		this.level = lvl;
		this.skills = new Skills(life+lvl*5, attack, rangeMax, rangeMin, defense, (float)(1f+Math.random()/2.0));
		this.isEnnemi = isEnnemi;
		if(isEnnemi)
			this.collisionGroup = Param.ENNEMIES_GROUP;
		else
			this.collisionGroup = Param.HEROES_GROUP;
		nUnits++;
	}	
	public void setPosition(int newPos, double totalTime){
		if(hitBox != null)
			hitBox.moveToLinear(newPos, totalTime);
		else{
			hitBox = new BodyPolygon(new Vector2(newPos, Param.FLOOR_DEPTH), collisionGroup, skills.getLife());
			hitBox.getBody().setFixedRotation(true);
		}
	}
	protected int getPosition(){
		return (int)hitBox.getBodyWorldCenter().x;
	}
	protected void setLife(int life){
		this.skills.setLife(life);
	}
	public String toString(){
		return ", Level : "+ level + ", Life : " + skills.getLife();
	}
	public void attack(float dt){
		timeCounter += dt;
		if(timeCounter > skills.getCooldown()){
			attack();
			timeCounter = 0f;
		}
	}
	public boolean isDefending(){
		return defend;
	}
	public void setDefending(boolean defend){
		//TODO disable it after the defend routine
		this.defend = defend;
	}
	public void receive(float damage){
		if(damage > getDefense())
			enableDeadAnimation = this.hitBox.applyDamage(damage);
	}
	private float getDefense() {
		if(defend)
			return skills.getDefense();
		else
			return 0;
	}
	@Override
	public void draw(GdxGraphics g){
		float stateTime = CurrentLevel.getLevel().getStateTime();
		float xDrawCoordinate = getPosition() - g.getCamera().position.x + Param.CAM_WIDTH / 2;
		if(enableDeadAnimation){
			float y,angle;
			angle = hitBox.getBodyAngle();
			y = hitBox.getBodyWorldCenter().y;
			legs.drawRotatedFrameAlpha(0, angle, xDrawCoordinate, y, -32, -35, opacity);
			body.drawRotatedFrameAlpha(0, angle, xDrawCoordinate, y, -32, -25, opacity);
			eye.drawRotatedFrameAlpha(expression.ordinal(), angle, xDrawCoordinate, y, -32, -13, opacity);
		}
		else {
			frameIndex = legs.drawKeyFrames(stateTime, xDrawCoordinate, Param.FLOOR_DEPTH);
			body.drawWalkAnimation(frameIndex, (4*(species.ordinal()))+(level), xDrawCoordinate , 40, 32, 38);
			eye.drawWalkAnimation(frameIndex, expression.ordinal(), xDrawCoordinate, 52, 32, 38);
		}
	}
	public abstract void drawArms(GdxGraphics g);
	/**
	 * This is only to load files in the PortableApplication onInit method
	 */
	public void setLegsSprite(String url, int cols, int rows){
		legs = new SpriteSheet(url, cols , rows, 0.2f, isEnnemi);
	}
	/**
	 * This is only to load files in the PortableApplication onInit method
	 */
	public void setBodySprite(String url, int cols, int rows) {
		body = new SpriteSheet(url, cols , rows, 0.2f, false);		
	}
	/**
	 * This is only to load files in the PortableApplication onInit method
	 */
	public void setEyeSprite(String url, int cols, int rows) {
		eye = new SpriteSheet(url, cols , rows, 0.2f, false);		
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
	public float getLife(){
		return hitBox.getLife();
	}
	public Skills getSkills(){
		return skills;
	}
	public boolean isDead(){
		if(getLife() <= 0){
			enableDeadAnimation = true;
			expression = Expression.DEAD;
			opacity -= 0.005f;
			if(opacity <= 0){
				return true;
			}
		}
		return false;
	}
	public void destroyBox() {
		hitBox.destroy();
	}
	
	public abstract void attack();
	public void applyImpulse(int intensity) {
		Vector2 pos = hitBox.getBodyPosition();
		Vector2 force = new Vector2(intensity, 0);
		hitBox.applyBodyLinearImpulse(force , pos, true);
	}
	public void setExpression(Expression exp) {
		expression = exp;
	}
	public int getEndurance() {
		int defense = 0;
		if(defend)
			defense = skills.getDefense();
		return skills.getLife() + defense;
	}
	public boolean isFatal(int damage) {
		boolean fatal = false;
		if(damage >= getEndurance())
			fatal = true;
		return fatal;
	}
	protected Vector<Unit> getUnitsInRange(){
		Vector<Unit> unitsInRange = new Vector<Unit>();
		Company ennemies = CurrentLevel.getLevel().getEnnemies();
		for (Section s : ennemies.sections) {
			for (Unit u : s.units) {
				int distance = u.getPosition() - this.getPosition();
				distance = Math.abs(distance) - 64;
				if(distance < this.skills.getRangeMax()){
					unitsInRange.add(u);
				}
			}
		}
		return unitsInRange;
	}
	protected abstract int findBestPosition();
	public void move(){
		if(findBestPosition() != getPosition())
			setPosition(findBestPosition(), Math.abs(getPosition()-findBestPosition()) / Param.UNIT_SPEED);
	}
}
