package hevs.fragil.patapon.units;
import java.util.Iterator;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.mechanics.State;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.Arrow;
import hevs.fragil.patapon.physics.Spear;

public class Company implements DrawableObject {
	public String name = "";
	double feverFactor = 0.1;
	public Vector<Section> sections = new Vector<Section>();
	private State action;
	private boolean ready;
	private boolean freeToMove = false;
	private int fixedPos;
	
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
		setPosition(pos, 0.1f);
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
	public int getMinWidth(){
		int width = 0;
		for (Section section : sections) {
			width += section.getWidth();
		}
		int nSections = sections.size();
		return (int)(width + (nSections-1)*Param.SECTION_KEEPOUT);
	}
	public void setPosition(int newPos, float totalTime){
		int width = getMinWidth();
		float screenMargin = newPos - width/2f;
		
		if(screenMargin > 0){
			float tempPos = screenMargin;
			fixedPos = newPos;
			for (Section section : sections) {
				tempPos += section.getWidth()/2f;
				section.setPosition((int)tempPos,totalTime);
				tempPos += section.getWidth()/2f + Param.SECTION_KEEPOUT;
			}
		}		
	}
	public State getAction(){
		return action;
	}
	public void add(Section s){
		sections.addElement(s);
	}
	public void setAction(State a){
		if((ready && a != null) || a == State.IDLE){
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
	/**
	 * @param nb1 : number of archers
	 * @param nb2 : number of swordmans
	 * @param nb3 : number of shields
	 * @return a sample company that contains {@code nb1} archers,
	 * {@code nb2} swordmans and {@code nb3}shields.
	 */
	public void initRandomHeroes(int nb1, int nb2, int nb3){
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
		
		int initialPos = getMinWidth()/2 + 50;
		setPosition(initialPos, 100);
		
		//Load the image files
		for (Section s : sections) {
			for (Unit u : s.units) {
				u.setBodySprite("data/images/bodies64x102.png", 5, 5);
				u.setEyeSprite("data/images/eyes64x54.png", 7, 1);
				u.setLegsSprite("data/images/legs2_64x42.png", 4, 1, false);
				u.setArmsSprite("data/images/arms64x96.png", 4, 8);
			}
		}
		Arrow.setImgPath("data/images/fleche.png");
		Spear.setImgPath("data/images/fleche.png");
	}
	@Override
	public void draw(GdxGraphics g) {
		for (Section section : sections) {
			section.draw(g);
		}
		
		// Only to debug move processing
//		g.drawFilledCircle(getPosition() - g.getCamera().position.x + Param.CAM_WIDTH / 2, Param.FLOOR_DEPTH, 10, Color.YELLOW);
//		g.drawFilledCircle(getPosition() - Param.COMPANY_WIDTH - g.getCamera().position.x + Param.CAM_WIDTH / 2, Param.FLOOR_DEPTH, 10, Color.CYAN);
//		g.drawFilledCircle(getPosition() + Param.COMPANY_WIDTH - g.getCamera().position.x + Param.CAM_WIDTH / 2, Param.FLOOR_DEPTH, 10, Color.CYAN);
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
		
		int initialPos = getMinWidth()/2 + 1000;
		setPosition(initialPos, 100);
		
		//Load the image files
		for (Section s : sections) {
			for (Unit u : s.units) {
				u.setBodySprite("data/images/badbody64x102.png", 1,1);
				u.setEyeSprite("data/images/badeyes64x54.png", 7, 1);
				u.setLegsSprite("data/images/legs64x42.png", 4, 1, true);
				u.setArmsSprite("data/images/arms64x96.png", 4, 8);
				u.setExpression(Look.ANGRY);
			}
		}
		Arrow.setImgPath("data/images/fleche.png");
	}
	public void aiMove() {
		if(freeToMove){
			freeMove();
		}
		else
			regroup();
	}
	private void freeMove(){
		for (Section s : sections) {
			for (Unit u : s.units) {
				//when no enemy is in the units's range, unit must try to find a better place
				if(u.getUnitsInRange().isEmpty()){
					float u2uDistance = u.unitToUnitDistance(u.findNextReachableEnemy());
					//if we are too near, we must increase the distance
					boolean increaseDistance = (u2uDistance < u.getSkills().getRangeMin());
					//get desired position depending of increase or decrease of the distance with enemies
					int desiredPos = u.desiredPos(increaseDistance);
					//test if this new position is contained between the company maximum limits
					if(isInCompanyRange(desiredPos)){
						//if desiredPos is in company range
						setPosition(u.desiredPos(false), Gdx.graphics.getDeltaTime());
					}
				}
			}
		}
	}
	private boolean isInCompanyRange(int desiredPos) {
		if(fixedPos - Param.COMPANY_WIDTH < desiredPos && desiredPos < fixedPos + Param.COMPANY_WIDTH)
			return true;
		return false;
	}
	private void regroup(){
		
	}
	public boolean isEmpty() {
		return sections.isEmpty();
	}
	public void regroupUnits() {
		freeToMove = false;
	}
	public void freeUnits(){
		freeToMove = true;
	}
}