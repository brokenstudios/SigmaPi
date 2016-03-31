package hevs.fragil.patapon.units;
import java.util.Iterator;
import java.util.Vector;

import hevs.fragil.patapon.others.Data;

public class Company {
	public String name = "";
	public int globalPosition = 50;
	double feverFactor = 0.1;
	public Vector<Section> sections = new Vector<Section>();
	
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
		this.globalPosition = pos;
		this.name = name;   
	}
	public String toString(){
		String t = "Start of company \n";
		t += " This company is at position : "+ globalPosition +"\n";
		t += " This company's fever factor : "+ feverFactor +"\n";
		t += " This company contains : \n";
		for (Section section : sections) {
			t += section.toString()+"\n";
		}
		t += "End of Company";
		return t;
	}
	public int getNbUnits(){
		int s = 0;
		for (Iterator<Section> i = sections.iterator(); i.hasNext();) {
			Section section = (Section) i.next();
			s += section.units.size();
		}
		return s;
	}
	public int getWidth(){
		int width = 0;
		for (Section section : sections) {
			width += section.getWidth();
		}
		int nSections = sections.size();
		return (int)(width + (nSections-1)*Data.SECTION_DISTANCE);
	}
	public void moveAbsolute(int newPos){
		int width = getWidth();
		float screenMargin = newPos - width/2f;
		if(screenMargin > 0){
			globalPosition = newPos;
			float tempPos = screenMargin;
			for (Section section : sections) {
				tempPos += section.getWidth()/2f;
				section.move((int)tempPos);
				tempPos += section.getWidth()/2f + Data.SECTION_DISTANCE;
			}
		}		
		System.out.println("Company "+name+" moved to : " + globalPosition);
	}
	public void moveRelative(int increment){
		int width = getWidth();
		double screenMargin = globalPosition + increment - width/2.0;
		if(screenMargin > 0){
			globalPosition += increment;
			double tempPos = screenMargin;
			for (Section section : sections) {
				tempPos += section.getWidth()/2.0;
				section.move((int)tempPos);
				tempPos += section.getWidth()/2.0 + Data.SECTION_DISTANCE;
			}
		}		
	}
	public void add(Section s){
		sections.addElement(s);
	}
	public void remove(Section s){
		sections.remove(s);
	}
}