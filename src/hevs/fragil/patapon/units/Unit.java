package hevs.fragil.patapon.units;
import hevs.fragil.patapon.others.*;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public abstract class Unit implements DrawableObject{
	public int position;
	int rank;
	int index;
	protected double life;
	FightFactor attack;
	
	Unit(){
		this(1);
	}
	Unit(int r){
		this.rank = r;
		this.life = Data.LIFE_BASE+rank*5;
	}	
	public void move(int newPos){
		this.position = newPos;
	}
	protected void setLife(double d){
		this.life = d;
	}
	public String toString(){
		return ", Level : "+ rank + ", Life : " + life;
	}
}
