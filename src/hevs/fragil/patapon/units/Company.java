package hevs.fragil.patapon.units;
import java.util.Iterator;
import java.util.Vector;

import ch.hevs.gdx2d.lib.GdxGraphics;
import hevs.fragil.patapon.mechanics.Action;
import hevs.fragil.patapon.mechanics.Param;

public class Company {
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
				xSum += u.getPosition();
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
	public void draw(GdxGraphics g, float stateTime) {
		for (Section section : sections) {
			section.draw(g,stateTime);
		}
	}
}