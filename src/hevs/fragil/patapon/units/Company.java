package hevs.fragil.patapon.units;
import java.util.Iterator;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import hevs.fragil.patapon.mechanics.CurrentLevel;
import hevs.fragil.patapon.mechanics.Param;
import hevs.fragil.patapon.physics.Arrow;
import hevs.fragil.patapon.physics.Spear;
import hevs.fragil.patapon.physics.Tower;

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
	public void setPosition(int newPos){
		//move to the right, must check next position with right company limit
		if(newPos > fixedPos){
			if(areaClear(newPos + (Param.COMPANY_MARGIN +getMinWidth())/2)){
				fixedPos = newPos;
			}
		}
		//move to the left, must check next position with left company limit
		else if (areaClear(newPos - (Param.COMPANY_MARGIN +getMinWidth())/2))
				fixedPos = newPos;
	}
	private boolean areaClear(int posToTry) {
		for (DrawableObject d : CurrentLevel.getLevel().getDecor().toDraw) {
			if(d instanceof Tower){
				return !((Tower) d).isOccuped(posToTry);
			}
		}
		return true;
	}
	public void setAction(State a){
		if((ready && a != null) || a == State.IDLE){
			action = a;
			ready = false;
			System.out.println("action : " + a + " set !");
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
		return fixedPos;
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
	public State getAction(){
		return action;
	}
	public void add(Section s){
		sections.addElement(s);
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
		
		int width = getMinWidth();
		float screenMargin = initialPos - width/2f;
		
		if(screenMargin > 0){
			float tempPos = screenMargin;
			fixedPos = initialPos;
			for (Section section : sections) {
				tempPos += section.getWidth()/2f;
				section.setPosition((int)tempPos, 0.1f);
				tempPos += section.getWidth()/2f + Param.SECTION_KEEPOUT;
			}
		}
		
		//Load the image files
		for (Section s : sections) {
			for (Unit u : s.units) {
				u.setBodySprite("data/images/bodies.png", 5,5);
				u.setEyeSprite("data/images/eyes.png", 5, 2);
				u.setLegsSprite("data/images/legs.png", 4, 1, false);
				u.setArmsSprite(4, 6, false);
			}
		}
		
		Arrow.setImgPath("data/images/fleche.png");
		Spear.setImgPath("data/images/fleche.png");
	}
	public void initEnnemies(int nb1, int nb2, int nb3) {
		for(int i = 0 ; i < 3; i++){
			add(new Section(Integer.toString(i)));
		}
		for(int i = 0 ; i < nb3; i++){
			sections.elementAt(0).add(new Shield(0,Species.TAPI,true));
		}
		for(int i = 0 ; i < nb2; i++){
			sections.elementAt(1).add(new Spearman(0,Species.TAPI,true));
		}
		for(int i = 0 ; i < nb1; i++){
			sections.elementAt(2).add(new Archer(0,Species.TAPI,true));
		}
		
		// Set the initial position
		int initialPos = getMinWidth()/2 + 4000;
	
		int width = getMinWidth();
		float screenMargin = initialPos - width/2f;
		
		if(screenMargin > 0){
			float tempPos = screenMargin;
			fixedPos = initialPos;
			for (Section section : sections) {
				tempPos += section.getWidth()/2f;
				section.setPosition((int)tempPos, 0.1f);
				tempPos += section.getWidth()/2f + Param.SECTION_KEEPOUT;
			}
		}
		
		//Load the image files
		for (Section s : sections) {
			for (Unit u : s.units) {
				u.setBodySprite("data/images/badbody.png", 1,1);
				u.setEyeSprite("data/images/badeyes.png", 5, 2);
				u.setLegsSprite("data/images/legs.png", 4, 1, true);
				u.setArmsSprite(4, 6, true);
				u.setExpression(Look.ANGRY);
			}
		}
		Arrow.setImgPath("data/images/fleche.png");
	}
	@Override
	public void draw(GdxGraphics g) {
		for (Section section : sections) {
			section.draw(g);
		}
	}
	public void aiMove() {
		if(freeToMove){
			freeMove();
		}
		else{
			regroup();
		}
	}
	
	private void freeMove(){
		for (Section s : sections) {
			for (Unit u : s.units) {
				if(!u.isDying()){
					//when no enemy is in the units's range, unit must try to find a better place
					if(u.getUnitsInRange().isEmpty() && u.getTowersInRange().isEmpty()){
						float u2uDistance = u.unitToUnitDistance(u.findNextReachableEnemy());
						
						//if we are too near, we must increase the distance
						boolean increaseDistance = (u2uDistance < u.getSkills().getRangeMin());
						
						//get desired position depending of increase or decrease the distance with enemies
						int desiredPos = u.desiredPos(increaseDistance);
						
						//test if this new position is contained between the company maximum limits
						if(isInCompanyRange(desiredPos)){
							//if desiredPos is in company range and free from unit
							float distance = getNextUnitDistance();
							distance = Math.abs(distance);
							
							//avoid hidden units
							if(distance > Param.UNIT_BODY_WIDTH/2){
								u.setPosition(u.desiredPos(false), Gdx.graphics.getDeltaTime());							
							}
						}
					}
				}
			}
		}
	}
	private void regroup(){
		float dt = Gdx.graphics.getDeltaTime();
		for (Section s : sections) {
			for (Unit u : s.units) {
				if(!u.isDying()){
					//get position in the perfect rank
					float desiredPos = u.getPosition().x;
					//move to the right
					if(u.getPosition().x < getOrderedPosition(u) - Param.UNIT_POSITION_TOLERANCE)
						desiredPos += Param.UNIT_SPEED * dt;
					//move to the left
					else if(u.getPosition().x > getOrderedPosition(u) + Param.UNIT_POSITION_TOLERANCE)
						desiredPos -= Param.UNIT_SPEED * dt;
					u.setPosition((int)desiredPos, dt);					
				}
			}
		}
	}
	public void regroupUnits() {
		freeToMove = false;
	}
	public void freeUnits(){
		freeToMove = true;
	}
	private boolean isInCompanyRange(int desiredPos) {
		if(fixedPos - (Param.COMPANY_MARGIN + getMinWidth())/2 < desiredPos && desiredPos < fixedPos + (Param.COMPANY_MARGIN + getMinWidth())/2)
			return true;
		return false;
	}
	
	private float getNextUnitDistance(){
		float distance = 0;
				
		for (Section s : sections) {
			for (Unit u : s.units) {
				if(u.isEnemy){
					// Check next unit in section, if last of section, check next section
					if(!(s.units.firstElement() == u)){
						distance = s.units.elementAt(s.units.indexOf(u)-1).getPosition().x - u.getPosition().x;							
					}
					else if(!(sections.firstElement() == s)){
						distance = sections.elementAt(sections.indexOf(s)-1).units.firstElement().getPosition().x;
						distance -= u.getPosition().x;
					}
					
				}
				else{
					// Check next unit in section, if last of section, check next section
					if(!(s.units.lastElement() == u)){
						distance = s.units.elementAt(s.units.indexOf(u)+1).getPosition().x - u.getPosition().x;							
					}
					else if(!(sections.lastElement() == s)){
						distance = sections.elementAt(sections.indexOf(s)+1).units.firstElement().getPosition().x;
						distance -= u.getPosition().x;
					}
				}
			}
		}
		distance = Math.abs(distance);				
		
		return distance;
	}
	
	/**
	 * Return ordered position of {@code unit}
	 * @param  unit : unit that must move
	 * @return desired position
	 */
	private int getOrderedPosition(Unit unit) {
		int index = 0;
		int sectionNumber = 0;
		for (Section s : sections) {
			if(s.units.contains(unit)){
				index = s.units.indexOf(unit);
				break;
			}
			sectionNumber++;
		}
		int startPosition = fixedPos - getMinWidth() / 2;
		int previousSectionsWidth = 0;
		for (int i = 0 ; i < sectionNumber ; i++) {
			previousSectionsWidth += sections.elementAt(i).getWidth() + Param.SECTION_KEEPOUT;
		}
		int orderedPos = startPosition + previousSectionsWidth + index * Param.UNIT_BODY_WIDTH;
		return orderedPos;
	}
	public boolean isEmpty() {
		return sections.isEmpty();
	}
}