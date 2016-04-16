package hevs.fragil.patapon.units;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.others.Param;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public abstract class Unit implements DrawableObject{
	public int position;
	int level = 1;
	int species = 1;
	protected double life;
	FightFactor attack;
	static SpriteSheet legs;
	//default values
	SpriteSheet body, eye;
	int eyeFrame = (int)(Math.random()*5);
	
	Unit(){
		this(1);
	}
	Unit(int lvl){
		this(1,1);
	}
	Unit(int lvl, int species){
		this.species = species;
		this.level = lvl;
		this.life = Param.LIFE_BASE + level * 5;
	}	
	public void move(int newPos){
		this.position = newPos;
	}
	protected void setLife(double d){
		this.life = d;
	}
	public String toString(){
		return ", Level : "+ level + ", Life : " + life;
	}
	public abstract void attack();
	public abstract void draw(GdxGraphics g, float time);
	public void drawLegs(float stateTime){
		legs.drawKeyFrames(stateTime, position);
	}
	public void drawBody(){
		body.drawFrame((5*species)-level, position, 20);
	}
	public void drawEye() {
		//TODO Choper l'état ! oui monsieur encore du job
		eye.drawFrame(eyeFrame, position, 32);
	}
	//only to load files in the onInit method
	public static void setLegsSprite(String url, int cols, int rows){
		legs = new SpriteSheet(url, cols , rows);
	}
	public void setBodySprite(String url, int cols, int rows) {
		body = new SpriteSheet(url, cols , rows);		
	}
	public void setEyeSprite(String url, int cols, int rows) {
		eye = new SpriteSheet(url, cols , rows);		
	}
	
}
