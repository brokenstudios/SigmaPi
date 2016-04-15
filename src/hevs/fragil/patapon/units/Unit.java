package hevs.fragil.patapon.units;
import hevs.fragil.patapon.others.*;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public abstract class Unit implements DrawableObject{
	public int position;
	int level = 1;
	Breed breed = Breed.Breed1;
	protected double life;
	FightFactor attack;
	
	Unit(){
		this(1);
	}
	Unit(int r){
		this.level = r;
		this.life = Param.LIFE_BASE+level*5;
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
	public abstract void draw(GdxGraphics g, float stateTime);
}
