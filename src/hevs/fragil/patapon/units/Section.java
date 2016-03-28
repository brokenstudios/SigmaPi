package hevs.fragil.patapon.units;
import java.util.Iterator;
import java.util.Vector;

import hevs.fragil.patapon.others.Data;

public class Section {
	public String name = "";
	int globalLife;
	int width;
	public int globalPosition;
	
	public Vector<Unit> units = new Vector<Unit>();
	public Section(){
		this(0,"noname");
	}
	public Section(String name){
		this(0,name);
	}
	public Section(int pos){
		this(pos, "noname");
	}
	public Section(int pos, String name){
		this.globalPosition = pos;
		this.name = name;   
	}
	public int getWidth(){
		return units.size()*Data.UNIT_DISTANCE;
	}
	public void move(int newPos){
		int width = getWidth();
		double margin = newPos - width/2.0 + Data.UNIT_DISTANCE/2.0;
		if(margin > 0){
			globalPosition = newPos;
			double tempPos = margin;
			for (Unit unit : units) {
				unit.move((int)tempPos);
				tempPos += Data.UNIT_DISTANCE;
			}
		}		
	}
	public String toString(){
		String t = "  Start of Section \n";
		t += "   This section contains : \n";
		for (Unit unit : units) {
			t += "     "+unit.toString()+"\n";
		}
		t += "  End of Section";
		return t;
	}
	public void add(Unit u){
		units.addElement(u);
		width += Data.UNIT_DISTANCE;
	}
	public void remove(Unit u){
		units.remove(u);
		width -= Data.UNIT_DISTANCE;
	}
}
