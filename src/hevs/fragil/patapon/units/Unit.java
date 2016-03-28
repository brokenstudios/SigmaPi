package hevs.fragil.patapon.units;
import hevs.fragil.patapon.others.*;

public abstract class Unit {
	public int position;
	int rank;
	int index;
	public int id;
	protected double life;
	FightFactor attack;
	
	Unit(){
		this(1);
	}
	Unit(int r){
		this.rank = r;
		this.life = Data.DEFAULTLIFE+rank*5;
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
