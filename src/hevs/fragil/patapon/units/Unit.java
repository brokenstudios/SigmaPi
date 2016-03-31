package hevs.fragil.patapon.units;
import hevs.fragil.patapon.others.*;
import hevs.gdx2d.components.bitmaps.BitmapImage;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public abstract class Unit implements DrawableObject{
	public int position;
	int rank;
	int index;
	public int id;
	protected double life;
	FightFactor attack;
	BitmapImage img ;
	
	Unit(){
		this(1);
	}
	Unit(int r){
		this.rank = r;
		this.life = Data.DEFAULT_LIFE+rank*5;
	}	
	public void move(int newPos){
		this.position = newPos;
	}
	protected void setLife(double d){
		this.life = d;
	}
	protected void setImgPath(String url){
		this.img = new BitmapImage(url);
	}
	public String toString(){
		return ", Level : "+ rank + ", Life : " + life;
	}
	@Override
	public void draw(GdxGraphics g) {
		g.drawPicture(position, Data.FLOOR, img);
	}
}
