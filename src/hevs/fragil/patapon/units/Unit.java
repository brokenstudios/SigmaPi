package hevs.fragil.patapon.units;
import com.badlogic.gdx.math.Vector2;

import hevs.fragil.patapon.drawables.BodyPolygon;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.others.Param;
import hevs.gdx2d.components.physics.PhysicsPolygon;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public abstract class Unit implements DrawableObject{
	public int position;
	int level = 1;
	int species = 1;
	protected int life;
	protected int attack;
	protected int defense;
	protected int distance;
	protected int range;
	protected int cooldown;
	protected long lastAttack = 0;
	BodyPolygon hitBox;
	static SpriteSheet legs;
	static int nUnits;
	SpriteSheet body, eye;
	int walkIndex;
	
	Unit(int lvl, int species, int attack, int defense, int life, int distance, int range, int cooldown){
		this.species = species;
		this.level = lvl;
		this.life = Param.LIFE_BASE + level * 5;
		this.attack = attack;
		this.defense = defense;
		this.distance = distance;
		this.life = life;
		this.range = range;
		this.cooldown = cooldown;
		hitBox = new BodyPolygon(new Vector2(position,Param.FLOOR_DEPTH));
//		hitBox.setCollisionGroup(-1);
		nUnits++;
	}	
	public void move(int newPos){
		hitBox.move(newPos);
	}
	protected void setLife(int d){
		this.life = d;
	}
	public String toString(){
		return ", Level : "+ level + ", Life : " + life;
	}
	public abstract void attack();
	public abstract void draw(GdxGraphics g, float time);
	public void drawLegs(float stateTime){
		walkIndex = legs.drawKeyFrames(stateTime, position-32);
	}
	public void drawBody(float stateTime){
		body.drawWalkAnimation(walkIndex, (5*(species-1))+(level-1), position-32, 20);
	}
	public void drawEye() {
		//TODO Choper l'état ! oui monsieur encore du job
		eye.drawWalkAnimation(walkIndex, 1, position-32, 32);
	}
	//only to load files in the onInit method
	public static void setLegsSprite(String url, int cols, int rows){
		legs = new SpriteSheet(url, cols , rows, 0.2f);
	}
	public void setBodySprite(String url, int cols, int rows) {
		body = new SpriteSheet(url, cols , rows, 0.2f);		
	}
	public void setEyeSprite(String url, int cols, int rows) {
		eye = new SpriteSheet(url, cols , rows, 0.2f);		
	}
	
}
