package hevs.fragil.patapon.units;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.drawables.SpriteSheet;
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
	private boolean dead = false;
	private boolean defend = false;
	private float opacity = 1f;
	private BodyPolygon hitBox;
	
	//Drawables
	private SpriteSheet legs;
	private int frameIndex;
	private SpriteSheet body, eye;
	
	Unit(int lvl, Species species, int attack, int defense, int life, int distance, int range, float cooldown, int collisionGroup){
		this.species = species;
		this.level = lvl;
		this.skills = new Skills(life+lvl*5, attack, range, defense, (float)(1f+Math.random()/2.0));
		this.collisionGroup = collisionGroup;
		nUnits++;
	}	
	public void setPosition(int newPos, double totalTime){
		if(hitBox != null)
			hitBox.moveToLinear(newPos, totalTime);
		else
			hitBox = new BodyPolygon(new Vector2(newPos, Param.FLOOR_DEPTH), collisionGroup, skills.getLife());
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
		if(defend){
			damage -= skills.getDefense();
		}
		if(damage > 0)
			this.hitBox.applyDamage(damage);
	}
	protected void draw(float stateTime){
		if(dead){
			double x,y,angle;
			angle = hitBox.getBodyAngle();
			x = hitBox.getBodyWorldCenter().x;
			y = hitBox.getBodyWorldCenter().y;
			legs.drawRotatedFrameAlpha(1, (float)angle, (float)x, (float)y, -32, -35, opacity);
			body.drawRotatedFrameAlpha(1, (float)angle, (float)x, (float)y, -32, -25, opacity);
			eye.drawRotatedFrameAlpha(expression.ordinal(), (float)angle, (float)x, (float)y, -32, -13, opacity);
		}
		else {
			frameIndex = legs.drawKeyFrames(stateTime, getPosition(), Param.FLOOR_DEPTH);
			body.drawWalkAnimation(frameIndex, (4*(species.ordinal()))+(level), getPosition(), 40, 32, 38);
			eye.drawWalkAnimation(frameIndex, expression.ordinal(), getPosition(), 52, 32, 38);
		}
	}
	
	/**
	 * This is only to load files in the PortableApplication onInit method
	 */
	public void setLegsSprite(String url, int cols, int rows){
		legs = new SpriteSheet(url, cols , rows, 0.2f);
	}
	/**
	 * This is only to load files in the PortableApplication onInit method
	 */
	public void setBodySprite(String url, int cols, int rows) {
		body = new SpriteSheet(url, cols , rows, 0.2f);		
	}
	/**
	 * This is only to load files in the PortableApplication onInit method
	 */
	public void setEyeSprite(String url, int cols, int rows) {
		eye = new SpriteSheet(url, cols , rows, 0.2f);		
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
			dead = true;
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
}
