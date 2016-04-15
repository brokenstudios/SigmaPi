package hevs.fragil.patapon.units;
import hevs.fragil.patapon.Resources;
import hevs.fragil.patapon.drawables.Frame;
import hevs.fragil.patapon.drawables.SpriteSheet;
import hevs.fragil.patapon.others.Param;
import hevs.gdx2d.lib.GdxGraphics;
import hevs.gdx2d.lib.interfaces.DrawableObject;

public abstract class Unit implements DrawableObject{
	public int position;
	int level = 1;
	protected double life;
	FightFactor attack;
	static SpriteSheet legs;
	//default values
	Frame bodyFrame;
	Frame eyeFrame;
	
	Unit(){
		this(1);
	}
	Unit(int r){
		this.level = r;
		this.life = Param.LIFE_BASE + level * 5;
		this.bodyFrame = Resources.getInstance().getFrame(BodyPart.EYE_CENTER);
		this.eyeFrame = Resources.getInstance().getFrame(BodyPart.BODY_BLUE);
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
		legs.draw(stateTime, position);
	}
	public void drawBody(){
		//TODO Choper l'état ! oui monsieur encore du job
		//TODO Ask mui why ??? isn't null but nothing to draw WTF
		if(bodyFrame != null){
			//NE MARCHE QUE DANS MAP GRAPHIC RENDER ! AUCUN SENS PUTAIN
			changeBody(BodyPart.BODY_BLUE);
			bodyFrame.draw(position);
		}
	}
	//only to load files in the onInit method
	public void changeBody(BodyPart body){
		this.bodyFrame = Resources.getInstance().getFrame(body);
	}
	public void changeEye(BodyPart eye){
		this.eyeFrame = Resources.getInstance().getFrame(eye);
	}
	public static void setLegsSprite(String url, int cols, int rows){
		legs = new SpriteSheet(url, cols , rows);
	}
}
