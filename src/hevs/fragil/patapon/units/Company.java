package hevs.fragil.patapon.units;
import java.util.Iterator;
import java.util.Vector;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.mechanics.Action;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.Arrow;

public class Company implements DrawableObject {
	public String name = "";
	double feverFactor = 0.1;
	public Vector<Section> sections = new Vector<Section>();
	private Action action;
	private boolean ready;
	public Company(){
		this(0,"noname");
	}
	public Company(String name){
		this(0,name);
	}
	public Company(int pos){
		this(pos, "noname");
	}
	public Company(int pos, String name){
		moveAbsolute(pos, 0.1);
		this.name = name;   
		this.ready = true;
	}
	public void setCollisionGroup(int collisionGroup){
		for (Section s : sections) {
			for (Unit u : s.units) {
				u.setCollisionGroup(collisionGroup);
			}
		}
	}
	public String toString(){
		String t = "Start of company \n";
		t += " This company is at position : "+ getPosition() +"\n";
		t += " This company's fever factor : "+ feverFactor +"\n";
		t += " This company contains : \n";
		for (Section section : sections) {
			t += section.toString()+"\n";
		}
		t += "End of Company";
		return t;
	}
	public float getPosition(){
		float xSum = 0;
		for (Section s : sections) {
			for (Unit u : s.units) {
				xSum += u.getPosition().x;
			}
		}
	
		return xSum / getNbUnits();
	}
	public int getNbUnits(){
		int s = 0;
		for (Iterator<Section> i = sections.iterator(); i.hasNext();) {
			Section section = (Section) i.next();
			s += section.units.size();
		}
		return s;
	}
	public int getNbSections(){
		return sections.size();
	}
	public int getWidth(){
		int width = 0;
		for (Section section : sections) {
			width += section.getWidth();
		}
		int nSections = sections.size();
		return (int)(width + (nSections-1)*Param.SECTION_KEEPOUT);
	}
	public void moveAbsolute(int newPos, double totalTime){
		int width = getWidth();
		float screenMargin = newPos - width/2f;
		if(screenMargin > 0){
			float tempPos = screenMargin;
			for (Section section : sections) {
				tempPos += section.getWidth()/2f;
				section.move((int)tempPos,totalTime);
				tempPos += section.getWidth()/2f + Param.SECTION_KEEPOUT;
			}
		}		
	}
	public Action getAction(){
		return action;
	}
	public void add(Section s){
		sections.addElement(s);
	}
	public void setAction(Action a){
		if((ready && a != null) || a == Action.STOP){
			System.out.println("action set : " + a);
			action = a;
			ready = false;
		}
	}
	public void actionFinished(){
		action = null;
		ready = true;
	}
	public void remove(Section s){
		sections.remove(s);
	}
	public void initRandom(int nb1, int nb2, int nb3) {
		for(int i = 0 ; i < 3; i++){
			add(new Section(Integer.toString(i)));
		}
		for(int i = 0 ; i < nb1; i++){
			sections.elementAt(0).add(new Archer());
		}
		for(int i = 0 ; i < nb2; i++){
			sections.elementAt(1).add(new Spearman());
		}
		for(int i = 0 ; i < nb3; i++){
			sections.elementAt(2).add(new Shield());
		}
		
		int initialPos = getWidth()/2 + Param.SECTION_KEEPOUT;
		moveAbsolute(initialPos, 100);
		
		//Load the image files
		for (Section s : sections) {
			for (Unit u : s.units) {
				u.setBodySprite("data/images/bodies64x102.png", 1,1);
				u.setEyeSprite("data/images/eyes64x54.png", 7, 1);
				u.setLegsSprite("data/images/legs64x42.png", 4, 1);
				u.setArmsSprite("data/images/arms64x96.png", 4, 8);
			}
		}
		Arrow.setImgPath("data/images/fleche.png");
		
		setCollisionGroup(Param.HEROES_GROUP);
	}
	@Override
	public void draw(GdxGraphics g) {
		for (Section section : sections) {
			section.draw(g);
		}
	}
	public void initEnnemies(int nb1, int nb2, int nb3) {
		for(int i = 0 ; i < 3; i++){
			add(new Section(Integer.toString(i)));
		}
		for(int i = 0 ; i < nb1; i++){
			sections.elementAt(0).add(new Archer(0,Species.TAPI,true));
		}
		for(int i = 0 ; i < nb2; i++){
			sections.elementAt(1).add(new Spearman(0,Species.TAPI,true));
		}
		for(int i = 0 ; i < nb3; i++){
			sections.elementAt(2).add(new Shield(0,Species.TAPI,true));
		}
		
		int initialPos = getWidth()/2 + 1600;
		moveAbsolute(initialPos, 100);
		
		//Load the image files
		for (Section s : sections) {
			for (Unit u : s.units) {
				u.setBodySprite("data/images/badbody64x102.png", 1,1);
				u.setEyeSprite("data/images/badeyes64x54.png", 3, 1);
				u.setLegsSprite("data/images/legs64x42.png", 4, 1);
				u.setArmsSprite("data/images/arms64x96.png", 4, 8);
				u.setExpression(Expression.ANGRY);
			}
		}
		Arrow.setImgPath("data/images/fleche.png");
	}
	public void intelligentMove() {
		for (Section s : sections) {
			for (Unit u : s.units) {
				u.move();
			}
		}
	}
	public boolean isEmpty() {
		return sections.isEmpty();
	}
}